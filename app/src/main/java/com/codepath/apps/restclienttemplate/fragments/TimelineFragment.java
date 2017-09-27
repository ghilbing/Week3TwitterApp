package com.codepath.apps.restclienttemplate.fragments;

import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TwitterApp;
import com.codepath.apps.restclienttemplate.TwitterClient;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.utils.TwitterControl;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by gretel on 9/25/17.
 */

public class TimelineFragment extends TweetsFragment {

    private TwitterClient client = TwitterApp.getRestClient();

    @Override
    public void swipeToRefresh() {

        super.swipeToRefresh();

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                TwitterControl.getInstance().fetchTimelineTweets(0, getNewestTweet(), new TwitterControl.OnTimelineTweetsReceivedListener() {
                    @Override
                    public void onTweetsReceived(List<Tweet> tweets) {
                        putFirstTweets(tweets, false);
                        swipeContainer.setRefreshing(false);
                    }

                    @Override
                    public void onTweetsFailed(int statusCode, Throwable throwable) {
                        displayAlertMessage(getResources().getString(R.string.error_no_internet));
                        swipeContainer.setRefreshing(false);

                    }
                });
            }
        });
    }

    @Override
    public void fetchTweetsForTimeLine() {
        TwitterControl.getInstance().fetchTimelineTweets(getOldestTweet(), 0, new TwitterControl.OnTimelineTweetsReceivedListener() {
            @Override
            public void onTweetsReceived(List<Tweet> tweets) {
                appendTweets(tweets);
            }

            @Override
            public void onTweetsFailed(int statusCode, Throwable throwable) {
                displayAlertMessage(getResources().getString(R.string.error_no_internet));

            }
        });
    }

    /*@Override
    public void populateTimeline(String maxId) {
        client.getHomeTimeline(maxId, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response){
                ArrayList<Tweet> tweets = Tweet.fromJson(response);
                addAll(tweets);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse){
                System.out.println("Timeline failed");
                System.out.println(errorResponse);
                Toast.makeText(getActivity().getApplicationContext(), "Couldn't get Tweets :(", Toast.LENGTH_LONG).show();
                swipeContainer.setRefreshing(false);
            }

        });



    }*/
}

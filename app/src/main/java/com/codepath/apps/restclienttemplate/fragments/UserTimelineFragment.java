package com.codepath.apps.restclienttemplate.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TwitterApp;
import com.codepath.apps.restclienttemplate.TwitterClient;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.models.User;
import com.codepath.apps.restclienttemplate.utils.TwitterControl;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by gretel on 9/26/17.
 */

public class UserTimelineFragment extends TweetsFragment {
    /*TwitterClient client = TwitterApp.getRestClient();
    String username;*/

    private static final String USER_ID = "USER_ID";

    public static UserTimelineFragment newInstance(long userId){

        UserTimelineFragment userTimelineFragment = new UserTimelineFragment();
        Bundle args = new Bundle();
        args.putLong(USER_ID, userId);
        userTimelineFragment.setArguments(args);
        return userTimelineFragment;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        long userId = getArguments().getLong(USER_ID);
        setUser(User.findUser(userId));
    }

    @Override
    public void fetchTweetsForTimeLine() {
        TwitterControl.getInstance().fetchUserTimeline(getUser().getRemoteId(), getOldestTweet(), 0, new TwitterControl.OnTimelineTweetsReceivedListener() {
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

    @Override
    public void swipeToRefresh() {
        super.swipeToRefresh();
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                TwitterControl.getInstance().fetchUserTimeline(getUser().getRemoteId(), 0, getNewestTweet(), new TwitterControl.OnTimelineTweetsReceivedListener() {
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


    /* @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        username = getArguments().getString("user");
    }

    public static UserTimelineFragment newInstance(String username) {
        UserTimelineFragment f = new UserTimelineFragment();
        Bundle args = new Bundle();
        args.putString("user", username);
        f.setArguments(args);
        return f;
    }

    public void populateTimeline(String maxId) {

        client.getUserTimeline(username, maxId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                System.out.println(response);
                ArrayList<Tweet> tweets = Tweet.fromJson(response);
                addAll(tweets);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                System.out.println("user timeline failed");
                System.out.println(errorResponse);
                Toast.makeText(getActivity().getApplicationContext(),
                        "Couldn't get Tweets :(", Toast.LENGTH_SHORT).show();
                swipeContainer.setRefreshing(false);
            }
        });
    }*/

    @Override
    public void onItemClick(View view, int position) {

    }
}

package com.codepath.apps.restclienttemplate.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TwitterClient;
import com.codepath.apps.restclienttemplate.activities.DetailActivity;
import com.codepath.apps.restclienttemplate.activities.ProfileActivity;
import com.codepath.apps.restclienttemplate.adapters.TweetAdapter;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.models.User;
import com.codepath.apps.restclienttemplate.utils.EndlessRecyclerViewScrollListener;
import com.codepath.apps.restclienttemplate.utils.TwitterControl;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by gretel on 9/25/17.
 */

public class TweetsFragment extends Fragment implements TweetAdapter.OnItemClickListener {

    @Bind(R.id.swipeContainer)
    SwipeRefreshLayout swipeContainer;

    @Bind(R.id.rvTweet)
    RecyclerView mRecycler;

    private List<Tweet> mTweets;
    private TweetAdapter mAdapter;

    private long mOldestTweet;
    private long mNewestTweet;
    private User mUser;
    private OnTweetFragmentListener mListener;
    public interface OnTweetFragmentListener {
        void onReplyToTweet(Tweet newTweetPost);
    }


    public TweetsFragment() {

    }

    /*
    public abstract void populateTimeline(String maxId);

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        if (isNetworkAvailable()){
            populateTimeline(null);

        }else {
            Toast.makeText(getActivity().getApplicationContext(), "No Internet connection", Toast.LENGTH_LONG).show();
        }

    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tweets, container, false);

        ButterKnife.bind(this, view);

        swipeToRefresh();
        setupRecycler();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTweets = new ArrayList<>();
        mOldestTweet = 0;
        mNewestTweet = 0;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        fetchTweetsForTimeLine();
    }


    protected void setupRecycler(){
        mAdapter = new TweetAdapter(getContext(), mTweets);
        mRecycler.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecycler.setLayoutManager(layoutManager);
       /* mRecycler.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                fetchTweetsForTimeLine();
            }

        });*/
    }

    public void swipeToRefresh() {
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright);
    }

    public void fetchTweetsForTimeLine() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnTweetFragmentListener) {
            mListener = (OnTweetFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnTweetsDialogFragmentListener");
        }
    }

    @Override
    public void onItemClick(View view, int position){
        Tweet tweet = mTweets.get(position);
        switch (view.getId()){
            case R.id.btnReply:
                replyToTweet(tweet);
                break;
            case R.id.btnRetweet:
                retweet(tweet);
                break;
            case R.id.btnFavorite:
                markTweetAsFavorite(tweet);
                break;
            case R.id.ivProfileImage:
                showProfile(tweet.getUser());
                break;
            default:
                openTweet(mTweets.get(position));
                break;
        }
    }

    public User getUser(){
        return mUser;
    }

    public void setUser(User user) {mUser = user;}

    public long getOldestTweet() {
        return mOldestTweet;
    }

    public long getNewestTweet() {
        return mNewestTweet;
    }

    public void appendTweets(List<Tweet> tweets) {
        mTweets.addAll(tweets);
        mAdapter.notifyItemRangeInserted(mAdapter.getItemCount(), tweets.size());
        Tweet oldestTweet = tweets.get(tweets.size() - 1);
        if (oldestTweet != null) {
            mOldestTweet = oldestTweet.getTweetId();
        }
        Tweet newestTweet = tweets.get(0);
        if (newestTweet != null && mNewestTweet == 0) {
            mNewestTweet = newestTweet.getTweetId();
        }
    }

    public void putFirstTweets(List<Tweet> tweets, boolean scrollToFirstItem) {
        if (tweets.size() > 0) {
            mTweets.addAll(0, tweets);
            mAdapter.notifyItemRangeInserted(0, tweets.size());
            mNewestTweet = tweets.get(0).getTweetId();

            if (scrollToFirstItem) {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecycler.getLayoutManager();
                linearLayoutManager.scrollToPositionWithOffset(0, 20);
            }
        }
    }

    public void displayAlertMessage(final String alertMessage) {
        Snackbar.make(getActivity().findViewById(android.R.id.content), alertMessage,
                Snackbar.LENGTH_LONG).show();
    }

    private void removeTweet(Tweet tweet) {
        mTweets.remove(tweet);
        mAdapter.notifyItemRemoved(mAdapter.getItemCount() - 1);
    }

    private void retweet(final Tweet tweet) {
        TwitterControl.getInstance().retweet(tweet.getTweetId(), new TwitterControl.OnTweetUpdatedListener() {
            @Override
            public void onTweetUpdated(Tweet updatedTweet) {
                mAdapter.notifyItemChanged(mTweets.indexOf(tweet));
            }

            @Override
            public void onTweetUpdateFailed(int statusCode, Throwable throwable) {
                displayAlertMessage(getResources().getString(R.string.error_retweet_failed));
            }
        });
    }

    private void openTweet(Tweet tweet) {
        Intent intent = DetailActivity.getStartIntent(getActivity(), tweet, TwitterControl.getInstance().getCurrentUser());
        startActivity(intent);
    }

    private void markTweetAsFavorite(final Tweet tweet) {
        TwitterControl.getInstance().markAsFavorite(tweet.getTweetId(), new TwitterControl.OnTweetUpdatedListener() {
            @Override
            public void onTweetUpdated(Tweet updatedTweet) {
                mAdapter.notifyItemChanged(mTweets.indexOf(tweet));
            }

            @Override
            public void onTweetUpdateFailed(int statusCode, Throwable throwable) {
                displayAlertMessage(getResources().getString(R.string.error_favorite_failed));
            }
        });
    }

    private void replyToTweet(final Tweet tweet) {
        if (mListener != null) {
            mListener.onReplyToTweet(tweet);
        }
    }

    private void showProfile(User user) {
        Intent intent = ProfileActivity.getStartIntent(getActivity(), user);
        startActivity(intent);
    }







        /*
        mAdapter = new TweetAdapter(getActivity(), new ArrayList<Tweet>());
        mRecycler.setAdapter(mAdapter);
        mRecycler.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mRecycler.addOnScrollListener(new EndlessRecyclerViewScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
               // Log.i( + (mAdapter.getItemCount()));
                if(mAdapter.getItemCount() == 0){
                    populateTimeline(null);
                } else if (mAdapter.getItemCount() >= TwitterClient.T_X_PAGE){
                    Tweet oldest = mAdapter.getItem(mAdapter.getItemCount()-1);
                    populateTimeline(oldest.getUid());
                }
            }
        });

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdapter.clear();
                mAdapter.notifyDataSetChanged();
                populateTimeline(null);
            }
        });

        return view;


    }

    protected boolean isNetworkAvailable(){
        ConnectivityManager cm = (ConnectivityManager) getActivity().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }*/

    public void add(Tweet tweet){
        mAdapter.add(0, tweet);
        mAdapter.notifyDataSetChanged();

    }

    public void addAll(ArrayList<Tweet> tweets){
        mAdapter.addAll(tweets);
        mAdapter.notifyDataSetChanged();
        swipeContainer.setRefreshing(false);
    }


}

package com.codepath.apps.restclienttemplate.activities;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.fragments.ComposeTweetFragment;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.models.User;
import com.codepath.apps.restclienttemplate.utils.TwitterControl;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailActivity extends AppCompatActivity implements ComposeTweetFragment.OnComposeListener {

    private static final String EXTRA_TWEET = "tweetdetails";
    private static final String EXTRA_USER = "currentuser";

    @Bind(R.id.toolbarDetail)
    Toolbar toolbar;
    @Bind(R.id.ivProfileImageDetail)
    ImageView ivProfileImage;
    @Bind(R.id.tvScreenNameDetail)
    TextView tvScreenName;
    @Bind(R.id.tvNameDetail)
    TextView tvName;
    @Bind(R.id.tvBodyDetail)
    TextView tvBody;
    @Bind(R.id.ivMediaDetail)
    ImageView ivMedia;
    @Bind(R.id.vvVideoDetail)
    VideoView vvVideo;
    @Bind(R.id.btnRetweet)
    Button btnRetweet;
    @Bind(R.id.btnFavorite)
    Button btnFavorite;
    @Bind(R.id.btnReply)
    Button btnReply;

    private User mCurrentUser;
    private Tweet mTweet;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTweet = Tweet.findTweet(getIntent().getLongExtra(DetailActivity.EXTRA_TWEET, 0));
        mCurrentUser = User.findUser(getIntent().getLongExtra(DetailActivity.EXTRA_USER, 0));
        setupTweet();

    }

    @Override
    public void onPostedTweet(Tweet newTweetPost) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    public static Intent getStartIntent(Context context, Tweet tweet, User currentUser) {
        Intent intent= new Intent(context, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_TWEET, tweet.getTweetId());
        intent.putExtra(DetailActivity.EXTRA_USER, currentUser.getRemoteId());
        return intent;
    }



    @OnClick(R.id.btnFavorite)
    public void onFavoriteClicked(View view) {
        markTweetAsFavorite();
    }

    @OnClick(R.id.btnRetweet)
    public void onRetweetClicked(View view) {
        retweet();
    }

    @OnClick(R.id.btnReply)
    public void onReplyClicked(View view) {
        ComposeTweetFragment composeTweetFragment = ComposeTweetFragment.newInstance(mCurrentUser, mTweet);
        composeTweetFragment.show(getSupportFragmentManager(), "fragment_compose_tweet");
    }

    private void setupVideo() {

        if (mTweet.getVideo() != null && !TextUtils.isEmpty(mTweet.getVideo().getUrl())) {

            vvVideo.setVideoPath(mTweet.getVideo().getUrl());
            vvVideo.requestFocus();
            vvVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    vvVideo.start();
                    mp.setLooping(true);
                }
            });
        } else {
            vvVideo.setVisibility(View.GONE);
        }
    }

    private void setupImage() {
        User user = mTweet.getUser();
        Glide.with(ivProfileImage.getContext()).
                load(mTweet.getUser().getProfileImage()).
                into(ivProfileImage);

        if (mTweet.getMedia() != null) {
            Glide.with(ivMedia.getContext()).
                    load(mTweet.getMedia().getUrl()).
                    into(ivMedia);
        } else {
            ivMedia.setVisibility(View.GONE);
        }
    }

    private void setupTweet() {
        User user = mTweet.getUser();
        tvName.setText(user.getName());
        tvScreenName.setText(user.getScreenName());
        tvBody.setText(mTweet.getBody());
        btnRetweet.setText(Integer.toString(mTweet.getRetweetCount()));
        btnFavorite.setText(Integer.toString(mTweet.getFavoriteCount()));

        setupImage();
        setupVideo();
    }

    private void markTweetAsFavorite() {
        TwitterControl.getInstance().markAsFavorite(mTweet.getTweetId(), new TwitterControl.OnTweetUpdatedListener() {

            @Override
            public void onTweetUpdated(Tweet updatedTweet) {
                btnFavorite.setText(Integer.toString(updatedTweet.getFavoriteCount()));
            }

            @Override
            public void onTweetUpdateFailed(int statusCode, Throwable throwable) {
                displayAlertMessage(getResources().getString(R.string.error_favorite_failed));
            }
        });
    }

    private void retweet() {
        TwitterControl.getInstance().retweet(mTweet.getTweetId(), new TwitterControl.OnTweetUpdatedListener() {
            @Override
            public void onTweetUpdated(Tweet updatedTweet) {
                // this will be easier when tweets are in a local db
                btnRetweet.setText(Integer.toString(updatedTweet.getRetweetCount()));
            }

            @Override
            public void onTweetUpdateFailed(int statusCode, Throwable throwable) {
                displayAlertMessage(getResources().getString(R.string.error_retweet_failed));
            }
        });
    }

    private void displayAlertMessage(final String alertMessage) {
        Snackbar.make(findViewById(android.R.id.content), alertMessage,
                Snackbar.LENGTH_LONG).show();
    }
}

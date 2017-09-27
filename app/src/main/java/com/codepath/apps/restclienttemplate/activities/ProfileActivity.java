package com.codepath.apps.restclienttemplate.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.fragments.ComposeTweetFragment;
import com.codepath.apps.restclienttemplate.fragments.TweetsFragment;
import com.codepath.apps.restclienttemplate.fragments.UserTimelineFragment;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.models.User;
import com.codepath.apps.restclienttemplate.utils.TwitterControl;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity implements ComposeTweetFragment.OnComposeListener, TweetsFragment.OnTweetFragmentListener {



    @Bind(R.id.toolbar)
    Toolbar toolbar;
   /* @Bind(R.id.ivBackground)
    ImageView ivBackground;
    @Bind(R.id.ivProfileImage)
    ImageView ivProfileImage;
    @Bind(R.id.tvProfileName)
    TextView tvProfileName;
    @Bind(R.id.tvUserName)
    TextView tvUserName;
    @Bind(R.id.tvTweetCount)
    TextView tvTweetCount;
    @Bind(R.id.tvUserFollowerCount)
    TextView tvFollowerCount;
    @Bind(R.id.tvUserFollowingCount)
    TextView tvFollowingCount;
    @Bind(R.id.tvTagline)
    TextView tagline;*/

    private static String EXTRA_USER = "userId";

    private long mCurrentUser;

    public static Intent getStartIntent(Context context, User currentUser){
        Intent intent = new Intent(context, ProfileActivity.class);
        intent.putExtra(ProfileActivity.EXTRA_USER, currentUser.getRemoteId());
        return intent;
    }


    //TwitterClient client = TwitterApp.getRestClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ButterKnife.bind(this);

        mCurrentUser = getIntent().getLongExtra(EXTRA_USER, 0);

       /* String user = getIntent().getStringExtra("usermane");

        if(savedInstanceState == null){
            UserTimelineFragment userFragment = UserTimelineFragment.newInstance(user);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.profileFragment, userFragment);
            transaction.commit();
        }*/

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(savedInstanceState == null){
            setupUserTimeline();
            setupUserProfile();
        }

        User user = User.findUser(mCurrentUser);
        getSupportActionBar().setTitle(user.getScreenName());


       //populateUser(user);

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


    private void setupUserTimeline() {
        UserTimelineFragment userTimelineFragment = UserTimelineFragment.newInstance(mCurrentUser);
        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.layout_timeline_placeholder, userTimelineFragment).
                commit();
    }

    private void setupUserProfile() {
        UserTimelineFragment userProfileFragment = UserTimelineFragment.newInstance(mCurrentUser);
        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.layout_user_profile_placeholder, userProfileFragment).
                commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user, menu);
        return true;
    }



    /*private void populateUser(String username) {

        if(username != null){
            client.getUser(username, handler);
        }else {
            client.getCredentials(handler);
        }

    }

    private JsonHttpResponseHandler handler = new JsonHttpResponseHandler(){
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

            try{
                int friends = response.getInt("friends_count");
                int followers = response.getInt("followers_count");
                int statuses = response.getInt("statuses_count");

                String fullName = response.getString("name");
                String screenName = response.getString("screen_name");
                String description = response.getString("description");
                String profileImage = response.getString("profile_image_url");
                String backgroundImage = response.getString("profile_background_image_url");

                tvProfileName.setText(fullName);
                tvUserName.setText("@" + screenName);
                tagline.setText(description);
                tvFollowerCount.setText(String.valueOf(followers) + " Followers");
                tvFollowingCount.setText(String.valueOf(friends) + " Following");
                tvTweetCount.setText(String.valueOf(statuses) + " Tweets");

                Glide.with(getApplicationContext()).load(Uri.parse(profileImage)).into(ivProfileImage);
                Glide.with(getApplicationContext()).load(Uri.parse(backgroundImage)).into(ivBackground);

            } catch (JSONException e){

                e.printStackTrace();
            }
        }
    };*/

    @Override
    public void onReplyToTweet(Tweet newTweetPost) {
        ComposeTweetFragment tweetDialogFragment = ComposeTweetFragment.newInstance(TwitterControl.getInstance().getCurrentUser(),
                newTweetPost);
        tweetDialogFragment.show(getSupportFragmentManager(), "fragment_compose_tweet_dialog");

    }

    @Override
    public void onPostedTweet(Tweet newTweetPost) {

    }
}

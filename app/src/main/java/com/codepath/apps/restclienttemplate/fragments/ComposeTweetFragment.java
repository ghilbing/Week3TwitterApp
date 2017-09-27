package com.codepath.apps.restclienttemplate.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TwitterClient;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.models.User;
import com.codepath.apps.restclienttemplate.utils.TwitterControl;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by gretel on 9/26/17.
 */

public class ComposeTweetFragment extends DialogFragment{


    @Bind(R.id.etNewTweet)
    EditText etNewTweet;
    @Bind(R.id.tvCount)
    TextView mTextCount;
    @Bind(R.id.ivProfileImage)
    RoundedImageView mProfileImage;
    @Bind(R.id.btnNewTweet)
    Button btnNewTweet;

    private static final String CURRENT_USER = "CURRENT_USER";
    private static final String REPLY_TO_TWEET = "REPLY_TO_TWEET";

    private static final int MAX_CARACTERS = 140;

    private User mCurrentUser;
    private Tweet mInReply;
    private OnComposeListener mListener;
    public interface OnComposeListener {
        void onPostedTweet(Tweet newTweetPost);
    }


    public ComposeTweetFragment() {}

    public static ComposeTweetFragment newInstance(User currentUser, Tweet inReply){
        ComposeTweetFragment fragment = new ComposeTweetFragment();
        Bundle args = new Bundle();
        if(inReply != null){
            args.putLong(CURRENT_USER, currentUser.getRemoteId());
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        if(getArguments() != null){
            mCurrentUser = User.findUser(getArguments().getLong(CURRENT_USER));
            mInReply = Tweet.findTweet(getArguments().getLong(REPLY_TO_TWEET, 0));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_compose_tweet, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        Glide.with(mProfileImage.getContext()).load(mCurrentUser.getProfileImage()).into(mProfileImage);
        if (mInReply != null) {
            etNewTweet.setText(mInReply.getUser().getScreenName());
        }

        etNewTweet.requestFocus();

    }

    @Override
    public void onResume() {
        //Get params from window
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        //Assing window properties
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);

        super.onResume();
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if(context instanceof OnComposeListener){
            mListener = (OnComposeListener) context;

            } else {
            throw new RuntimeException(context.toString() + " must implement listener");

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @OnTextChanged(R.id.etNewTweet)
    void onTweetBodyTextChanged(Editable tweetText){
        int remaining = MAX_CARACTERS - tweetText.toString().length();

        mTextCount.setText(Integer.toString(remaining));

        if (remaining <0) {
            mTextCount.setTextColor(ContextCompat.getColor(mTextCount.getContext(), R.color.red));
            mTextCount.setTextAppearance(getContext(), android.R.style.TextAppearance_Medium);
        }
        else {
            mTextCount.setTextColor(ContextCompat.getColor(mTextCount.getContext(), R.color.style_color_grey_text));
        }

        btnNewTweet.setEnabled(remaining>=0);

    }

    @OnClick(R.id.btnNewTweet)
    void onPostTweetClicked(final View view){
        TwitterControl.getInstance().postUpdate(etNewTweet.getText().toString(), 0, new TwitterControl.OnNewPostReceivedListener() {
            @Override
            public void onPostCreated(Tweet tweet) {
                mListener.onPostedTweet(tweet);
            }

            @Override
            public void onPostFailed(int statusCode, Throwable throwable) {

            }
        });
    }





}

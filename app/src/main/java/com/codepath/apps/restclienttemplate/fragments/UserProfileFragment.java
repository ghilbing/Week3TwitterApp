package com.codepath.apps.restclienttemplate.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.models.User;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by gretel on 9/26/17.
 */

public class UserProfileFragment extends Fragment {

    private static final String USER_ID = "USER_ID";

    @Bind(R.id.ivBackground)
    ImageView ivBackground;
    @Bind(R.id.ivProfileImage)
    RoundedImageView ivProfileImage;
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
    TextView tvDescription;

    User mUser;

    public static UserProfileFragment newInstance(long userId){
        UserProfileFragment userProfileFragment = new UserProfileFragment();
        Bundle args = new Bundle();
        args.putLong(USER_ID, userId);
        userProfileFragment.setArguments(args);
        return userProfileFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        ButterKnife.bind(this, view);
        setupUser(mUser);
        return view;
    }

    private void setupUser(User user) {
        Glide.with(ivProfileImage.getContext()).
                load(user.getProfileImage()).
                into(ivProfileImage);

        Glide.with(ivBackground.getContext()).
                load(user.getProfileBanner()).
                fitCenter().
                into(ivBackground);

        tvUserName.setText(user.getName());
        tvProfileName.setText(user.getScreenName());
        tvFollowerCount.setText(user.getFollowersCount());
        tvFollowingCount.setText(user.getFriendsCount());
        tvDescription.setText(user.getDescription());
      //  tvTweetCount.setText(user.getStatuses());
    }


}

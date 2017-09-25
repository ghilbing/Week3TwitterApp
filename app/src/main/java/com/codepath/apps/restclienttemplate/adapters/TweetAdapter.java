package com.codepath.apps.restclienttemplate.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.models.Tweet;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Bind;

/**
 * Created by gretel on 9/25/17.
 */

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder>{



    private List<Tweet> mTeets;
    Context context;

    //pass in the Tweets array in the constructor
    public TweetAdapter(List<Tweet> tweets){
        mTeets = tweets;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View tweetView = inflater.inflate(R.layout.item_tweet, parent, false);
        ViewHolder viewHolder = new ViewHolder(tweetView);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //Get the data according t psition
        Tweet tweet = mTeets.get(position);

        //populate the views according to this data
        holder.tvUserName.setText(tweet.user.name);
        holder.tvBody.setText(tweet.body);

        Glide.with(context).load(tweet.user.profileImageUrl).into(holder.ivProfileImage);




    }

    @Override
    public int getItemCount() {
        return mTeets.size();
    }

    //for each row, inflate the layout and cache the references into ViewHolder



    public class ViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.ivProfileImage)
        ImageView ivProfileImage;
        @Bind(R.id.tvUserName)
        TextView tvUserName;
        @Bind(R.id.tvBody)
        TextView tvBody;

        public ViewHolder (View itemView){
            super(itemView);

            ButterKnife.bind(this, itemView);
        }



    }
}

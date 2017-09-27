package com.codepath.apps.restclienttemplate.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import com.codepath.apps.restclienttemplate.MyDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.codepath.apps.restclienttemplate.TwitterClient;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.structure.BaseModel;

import static android.R.attr.excludeClass;
import static android.R.attr.name;

/**
 * Created by gretel on 9/25/17.
 */

@Table(database = MyDatabase.class)

public class Tweet extends BaseModel {


    @Column
    @PrimaryKey
    Long id;

    @Column String text;
    @Column String createdAt;
    @Column int retweetCount;
    @Column int favoriteCount;

    private List<Entities> entities;

    private List<ExtendedEntities> extendedEntities;

    @Column
    @ForeignKey(saveForeignKeyModel = true)
    private User user;


    public Tweet() {
        super();

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getBody() {
        return text;
    }

    public void setBody(String body) {
        this.text = body;
    }

    public String getCreatedAt() {
        return TwitterClient.getTimeAgo(createdAt);
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getRetweetCount() {
        return retweetCount;
    }

    public void setRetweetCount(int retweetCount) {
        this.retweetCount = retweetCount;
    }


    public int getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(int favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public long getTweetId() {
        return id;
    }

    public void setTweetId(long tweetId) {
        this.id = tweetId;
    }

    public List<Entities> getEntities() {return entities;}

    public List<ExtendedEntities> getExtendedEntities() { return extendedEntities;}


    private String getFirstMediaUrlFromExtendedEntities() {
        List<ExtendedEntities> extendedEntities = getExtendedEntities();
        if (extendedEntities == null || entities.isEmpty()) {
            return null;
        }
        for (ExtendedEntities extendedEntity : extendedEntities) {
            if (!"videos".equals(extendedEntity.getType())) {
                return extendedEntity.getMediaUrl();
            }
            String videoUrl = extendedEntity.getVideoInfo().getVariants().get(0).getUrl();
            return videoUrl;
        }
        return null;
    }

    public String getFirstMediaUrl() {
        String mediaUrl = getFirstMediaUrlFromExtendedEntities();
        if (mediaUrl != null) {
            return mediaUrl;
        }
        List<Entities> entities = getEntities();
        if (entities == null || entities.isEmpty()) {
            return null;
        }
        for (Entities entity : entities) {
            List<Media> mediaList = entity.getMedia();
            if (mediaList == null || mediaList.isEmpty()) {
                continue;
            }
            for (Media media : mediaList) {
                if (media.getMediaUrl() != null) {
                    return media.getMediaUrl();
                }
            }
        }
        return null;
    }


    public static Tweet findTweet(long userId) {

        return new Select().from(Tweet.class).where(Tweet_Table.id.eq(userId)).querySingle();
    }

/*
    public static Tweet fromJson(JSONObject json) {
        Tweet tweet = new Tweet();
        try {
            tweet.tweetId = json.getLong("id_str");
            tweet.body = json.getString("text");
            tweet.createdAt = json.getString("created_at");
            tweet.retweetCount = json.getInt("retweet_count");
            tweet.user = User.fromJson(json.getJSONObject("user"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tweet;
    }

    public static ArrayList<Tweet> fromJson(JSONArray json) {
        ArrayList<Tweet> tweets = new ArrayList<Tweet>(json.length());
        for (int i = 0; i < json.length(); i++) {
            try {
                Tweet tweet = fromJson(json.getJSONObject(i));
                if(tweet != null) {
                    tweets.add(tweet);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
        }
        return tweets;
    }

    protected Tweet(Parcel in) {
        tweetId = in.readLong();
        body = in.readString();
        createdAt = in.readString();
        retweetCount = in.readInt();
        user = (User) in.readValue(User.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tweetId);
        dest.writeString(body);
        dest.writeString(createdAt);
        dest.writeInt(retweetCount);
        dest.writeValue(user);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Tweet> CREATOR = new Parcelable.Creator<Tweet>() {
        @Override
        public Tweet createFromParcel(Parcel in) {
            return new Tweet(in);
        }

        @Override
        public Tweet[] newArray(int size) {
            return new Tweet[size];
        }
    };*/
}

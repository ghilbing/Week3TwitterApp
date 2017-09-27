package com.codepath.apps.restclienttemplate.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.codepath.apps.restclienttemplate.TwitterClient;

import static android.R.attr.name;

/**
 * Created by gretel on 9/25/17.
 */

@Table(name = "tweet")
public class Tweet extends Model {

    @Column(name="User")
    public User user;

    //private String id;
    @Column String body;
    @Column String createdAt;
    @Column int retweetCount;
    @Column int favoriteCount;

    @Column(name = "remote_id")
    public long tweetId;

    @Column(name="Entities", onDelete = Column.ForeignKeyAction.CASCADE)
    Entities entities;

    @Column(name="ExtendedEntities", onDelete = Column.ForeignKeyAction.CASCADE)
    ExtendedEntities extendedEntities;

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
        return body;
    }

    public void setBody(String body) {
        this.body = body;
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
        return tweetId;
    }

    public void setTweetId(long tweetId) {
        this.tweetId = tweetId;
    }

    public final Long cascadeSave() {
        if (user != null) {
            User originalUser = User.findUser(user.getRemoteId());
            if (originalUser != null) {
                user = originalUser;
            } else {
                user.save();
            }
        }

        if (entities != null) {
            entities.cascadeSave();
        }

        if (extendedEntities != null) {
            extendedEntities.cascadeSave();
        }

        return super.save();
    }

    public Media getMedia() {
        List<Media> media = entities.getMedia();
        return (media != null && media.size() > 0) ? media.get(0) : null;
    }

    public Video getVideo() {
        List<Video> videos = null;
        if (extendedEntities != null) {
            extendedEntities.getExtendedMedia();
        }
        return (videos != null &&  videos.size() > 0) ? videos.get(0) : null;
    }

    @Table(name = "Entities")
    public static class Entities extends Model {

        List<Media> media;

        public Entities() {
            super();
        }

        public List<Media> getMedia() {
            return getMany(Media.class, "Entities");
        }

        public final Long cascadeSave() {
            long retVal = save();
            if (media != null && media.size() > 0) {
                for (Media med : media) {
                    med.setEntities(this);
                    med.cascadeSave();
                }
            }
            return retVal;
        }
    }

    @Table(name = "ExtendedEntities")
    public static class ExtendedEntities extends Model {
        List<Video> media;

        public ExtendedEntities() {
            super();
        }

        public List<Video> getExtendedMedia() {
            return getMany(Video.class, "ExtendedEntities");
        }

        public final Long cascadeSave() {
            long retVal = save();
            if (media != null && media.size() > 0) {
                for (Video med : media) {
                    med.setExtendedEntities(this);
                    med.cascadeSave();
                }
            }
            return retVal;
        }
    }

    public static Tweet findTweet(long serverId) {
        return new Select().from(Tweet.class).where("remote_id = ?", serverId).executeSingle();
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

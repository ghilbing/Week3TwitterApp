package com.codepath.apps.restclienttemplate.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.activeandroid.annotation.Table;
import com.activeandroid.annotation.Column;
import com.activeandroid.Model;
import com.activeandroid.query.Select;


import org.json.JSONException;
import org.json.JSONObject;

import static android.R.attr.name;

/**
 * Created by gretel on 9/25/17.
 */
@Table(name = "user")
public class User extends Model{

    //List the attributes
   // @Column long uid;
    @Column String name;
    @Column String profileImage;
    @Column String screenName;
    @Column String profileBanner;
    @Column String description;
    @Column int followersCount;
    @Column String profileBackgroundColor;
    @Column int friendsCount;
   // @Column int statuses;

    @Column(name = "remote_id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public long remoteId;


    public User() {
        super();

    }

    public long getRemoteId() {
        return remoteId;
    }

    public void setRemoteId(long id) {
        this.remoteId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String userName) {
        this.name = userName;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String userProfileImage) {
        this.profileImage = userProfileImage;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String userScreenName) {
        this.screenName = userScreenName;
    }

    public String getProfileBanner() {
        return profileBanner;
    }

    public void setProfileBanner(String profileBanner) {
        this.profileBanner = profileBanner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }

    public int getFriendsCount() {
        return friendsCount;
    }

   /* public int getStatuses() {
        return statuses;
    }

    public void setStatuses(int statuses) {
        this.statuses = statuses;
    }*/

    public void setFriendsCount(int friendsCount) {
        this.friendsCount = friendsCount;
    }

    public String getProfileBackgroundColor() {
        return profileBackgroundColor;
    }

    public void setProfileBackgroundColor(String profileBackgroundColor) {
        this.profileBackgroundColor = profileBackgroundColor;
    }

    public static User findUser(long userId) {
        return new Select().from(User.class).where("remote_id = ?", userId).executeSingle();

    /*}
    public static User fromJson(JSONObject json) {
        User user = new User();
        try {
            user.remoteId = json.getLong("id_str");
            user.name = json.getString("name");
            user.profileImage = json.getString("profile_image_url");
            user.screenName = json.getString("screen_name");
            user.description = json.getString("description");
          //  user.statuses = json.getInt("statuses_count");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }*/

   /* protected User(Parcel in) {
        remoteId = in.readLong();
        name = in.readString();
        profileImage = in.readString();
        screenName = in.readString();
    }*/

   /* @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(uid);
        dest.writeString(name);
        dest.writeString(profileImage);
        dest.writeString(screenName);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
*/

    }
}




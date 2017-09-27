package com.codepath.apps.restclienttemplate.models;


import com.codepath.apps.restclienttemplate.R;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.sql.language.Where;
import com.raizlabs.android.dbflow.structure.BaseModel;

import com.codepath.apps.restclienttemplate.MyDatabase;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by gretel on 9/25/17.
 */
@Table(database = MyDatabase.class)
public class User extends BaseModel{

    //List the attributes
   // @Column long uid;
    @Column String name;
    @Column String profileImageUrl;
    @Column String screenName;
    @Column String profileBannerUrl;
    @Column String tagline;
    @Column int followersCount;
    @Column int followingCount;
    @Column String profileBackgroundImageUrl;
    @Column int friendsCount;
    @Column int statuses;

    @Column
    @PrimaryKey long id;


    public User() {
        super();

    }

    public long getRemoteId() {
        return id;
    }
    public void setRemoteId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String userName) {
        this.name = userName;
    }

    public String getProfileImage() {
        return profileImageUrl;
    }

    public void setProfileImage(String userProfileImage) {
        this.profileImageUrl = userProfileImage;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String userScreenName) {
        this.screenName = userScreenName;
    }

    public String getProfileBanner() {
        return profileBannerUrl;
    }

    public void setProfileBanner(String profileBanner) {
        this.profileBannerUrl = profileBanner;
    }

    public String getDescription() {
        return tagline;
    }

    public void setDescription(String description) {
        this.tagline = description;
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

    public int getFollowingCount() {return followingCount;}

    public int getStatuses() {
        return statuses;
    }

    public void setStatuses(int statuses) {
        this.statuses = statuses;
    }

    public void setFriendsCount(int friendsCount) {
        this.friendsCount = friendsCount;
    }

    public String getProfileBackgroundColor() {
        return profileBackgroundImageUrl;
    }

    public void setProfileBackgroundColor(String profileBackgroundColor) {
        this.profileBackgroundImageUrl = profileBackgroundColor;
    }

    public static User findUser(long userId) {

        return new Select().from(User.class).where(User_Table.id.eq(userId)).querySingle();


    }


   /* public static User fromJson(JSONObject json) {
        User user = new User();
        try {
            user.id = json.getLong("id");
            user.name = json.getString("name");
            user.screenName = json.getString("screen_name");
            user.profileBackgroundImageUrl = json.getString("profile_background_image_url");
            user.profileImageUrl = json.getString("profile_image_url");
            user.tagline = json.getString("description");
            user.profileBannerUrl = json.getString("profile_banner_url");
            user.followersCount = json.getInt("followers_count");
            user.followingCount = json.getInt("friends_count");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;*/
    }

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









package com.codepath.apps.restclienttemplate.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by gretel on 9/26/17.
 */

@Table(name = "media")
public class Media extends Model {

    @Column
    String type;
    @Column
    String mediaUrl;
    @Column(name = "Entities")
    Tweet.Entities mEntities;

    public Media() {
        super();
    }

    public void setEntities(Tweet.Entities entities) {
        mEntities = entities;
    }

    public Long cascadeSave() {
        return save();
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return mediaUrl;
    }
}

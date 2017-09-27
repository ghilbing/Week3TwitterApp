package com.codepath.apps.restclienttemplate.models;

import com.activeandroid.Model;

import com.codepath.apps.restclienttemplate.MyDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by gretel on 9/26/17.
 */


public class Media extends BaseModel {


    Long id;

    String mediaUrl;

    public Media() {
        super();
    }

    public Long getId() {
        return id;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }


}

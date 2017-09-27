package com.codepath.apps.restclienttemplate.models;

/**
 * Created by gretel on 9/27/17.
 */

public class ExtendedEntities {

    Long id;
    String mediaUrl;
    VideoInfo videoInfo;
    String type;

    public Long getId() {
        return id;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public VideoInfo getVideoInfo() {
        return videoInfo;
    }

    public String getType() {
        return type;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public void setVideoInfo(VideoInfo videoInfo) {
        this.videoInfo = videoInfo;
    }

    public void setType(String type) {
        this.type = type;
    }
}

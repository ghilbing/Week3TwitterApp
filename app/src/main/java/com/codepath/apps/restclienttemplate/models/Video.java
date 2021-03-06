package com.codepath.apps.restclienttemplate.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

/**
 * Created by gretel on 9/26/17.
 */
/*
public class Video extends Media {


    @Column(name="VideoInfo")
    VideoInfo videoInfo;

    @Column (name = "ExtendedEntities") Tweet.ExtendedEntities mExtendedEntities;

    public Video() {
        super();
    }

    public void setExtendedEntities(Tweet.ExtendedEntities entities) {
        mExtendedEntities = entities;
    }

    @Override
    public Long cascadeSave() {
        if (videoInfo != null) {
            videoInfo.cascadeSave();
        }
        return save();
    }

    @Override
    public String getUrl() {
        // TODO - add a helper that returns the first mp4 flavor?
        List<Video.VideoInfo.Flavor> flavors = null;
        if (videoInfo != null) {
            flavors = videoInfo.getFlavors();
        }
        return (flavors != null && flavors.size() > 0) ? flavors.get(0).url : null;
    }

    @Table(name="VideoInfo")
    public static class VideoInfo extends Model {


        List<Flavor> flavors;

        public VideoInfo() {
            super();
        }

        public Long cascadeSave() {
            long retVal = save();
            if (flavors != null && flavors.size() > 0) {
                for (Flavor flavor : flavors) {
                    flavor.setVideoInfo(this);
                    flavor.cascadeSave();
                }
            }
            return retVal;
        }

        public List<Flavor> getFlavors() {
            return getMany(Flavor.class, "VideoInfo");
        }

        @Table(name = "Flavor")
        public static class Flavor extends Model {
            @Column String contentType;
            @Column String url;
            @Column (name = "VideoInfo") Video.VideoInfo mVideoInfo;

            public Flavor() {
                super();
            }

            public final Long cascadeSave() {
                return save();
            }

            public void setVideoInfo(VideoInfo videoInfo) {
                mVideoInfo = videoInfo;
            }
        }
    }

}
*/

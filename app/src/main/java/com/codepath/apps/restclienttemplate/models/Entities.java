package com.codepath.apps.restclienttemplate.models;

import java.util.List;

/**
 * Created by gretel on 9/27/17.
 */

public class Entities {

    Long id;

    List<Media> media;

    public Long getId() {
        return id;
    }

    public List<Media> getMedia() {
        return media;
    }

    public void setMedia(List<Media> media) {
        this.media = media;
    }

}

package com.codepath.apps.restclienttemplate.models;

/**
 * Created by gretel on 9/27/17.
 */

public class Variants {

    String bitRate;
    String contentType;
    String url;

    public String getBitRate() {
        return bitRate;
    }

    public String getContentType() {
        return contentType;
    }

    public String getUrl() {
        return url;
    }

    public void setBitRate(String bitRate) {
        this.bitRate = bitRate;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

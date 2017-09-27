package com.codepath.apps.restclienttemplate.models;

import java.util.List;

/**
 * Created by gretel on 9/27/17.
 */

public class VideoInfo {

    List<Variants> variants;

    public List<Variants> getVariants() {
        return variants;
    }

    public void setVariants(List<Variants> variants) {
        this.variants = variants;
    }
}

package com.cc.tongxundi.bean;

import java.io.Serializable;

public class VideoBean implements Serializable {
    private String description;
    private String contentUrl;
    private int duration;
    private String thumbnailUrl;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getVideoUrl() {
        return contentUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.contentUrl = videoUrl;
    }

    public int getVideoDur() {
        return duration;
    }

    public void setVideoDur(int videoDur) {
        this.duration = videoDur;
    }
}

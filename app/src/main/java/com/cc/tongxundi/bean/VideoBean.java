package com.cc.tongxundi.bean;

import java.io.Serializable;

public class VideoBean implements Serializable {
    private String videoDes;
    private String videoUrl;
    private int videoDur;
    private String videoCover;

    public String getVideoCover() {
        return videoCover;
    }

    public void setVideoCover(String videoCover) {
        this.videoCover = videoCover;
    }

    public String getVideoDes() {
        return videoDes;
    }

    public void setVideoDes(String videoDes) {
        this.videoDes = videoDes;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public int getVideoDur() {
        return videoDur;
    }

    public void setVideoDur(int videoDur) {
        this.videoDur = videoDur;
    }
}

package com.fitness.model;

public class SimpleTrainingModel {
    private int id;
    private String urlYoutobe;
    private String title;
    private String deskripsi;
    private String channel;
    private boolean statusClick;
    private boolean playVideo;

    public String getUrlYoutobe() {
        return urlYoutobe;
    }

    public void setUrlYoutobe(String urlYoutobe) {
        this.urlYoutobe = urlYoutobe;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public boolean isStatusClick() {
        return statusClick;
    }

    public void setStatusClick(boolean statusClick) {
        this.statusClick = statusClick;
    }

    public boolean isPlayVideo() {
        return playVideo;
    }

    public void setPlayVideo(boolean playVideo) {
        this.playVideo = playVideo;
    }
}

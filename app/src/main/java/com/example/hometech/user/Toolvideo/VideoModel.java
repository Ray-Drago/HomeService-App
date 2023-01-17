package com.example.hometech.user.Toolvideo;

public class VideoModel {
    String toolname,toolurl;

    public VideoModel(String toolname, String toolurl) {
        this.toolname = toolname;
        this.toolurl = toolurl;
    }

    public String getToolname() {
        return toolname;
    }

    public String getToolurl() {
        return toolurl;
    }
}

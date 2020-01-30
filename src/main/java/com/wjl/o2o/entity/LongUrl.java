package com.wjl.o2o.entity;

public class LongUrl {
    private String long_url;
    private String action = "long2short";

    public String getLong_url() {
        return long_url;
    }

    public void setLong_url(String long_url) {
        this.long_url = long_url;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public LongUrl(String longUrl){
        this.long_url = longUrl;
    }
}

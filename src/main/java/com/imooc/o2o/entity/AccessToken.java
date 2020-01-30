package com.imooc.o2o.entity;

public class AccessToken {
    private String accessToken;
    private long expireTime;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    public AccessToken(String accessToken, String expiresIn) {
        this.accessToken = accessToken;
        expireTime = System.currentTimeMillis()+Integer.parseInt(expiresIn)*1000;
    }

    public boolean isExpire(){
        return System.currentTimeMillis() > expireTime;
    }

}

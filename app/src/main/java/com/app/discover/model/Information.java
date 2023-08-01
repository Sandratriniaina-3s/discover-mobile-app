package com.app.discover.model;

import java.io.Serializable;

public class Information implements Serializable {

    private String token;
    private String userId;

    public Information() {
    }

    public Information(String token, String userId) {
        this.token = token;
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}

package com.app.discover.model;

public class Comment {

    private String _id;
    private String date;
    private String value;
    private User user;

    public Comment() {
    }

    public Comment(String _id, String date, String value, User user) {
        this._id = _id;
        this.date = date;
        this.value = value;
        this.user = user;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

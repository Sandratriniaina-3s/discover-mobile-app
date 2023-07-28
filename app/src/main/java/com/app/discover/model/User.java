package com.app.discover.model;

public class User {

    private String _id;
    private String fullName;
    private String email;
    private String password;

    public User() {
    }

    public User(String _id, String fullName, String email, String password) {
        this._id = _id;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
    }

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}

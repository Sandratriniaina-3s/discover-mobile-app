package com.app.discover.model;

import java.util.ArrayList;

public class Site {
    private String name;
    private String location;
    private String description;
    private String thumbnail;
    private String[] photos;

    private String[] videos;

    public String[] getVideos() {
        return videos;
    }

    public void setVideos(String[] videos) {
        this.videos = videos;
    }

    public String[] getPhotos() {
        return photos;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    private String _id;

    public String get_id() {
        return _id;
    }
    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}

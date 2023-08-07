package com.app.discover.model;

import java.util.Date;

public class Notification {
    private String _id;
    private String commentId;
    private String createdAt;
    private String[] readBy;
    private String content;
    private String siteId;

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String[] getReadBy() {
        return readBy;
    }

    public void setReadBy(String[] readBy) {
        this.readBy = readBy;
    }
}

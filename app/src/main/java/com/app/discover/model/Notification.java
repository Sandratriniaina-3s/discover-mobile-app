package com.app.discover.model;

import java.util.Date;

public class Notification {
    private String _id;
    private String commentId;
    private Date createdAt;
    private String[] readBy;

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    private String siteId;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private String content;

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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String[] getReadBy() {
        return readBy;
    }

    public void setReadBy(String[] readBy) {
        this.readBy = readBy;
    }
}

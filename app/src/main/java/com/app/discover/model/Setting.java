package com.app.discover.model;

import java.io.Serializable;

public class Setting implements Serializable {

    private Information information;
    private boolean notificationState;

    public Setting() {
    }

    public Setting(Information information) {
        this.information = information;
    }

    public Information getInformation() {
        return information;
    }

    public void setInformation(Information information) {
        this.information = information;
    }

    public boolean getNotificationState() {
        return notificationState;
    }

    public void setNotificationState(boolean notificationState) {
        this.notificationState = notificationState;
    }



}

package com.app.discover.model;

import java.io.Serializable;

public class Setting implements Serializable {

    private Information information;
    // OTHER APP SETTING

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



}

package com.app.discover.controller;

import android.content.Context;

import com.app.discover.model.Information;
import com.app.discover.model.Setting;
import com.app.discover.utils.Serializer;

import java.io.File;

public final class DataManager {

    private static DataManager instance = null;
    private static Setting setting = null;
    private static String filename = "appsetting" ;

    private DataManager() {
        super();
    }

    public static final DataManager getInstance(Context context) {
        if (instance == null) {
            instance = new DataManager();
            retrieveSetting(context);
        }
        return instance;
    }

    private static boolean isPrivateFileExists(Context context) {
            File file = new File(context.getFilesDir(), filename);
            return file.exists();
        }

    public Setting getSetting() {
        return setting;
    }

    public void setSetting(Setting setting) {
        this.setting = setting;
    }

    public void saveSetting(Setting newSetting, Context context){
        setting = newSetting;
        Serializer.serialize(filename, setting, context);
    }

    private static void retrieveSetting(Context context){
        if(isPrivateFileExists(context)){
            setting = (Setting) Serializer.deSerialize(filename, context);
        }
    }


}

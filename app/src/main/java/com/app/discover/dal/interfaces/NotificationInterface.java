package com.app.discover.dal.interfaces;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

public interface NotificationInterface {

    void handleObjectResponse(JSONObject jsonObject);
    void handleArrayResponse(JSONArray jsonArray);
    void handleError(VolleyError volleyError);
}

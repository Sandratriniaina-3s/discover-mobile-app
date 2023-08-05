package com.app.discover.dal.service;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.app.discover.controller.DataManager;
import com.app.discover.dal.interfaces.NotificationInterface;
import com.app.discover.dal.interfaces.SiteInterface;
import com.app.discover.utils.VolleySingleton;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class NotificationService {
    private Context context;
    private DataManager dataManager;

    public NotificationService(Context context) {
        this.context = context;
       dataManager = DataManager.getInstance(context);
    }

    public void getAllNotifications(String url, NotificationInterface callback){
        String reqUrl = url;
        String userId =  dataManager.getSetting().getInformation().getUserId();
        if(userId!=null){
            reqUrl+="?userId="+userId;

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                    Request.Method.GET,
                    reqUrl,
                    null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            callback.handleArrayResponse(response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            callback.handleError(error);
                        }
                    }
            )
            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    Log.i("aaa", dataManager.getSetting().getInformation().getUserId());
                    headers.put("Authorization", "Bearer " + dataManager.getSetting().getInformation().getToken());
                    return headers;
                }
            };

            VolleySingleton.getInstance(context).getRequestQueue().add(jsonArrayRequest);
        }
    }
}

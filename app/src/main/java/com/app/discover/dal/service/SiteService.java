package com.app.discover.dal.service;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.app.discover.dal.interfaces.UserInterface;
import com.app.discover.utils.VolleySingleton;

import org.json.JSONArray;

public class SiteService {

    private Context context;

    public SiteService(Context context) {
        this.context = context;
    }

    public void getAllSite(String url, String search, UserInterface callback){
        String reqUrl = url;
        if(search != null){
            reqUrl+="?search="+search;
        }
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

        );

        VolleySingleton.getInstance(context).getRequestQueue().add(jsonArrayRequest);

    }

}

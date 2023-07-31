package com.app.discover.dal.service;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.app.discover.dal.interfaces.SiteInterface;
import com.app.discover.dal.interfaces.UserInterface;
import com.app.discover.utils.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

public class SiteService {

    private Context context;

    public SiteService(Context context) {
        this.context = context;
    }

    public void getAllSite(String url, String search, SiteInterface callback){
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

    public void getSiteById(String url, String id, SiteInterface callback){
        Log.d("-------------",id);
        String reqUrl = url+"/"+id;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                reqUrl,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.handleObjectResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.handleError(error);
                    }
                }
        );
        VolleySingleton.getInstance(context).getRequestQueue().add(jsonObjectRequest);
    }

}

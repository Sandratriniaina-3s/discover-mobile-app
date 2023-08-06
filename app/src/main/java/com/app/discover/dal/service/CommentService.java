package com.app.discover.dal.service;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.app.discover.controller.DataManager;
import com.app.discover.dal.interfaces.CommentInterface;
import com.app.discover.utils.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CommentService {

    private Context context;
    private DataManager dataManager ;

    public CommentService(Context context) {
        this.context = context;
        dataManager = DataManager.getInstance(context);
    }

    public void getAllComment(String url, CommentInterface callback){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
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
                headers.put("Authorization", "Bearer " + dataManager.getSetting().getInformation().getToken());
                return headers;
            }
        };

        VolleySingleton.getInstance(context).getRequestQueue().add(jsonArrayRequest);

    }

    public void addComment(String url, JSONObject jsonObject, CommentInterface callback){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonObject,
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
        )
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + dataManager.getSetting().getInformation().getToken());
                return headers;
            }
        };

        VolleySingleton.getInstance(context).getRequestQueue().add(jsonObjectRequest);

    }

}

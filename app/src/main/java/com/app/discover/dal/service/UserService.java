package com.app.discover.dal.service;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.app.discover.dal.interfaces.UserInterface;
import com.app.discover.model.User;
import com.app.discover.utils.VolleySingleton;

import org.json.JSONObject;

public class UserService {

    private Context context;

    public UserService(Context context) {
        this.context = context;
    }

    public void addAndGetUser(String url, JSONObject jsonObject, final UserInterface callback){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.handleObjectResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.handleError(error);
            }
        }
        );

        VolleySingleton.getInstance(context).getRequestQueue().add(jsonObjectRequest);

    }

    public void updateUser(String url, JSONObject jsonObject, final UserInterface callback){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.PUT,
                url,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.handleObjectResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.handleError(error);
            }
        }
        );

        VolleySingleton.getInstance(context).getRequestQueue().add(jsonObjectRequest);

    }

    public void getUserById(String url, final UserInterface callback){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.handleObjectResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.handleError(error);
            }
        }
        );

        VolleySingleton.getInstance(context).getRequestQueue().add(jsonObjectRequest);

    }

}

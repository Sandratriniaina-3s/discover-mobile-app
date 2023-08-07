package com.app.discover.controller.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.app.discover.R;
import com.app.discover.adapter.NotificationAdapter;
import com.app.discover.dal.interfaces.NotificationInterface;
import com.app.discover.dal.service.NotificationService;
import com.app.discover.model.Notification;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

public class NotificationFragment extends Fragment {
    private Notification[] notifications;
    private Context context;
    private NotificationService notificationService;
    private RecyclerView recyclerView;
    private Gson gson;
    private NotificationAdapter notificationAdapter;
    private String url;
    private CircularProgressIndicator notificationLoaders;

    public NotificationFragment() {
        // Required empty public constructor
    }

    public static NotificationFragment newInstance(String param1, String param2) {
        NotificationFragment fragment = new NotificationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        init(view);
        getNotifications(url);
        return view;
    }

    private void updateRecyclerView(Context context,Notification[] notifications){
        notificationAdapter = new NotificationAdapter(context,notifications);
        recyclerView.setAdapter(notificationAdapter);
    }

    private void getNotifications(String url){
        notificationService.getAllNotifications(url,new NotificationInterface(){
            @Override
            public void handleObjectResponse(JSONObject jsonObject) {

            }

            @Override
            public void handleArrayResponse(JSONArray jsonArray) {
                notifications = gson.fromJson(jsonArray.toString(),Notification[].class);
                updateRecyclerView(context,notifications);
                notificationLoaders.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void handleError(VolleyError volleyError) {

            }
        });
    }

    private void init(View view){
        notifications = null;
        context = view.getContext();
        notificationService = new NotificationService(context);
        recyclerView = view.findViewById(R.id.notification_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        gson = new Gson();
        notificationAdapter = null;
        url="http://192.168.56.1:8000/notifications";
        notificationLoaders = view.findViewById(R.id.notification_loaders);
        notificationLoaders.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

}
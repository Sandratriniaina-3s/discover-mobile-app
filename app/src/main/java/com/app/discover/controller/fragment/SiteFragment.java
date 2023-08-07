package com.app.discover.controller.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.app.discover.R;
import com.app.discover.adapter.SiteAdapter;
import com.app.discover.controller.DataManager;
import com.app.discover.controller.SocketManager;
import com.app.discover.dal.interfaces.SiteInterface;
import com.app.discover.dal.service.SiteService;
import com.app.discover.model.Site;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import io.socket.client.Socket;

public class SiteFragment extends Fragment {

    private SiteService siteService;
    private Site[] sites;
    private RecyclerView recyclerView;
    private Context context;
    private SiteAdapter siteListAdapter;
    private String url;
    private Gson gson;
    private TextView searchBar;
    private Socket socket;
    private DataManager dataManager;
    private LinearLayout siteContainer;
    private CircularProgressIndicator siteLoader;

    public SiteFragment() {
        // Required empty public constructor
    }

    public static SiteFragment newInstance(String param1, String param2) {
        SiteFragment fragment = new SiteFragment();
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
        View view = inflater.inflate(R.layout.fragment_site, container, false);

        init(view);
        SocketManager.initSocket(view.getContext());

        searchBar.addTextChangedListener(new TextWatcher() {
            private final Handler handler = new Handler();
            private Runnable runnable;
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(runnable!=null){
                    handler.removeCallbacks(runnable);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                handler.postDelayed(()->getSites(url,editable.toString()),300);
                updateRecyclerView(context, sites);
            }
        });

        getSites(url,null);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void init(View view){
        sites = null;
        context = view.getContext();
        siteService = new SiteService(context);
        recyclerView = view.findViewById(R.id.site_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        searchBar = view.findViewById(R.id.searchEditText);
        url = "http://192.168.56.1:8000/sites";
        gson = new Gson();
        siteListAdapter = null;
        socket = SocketManager.getSocket();
        dataManager = DataManager.getInstance(context);
        siteLoader = view.findViewById(R.id.site_loaders);
        siteContainer = view.findViewById(R.id.site_container);
        siteContainer.setVisibility(View.GONE);
        siteLoader.setVisibility(View.VISIBLE);
    }

    private void updateRecyclerView(Context context, Site[] sites){
        siteListAdapter = new SiteAdapter(context, sites);
        recyclerView.setAdapter(siteListAdapter);
    }

    private void getSites(String url,String search){

        siteService.getAllSite(url, search,new SiteInterface() {
            @Override
            public void handleObjectResponse(JSONObject jsonObject) {

            }

            @Override
            public void handleArrayResponse(JSONArray jsonArray) {
                sites = gson.fromJson(jsonArray.toString(), Site[].class);
                updateRecyclerView(context, sites);
                siteContainer.setVisibility(View.VISIBLE);
                siteLoader.setVisibility(View.GONE);
            }

            @Override
            public void handleError(VolleyError volleyError) {

            }
        });
    }

}
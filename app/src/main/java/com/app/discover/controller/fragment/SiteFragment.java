package com.app.discover.controller.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.app.discover.R;
import com.app.discover.adapter.SiteListAdapter;
import com.app.discover.dal.interfaces.UserInterface;
import com.app.discover.dal.service.SiteService;
import com.app.discover.model.Site;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SiteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SiteFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private SiteService siteService;
    private Site[] sites;
    private RecyclerView recyclerView;

    private Context context;
    private SiteListAdapter siteListAdapter;
    private String url;
    private Gson gson;

    public SiteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SiteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SiteFragment newInstance(String param1, String param2) {
        SiteFragment fragment = new SiteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_site, container, false);

        init(view);

        TextView searchBar = view.findViewById(R.id.searchEditText);

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
        url = "http://192.168.56.1:8000/sites";
        gson = new Gson();
        siteListAdapter = null;
    }

    private void updateRecyclerView(Context context, Site[] sites){
        siteListAdapter = new SiteListAdapter(context, sites);
        recyclerView.setAdapter(siteListAdapter);
    }

    private void getSites(String url,String search){
        siteService.getAllSite(url, search,new UserInterface() {
            @Override
            public void handleObjectResponse(JSONObject jsonObject) {

            }

            @Override
            public void handleArrayResponse(JSONArray jsonArray) {
                sites = gson.fromJson(jsonArray.toString(), Site[].class);
                updateRecyclerView(context, sites);
            }

            @Override
            public void handleError(VolleyError volleyError) {

            }
        });
    }

}
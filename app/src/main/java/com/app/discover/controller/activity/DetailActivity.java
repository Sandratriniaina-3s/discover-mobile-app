package com.app.discover.controller.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.app.discover.R;
import com.app.discover.controller.fragment.CommentFragment;
import com.app.discover.controller.fragment.PhotoFragment;
import com.app.discover.controller.fragment.VideoFragment;
import com.app.discover.dal.interfaces.SiteInterface;
import com.app.discover.dal.service.SiteService;
import com.app.discover.model.Site;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

public class DetailActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private Context context;
    private SiteService siteService;
    private Gson gson;
    public static String siteId;
    private String url ;
    private Site site;
    private MaterialToolbar toolbar;
    private CircularProgressIndicator siteDetailLoaders;
    private LinearLayout detailContainer;
    private String apiUrl = "http://192.168.56.1:8000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent i = getIntent();
        init();
        siteId = i.getStringExtra("siteId");

        getSiteById(url,siteId);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void init(){
        tabLayout = findViewById(R.id.detail_tab_layout);
        context = this;
        siteService = new SiteService(context);
        gson=new Gson();
        toolbar = findViewById(R.id.d_appbar);
        url = "http://192.168.56.1:8000/sites";
        siteDetailLoaders = findViewById(R.id.site_detail_loaders);
        detailContainer = findViewById(R.id.detail_container);
        siteDetailLoaders.setVisibility(View.VISIBLE);
        detailContainer.setVisibility(View.GONE);
    }

    private void launchFragmentWithData(Fragment fragment,String dataName, String[] data) {
        Bundle args = new Bundle();
        args.putStringArray(dataName,data);
        fragment.setArguments(args);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.tab_content_layout, fragment);
        fragmentTransaction.commit();
    }


    public void getSiteById(String url,String id){
        siteService.getSiteById(url, id, new SiteInterface() {
            @Override
            public void handleObjectResponse(JSONObject jsonObject) {
                site = gson.fromJson(jsonObject.toString(),Site.class);

                launchFragmentWithData(new PhotoFragment(),"PHOTOS",site.getPhotos());

                tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        switch (tab.getPosition()){
                            case 0:
                                launchFragmentWithData(new PhotoFragment(),"PHOTOS",site.getPhotos());
                                break;
                            case 1:
                                launchFragmentWithData(new VideoFragment(),"VIDEOS",site.getVideos());
                                break;
                            case 2:
                                launchFragmentWithData(new CommentFragment(),"SITES",new String[]{site.get_id()});
                                break;
                        }
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {

                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }
                });

                setDetailView(site);

                siteDetailLoaders.setVisibility(View.GONE);
                detailContainer.setVisibility(View.VISIBLE);
            }

            @Override
            public void handleArrayResponse(JSONArray jsonArray) {

            }

            @Override
            public void handleError(VolleyError volleyError) {

            }
        });
    }

    private void setDetailView(Site site){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ImageView thumbnail = findViewById(R.id.thumbnail);
                TextView name = findViewById(R.id.name);
                TextView description = findViewById(R.id.description);
                TextView location = findViewById(R.id.location);
                Picasso.get().load(apiUrl+site.getThumbnail()).into(thumbnail);
                name.setText(site.getName());
                description.setText(site.getDescription());
                location.setText(site.getLocation());
            }
        });
    }
}
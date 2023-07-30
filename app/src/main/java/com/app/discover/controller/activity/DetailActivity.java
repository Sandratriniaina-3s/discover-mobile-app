package com.app.discover.controller.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

import com.app.discover.R;
import com.app.discover.controller.fragment.CommentFragment;
import com.app.discover.controller.fragment.PhotoFragment;
import com.app.discover.controller.fragment.VideoFragment;
import com.google.android.material.tabs.TabLayout;

public class DetailActivity extends AppCompatActivity {

    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        init();

        launchFragment(new PhotoFragment());

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
               switch (tab.getPosition()){
                    case 0:
                        launchFragment(new PhotoFragment());
                        break;
                    case 1:
                        launchFragment(new VideoFragment());
                        break;
                    case 2:
                        launchFragment(new CommentFragment());
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

    }

    private void init(){
        tabLayout = findViewById(R.id.detail_tab_layout);
    }

    private void launchFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.tab_content_layout, fragment);
        fragmentTransaction.commit();
    }

}
package com.app.discover.controller.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.app.discover.R;
import com.app.discover.controller.SocketManager;
import com.app.discover.controller.fragment.NotificationFragment;
import com.app.discover.controller.fragment.SettingFragment;
import com.app.discover.controller.fragment.SiteFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigation;
    private TextView activeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SocketManager.initSocket();
        init();

        launchFragment(new SiteFragment(), "Sites");

        // Initialize Picasso with the application context
        Picasso.setSingletonInstance(new Picasso.Builder(this).build());

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.site_item){
                    launchFragment(new SiteFragment(), "Sites");
                } else if (item.getItemId() == R.id.notification_item) {
                    launchFragment(new NotificationFragment(), "Notifications");
                }else {
                    launchFragment(new SettingFragment(), "Paramètres");
                }
                return true;
            }
        });
    }

    private void init(){
        bottomNavigation = findViewById(R.id.bottom_navigation);
        activeFragment = findViewById(R.id.active_fragment_name);

    }

    private void launchFragment(Fragment fragment, String fragmentName) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
        activeFragment.setText(fragmentName);
    }

}
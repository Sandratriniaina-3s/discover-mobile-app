package com.app.discover.controller.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import com.app.discover.R;
import com.app.discover.controller.DataManager;
import com.app.discover.model.Setting;
import com.google.android.material.snackbar.Snackbar;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DELAY = 3000;
    private Context context;
    private DataManager dataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        context = this;
        dataManager = DataManager.getInstance(context);

        if (isNetworkAvailable()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    if(dataManager.getSetting() == null || dataManager.getSetting().getInformation() == null){
                        Setting setting = new Setting();
                        setting.setNotificationState(true);
                        dataManager.saveSetting(setting, context);
                        startNewActivity(LoginActivity.class);
                    }
                    else {
                        startNewActivity(MainActivity.class);
                    }

                }
            }, SPLASH_DELAY);
        } else {
            Snackbar.make(findViewById(android.R.id.content), "Pas de connexion Internet.\nVeuillez vérifier vos paramètres réseau.", Snackbar.LENGTH_INDEFINITE)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            finish();
                        }
                    }).show();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = null;
        if (connectivityManager != null) {
            activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        }
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void startNewActivity(Class activity){
        Intent intent = new Intent(context, activity);
        startActivity(intent);
        finish();
    }

}
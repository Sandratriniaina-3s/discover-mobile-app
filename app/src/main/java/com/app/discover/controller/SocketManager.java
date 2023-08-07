package com.app.discover.controller;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.app.discover.R;
import com.app.discover.model.Setting;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class SocketManager {

    private final String apiUrl = "https://discover-api.onrender.com";
    private static Socket socket;

    private static DataManager dataManager;

    public static Socket getSocket() {
        return socket;
    }


    public static void initSocket(Context context) {

        dataManager = DataManager.getInstance(context);
        Setting setting = dataManager.getSetting();

        if(setting.getNotificationState()){
            try {
                IO.Options options = new IO.Options();
                options.forceNew = true;
                socket = IO.socket("https://discover-api.onrender.com", options);
                socket.on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
                        Exception e = (Exception) args[0];
                        e.printStackTrace();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

            socket.connect();

            socket.on(Socket.EVENT_CONNECT, args -> {

            });

            socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    // Connected to the server
                }
            }).on("response", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    String response = (String) args[0];
                    // Handle the response from the server
                }
            });

            socket.on("notification", new Emitter.Listener() {
                @Override
                public void call(Object... args) {

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "Comment");
                    builder.setContentTitle("Nouveau commentaire");
                    builder.setContentText("Un nouveau commentaire a été ajouté");
                    builder.setSmallIcon(R.drawable.logo);
                    builder.setAutoCancel(true);

                    NotificationManagerCompat manager = NotificationManagerCompat.from(context);
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                        NotificationChannel channel = new NotificationChannel("Comment", "Discover", NotificationManager.IMPORTANCE_DEFAULT);
                        NotificationManager nManager = context.getSystemService(NotificationManager.class);
                        nManager.createNotificationChannel(channel);
                    }
                    manager.notify(1, builder.build());
                }
            });
        }



    }
}

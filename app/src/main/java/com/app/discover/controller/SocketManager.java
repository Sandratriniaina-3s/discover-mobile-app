package com.app.discover.controller;

import android.util.Log;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class SocketManager {

    private final String apiUrl = "http://192.168.1.101:8000";
    private static Socket socket;

    public static Socket getSocket() {
        return socket;
    }


    public static void initSocket(){
        try {
            IO.Options options = new IO.Options();
            options.forceNew = true;
            socket = IO.socket("http://192.168.1.101:8000",options); // Replace with your server's IP address
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
           Log.d("-------------------","connected to server");
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

        socket.on("messager", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.d("-----------------","Message received");
            }
        });

    }
}

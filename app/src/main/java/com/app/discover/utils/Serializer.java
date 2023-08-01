package com.app.discover.utils;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Serializer {

    public static void serialize(String filename, Object object, Context context){
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(filename,Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream;
            try {
                objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(object);
                objectOutputStream.flush();
                objectOutputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object deSerialize(String filename, Context context){
        Object object = null;
        try {
            FileInputStream fileInputStream = context.openFileInput(filename);
            ObjectInputStream objectInputStream;
            try {
                objectInputStream = new ObjectInputStream(fileInputStream);
                try {
                    object = objectInputStream.readObject();
                    objectInputStream.close();
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return object;
    }

}

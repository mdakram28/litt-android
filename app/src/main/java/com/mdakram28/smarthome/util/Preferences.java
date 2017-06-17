package com.mdakram28.smarthome.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

/**
 * Created by mdakram28 on 12/6/17.
 */

public class Preferences {
    public static final String SERVER = "192.168.0.102";
    public static final String websocketServer = String.format("ws://%s:8000/", SERVER);
    public static final String httpServer = String.format("http://%s:8000", SERVER);
    public static void setAccessToken(@NonNull Context context, String token) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("ACCESSTOKEN", token);
        editor.apply();
    }

    public static String getAccessToken(@NonNull Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        return sharedPreferences.getString("ACCESSTOKEN", null);
    }

}

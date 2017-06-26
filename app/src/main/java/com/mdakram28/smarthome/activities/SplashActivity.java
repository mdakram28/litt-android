package com.mdakram28.smarthome.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mdakram28.smarthome.R;
import com.mdakram28.smarthome.listeners.SocketConnectedListener;
import com.mdakram28.smarthome.listeners.SocketInitListener;
import com.mdakram28.smarthome.util.Preferences;
import com.mdakram28.smarthome.websocket.Socket;

import java.io.IOException;

public class SplashActivity extends AppCompatActivity implements SocketInitListener{
    Socket socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        socket = Socket.getSocket();
        Socket.setInitListener(SplashActivity.this);
        socket.init();

        String appToken = Preferences.getAccessToken(this);
        if(appToken != null){
            try {
                socket.connect(appToken);
                socket.addConnectedListener(new SocketConnectedListener() {
                    @Override
                    public void onConnect() {
                        socket.requestDeviceConfig();
                        socket.requestRoomConfig();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
        }
    }

    @Override
    public void onInit() {
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
    }
}

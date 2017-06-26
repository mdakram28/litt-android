package com.mdakram28.smarthome.websocket;

import android.util.Log;

import com.google.gson.Gson;
import com.mdakram28.smarthome.listeners.SocketDataListener;
import com.mdakram28.smarthome.listeners.UIChangeListener;
import com.mdakram28.smarthome.websocket.models.Control;
import com.mdakram28.smarthome.websocket.models.Notification;
import com.mdakram28.smarthome.websocket.models.Room;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.mdakram28.smarthome.websocket.NotificationPersistence.notifications;

/**
 * Created by mdakram28 on 13/6/17.
 */

public class ControlsPersistence {
    public static List<Control> controls = new ArrayList<>();

    public static void init(Socket socket) {
        socket.addTopicListener("deviceConfig", new SocketDataListener() {
            @Override
            public void onDataReceived(String type, Object data) {
                try {
                    controls.clear();
                    Gson gson = new Gson();
                    JSONArray jsonArray = (JSONArray) data;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Control control = gson.fromJson(jsonArray.getJSONObject(i).toString(), Control.class);
                        controls.add(control);
                    }
                    System.out.println("Controls new sie = "+controls.size());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        socket.addTopicListener("stateChange", new SocketDataListener() {
            @Override
            public void onDataReceived(String type, Object data) {
                JSONObject jsonObject = (JSONObject) data;
                try {
                    String device = jsonObject.getString("device");
                    String state = jsonObject.getString("state");
                    updateControl(device, state);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static boolean isInit(){
        return controls.size() != 0;
    }

    public static void updateControl(String device, String state){
        for(Control c : controls){
            if (Objects.equals(c.deviceId, device)){
                controls.get(controls.indexOf(c)).state = state;
            }
        }
    }
}

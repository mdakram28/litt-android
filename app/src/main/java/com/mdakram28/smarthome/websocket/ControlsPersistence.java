package com.mdakram28.smarthome.websocket;

import android.util.Log;

import com.google.gson.Gson;
import com.mdakram28.smarthome.listeners.SocketDataListener;
import com.mdakram28.smarthome.listeners.UIChangeListener;
import com.mdakram28.smarthome.websocket.models.Control;
import com.mdakram28.smarthome.websocket.models.Notification;
import com.mdakram28.smarthome.websocket.models.Room;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.mdakram28.smarthome.websocket.NotificationPersistence.notifications;

/**
 * Created by mdakram28 on 13/6/17.
 */

public class ControlsPersistence {
    public static List<Control> controls = new ArrayList<>();
    public static List<UIChangeListener> uiChangeListeners = new ArrayList<UIChangeListener>();

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
                    for (final UIChangeListener listener : uiChangeListeners) {
                        listener.updateUI();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

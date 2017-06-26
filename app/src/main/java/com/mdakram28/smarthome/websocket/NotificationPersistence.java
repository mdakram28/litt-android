package com.mdakram28.smarthome.websocket;

import com.google.gson.Gson;
import com.mdakram28.smarthome.listeners.UIChangeListener;
import com.mdakram28.smarthome.listeners.SocketDataListener;
import com.mdakram28.smarthome.websocket.models.Notification;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by mdakram28 on 13/6/17.
 */

public class NotificationPersistence {
    public static List<Notification> notifications = new ArrayList<Notification>();
    public static List<UIChangeListener> uiChangeListeners = new ArrayList<UIChangeListener>();

    public static void init(Socket socket) {
        notifications.add(new Notification("Test notification", new Date(), "Messagee"));
        notifications.add(new Notification("Test notification 2", new Date(), "Messagee"));
        notifications.add(new Notification("Test notification 45324", new Date(), "Messagee"));
        notifications.add(new Notification("Test notification eefdsfdsg", new Date(), "Messagee"));
        socket.addTopicListener("notifications", new SocketDataListener() {
            @Override
            public void onDataReceived(String type, Object data) {
                try {
                    notifications.clear();
                    Gson gson = new Gson();
                    JSONArray jsonArray = (JSONArray) data;
                } catch (Exception e) {
                    e.printStackTrace();
                }

                for (final UIChangeListener listener : uiChangeListeners) {
                    listener.updateUI();
                }
            }
        });
    }

    public static boolean isInit(){
        return notifications.size() != 0;
    }
}

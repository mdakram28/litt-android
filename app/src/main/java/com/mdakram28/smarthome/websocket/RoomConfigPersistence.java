package com.mdakram28.smarthome.websocket;

import android.util.Log;

import com.google.gson.Gson;
import com.mdakram28.smarthome.listeners.UIChangeListener;
import com.mdakram28.smarthome.listeners.SocketDataListener;
import com.mdakram28.smarthome.websocket.models.Room;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mdakram28 on 13/6/17.
 */

public class RoomConfigPersistence {
    public static List<Room> rooms = new ArrayList<Room>();
    public static List<UIChangeListener> uiChangeListeners = new ArrayList<UIChangeListener>();

    public static void init(Socket socket) {
        socket.addTopicListener("roomConfig", new SocketDataListener() {
            @Override
            public void onDataReceived(String type, Object data) {
                try {
                    rooms.clear();
                    Gson gson = new Gson();
                    JSONArray jsonArray = (JSONArray) data;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Room room = gson.fromJson(jsonArray.getJSONObject(i).toString(), Room.class);
                        Log.d("VIEW", room.toString());
                        rooms.add(room);
                    }
                    for (final UIChangeListener listener : uiChangeListeners) {
                        listener.updateUI();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static boolean isInit(){
        return rooms.size() != 0;
    }
}

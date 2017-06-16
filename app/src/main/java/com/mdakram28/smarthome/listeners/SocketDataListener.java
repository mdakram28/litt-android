package com.mdakram28.smarthome.listeners;

import org.json.JSONObject;

/**
 * Created by mdakram28 on 13/6/17.
 */

public interface SocketDataListener {
    public void onDataReceived(String type, Object data);
}

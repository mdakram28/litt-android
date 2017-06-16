package com.mdakram28.smarthome.websocket.models;

/**
 * Created by mdakram28 on 13/6/17.
 */

public class SocketRequest {
    String command;
    String params;

    public SocketRequest(String command, String params) {
        this.command = command;
        this.params = params;
    }
}

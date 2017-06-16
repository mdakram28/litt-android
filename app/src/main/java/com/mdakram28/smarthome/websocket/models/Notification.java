package com.mdakram28.smarthome.websocket.models;

import java.util.Date;

/**
 * Created by mdakram28 on 14/6/17.
 */

public class Notification {
    public String message;
    public Date date;
    public String type;

    public Notification(String message, Date date, String type) {
        this.message = message;
        this.date = date;
        this.type = type;
    }
}

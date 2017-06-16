package com.mdakram28.smarthome.websocket.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mdakram28 on 14/6/17.
 */

public class Control {
    @SerializedName("device")
    public String deviceId;

    public String deviceType;

    @SerializedName("control_type")
    public String controlType;

    @SerializedName("roomid")
    public String roomId;
}

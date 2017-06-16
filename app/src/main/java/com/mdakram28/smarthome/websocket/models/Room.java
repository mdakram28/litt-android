package com.mdakram28.smarthome.websocket.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mdakram28 on 13/6/17.
 */

public class Room {
    public long id;

    @SerializedName("room_name")
    public String name;

    public int floor;

    public Room(long id, String name, int floor) {
        this.id = id;
        this.name = name;
        this.floor = floor;
    }

    public Room() {
    }

    @Override
    public String toString(){
        return "Room[id=" +id+
                ",name='" +name+
                "',floor=" +floor+
                "]";
    }
}

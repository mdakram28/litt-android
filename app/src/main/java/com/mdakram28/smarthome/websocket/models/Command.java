package com.mdakram28.smarthome.websocket.models;

import java.util.Objects;

/**
 * Created by mdakram28 on 14/6/17.
 */

public class Command {
    public String command = "deviceAction";
    public Params params;

    public Command(String device,String command){
        params = new Params();
        params.command = command;
        params.device = device;
    }

    public class Params{
        public String device;
        public String command;
    }
}

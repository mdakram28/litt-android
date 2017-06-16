package com.mdakram28.smarthome.listeners;

/**
 * Created by mdakram28 on 14/6/17.
 */

public interface ControlStateListener {
    public void controlStateReceived(String device, String state);
}

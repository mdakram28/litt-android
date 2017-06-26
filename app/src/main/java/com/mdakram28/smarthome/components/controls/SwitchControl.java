package com.mdakram28.smarthome.components.controls;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;

import com.mdakram28.smarthome.R;
import com.mdakram28.smarthome.listeners.ControlStateListener;
import com.mdakram28.smarthome.websocket.Socket;
import com.mdakram28.smarthome.websocket.models.Control;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mdakram28 on 14/6/17.
 */

public class SwitchControl extends RelativeLayout implements CompoundButton.OnCheckedChangeListener, ControlStateListener, View.OnTouchListener {

    @BindView(R.id.toggleButton_switch)
    Switch tbSwitch;
    Control control;
    Socket socket;
    boolean isChecked = false;

    public SwitchControl(Context context, Control control) {
        super(context);
        this.control = control;
        this.isChecked = Objects.equals(control.state, "1");
        initializeViews(context);
    }

    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.control_switch, this);
        ButterKnife.bind(this);
        tbSwitch.setChecked(isChecked);
        tbSwitch.setOnCheckedChangeListener(this);
        tbSwitch.setOnTouchListener(this);
        socket = Socket.getSocket();
        socket.setStateListener(control, this);
        System.out.println("redrawn");
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        System.out.println("Changed : " + isChecked);
        if (tbSwitch.getTag() != null) {
            tbSwitch.setTag(null);
        } else {
            socket.sendControlCommand(control.deviceId, isChecked ? "1" : "0");
            this.isChecked = isChecked;
        }
    }

    @Override
    public void controlStateReceived(String device, String state) {
        isChecked = state.equals("1");
        this.post(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("State received = " + isChecked);
                    tbSwitch.setTag("AUTO");
                    tbSwitch.setChecked(isChecked);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        tbSwitch.setTag(null);
        return false;
    }
}

package com.mdakram28.smarthome.components.controls;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mdakram28.smarthome.R;
import com.mdakram28.smarthome.listeners.ControlStateListener;
import com.mdakram28.smarthome.websocket.Socket;
import com.mdakram28.smarthome.websocket.models.Control;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mdakram28 on 14/6/17.
 */

public class ValueControl extends RelativeLayout implements ControlStateListener, View.OnClickListener {

    @BindView(R.id.textView_value)
    TextView tvValue;

    @BindView(R.id.button_dec)
    Button buttonDec;

    @BindView(R.id.button_inc)
    Button buttonInc;

    Control control;
    Socket socket;

    int value = 0;

    public ValueControl(Context context, Control control) {
        super(context);
        this.control = control;
        this.value = Integer.parseInt(control.state);
        initializeViews(context);
    }

    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.control_value, this);
        ButterKnife.bind(this);
        socket = Socket.getSocket();
        socket.setStateListener(control, this);
        buttonDec.setOnClickListener(this);
        buttonInc.setOnClickListener(this);
        tvValue.setText(Integer.toString(value));
    }

    @Override
    public void controlStateReceived(String device, String state) {
        value = Integer.parseInt(state);
        System.out.println("Received value = " + value);
        this.post(new Runnable() {
            @Override
            public void run() {
                tvValue.setText(Integer.toString(value));
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == buttonDec) {
            value--;
            tvValue.setText(Integer.toString(value));
        } else if (v == buttonInc) {
            value++;
            tvValue.setText(Integer.toString(value));
        }
        socket.sendControlCommand(control.deviceId, value + "");
    }
}

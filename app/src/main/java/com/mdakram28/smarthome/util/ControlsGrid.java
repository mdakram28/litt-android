package com.mdakram28.smarthome.util;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;

import com.mdakram28.smarthome.components.controls.SwitchControl;
import com.mdakram28.smarthome.components.controls.ValueControl;
import com.mdakram28.smarthome.websocket.ControlsPersistence;
import com.mdakram28.smarthome.websocket.models.Control;

import java.util.List;

/**
 * Created by mdakram28 on 14/6/17.
 */

public class ControlsGrid {
    GridLayout layout;
    Context context;

    public ControlsGrid(Context context, GridLayout layout) {
        this.layout = layout;
        this.context = context;
    }

    public void populate(String roomId) {
        layout.removeAllViews();
        System.out.println("Populating controls");
        while (ControlsPersistence.controls.size() == 0) ;
        System.out.println("Downloaded controls");
        List<Control> controls = ControlsPersistence.controls;
        int i = 0;
        for (Control control : controls) {
            if (control.roomId.equals(roomId)) {
                View view = null;
                if (control.controlType.equalsIgnoreCase("switch")) {
                    view = new SwitchControl(context, control);
                }else if(control.controlType.equalsIgnoreCase("variablesw")){
                    view = new ValueControl(context, control);
                }
                GridLayout.Spec rowSpan = GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL);
                GridLayout.Spec colspan = GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL);
                GridLayout.LayoutParams gridParam = new GridLayout.LayoutParams(rowSpan, colspan);
                layout.addView(view, gridParam);
            }
        }
    }
}

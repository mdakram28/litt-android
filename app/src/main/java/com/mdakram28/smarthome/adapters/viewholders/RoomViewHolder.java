package com.mdakram28.smarthome.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.mdakram28.smarthome.R;
import com.mdakram28.smarthome.websocket.models.Room;

/**
 * Created by mdakram28 on 13/6/17.
 */

public class RoomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView _tvName;
    private AdapterView.OnItemClickListener itemClickListener;
    private AdapterView parent;
    private int position;

    public RoomViewHolder(View itemView, AdapterView.OnItemClickListener itemClickListener) {
        super(itemView);
        _tvName = (TextView) itemView.findViewById(R.id.textView_name);
        this.itemClickListener = itemClickListener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (itemClickListener != null) itemClickListener.onItemClick(parent, v, position, position);
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setRoom(Room room) {
        _tvName.setText(room.name);
    }
}

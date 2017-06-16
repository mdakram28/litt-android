package com.mdakram28.smarthome.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.mdakram28.smarthome.R;
import com.mdakram28.smarthome.websocket.models.Notification;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mdakram28 on 13/6/17.
 */

public class NotificationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.textView_notification)
    TextView _tvNotification;

    @BindView(R.id.textView_time)
    TextView _tvTime;

    private AdapterView.OnItemClickListener itemClickListener;
    private AdapterView parent;
    private int position;

    public NotificationViewHolder(View itemView, AdapterView.OnItemClickListener itemClickListener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
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

    public void setNotification(Notification notification) {
        _tvNotification.setText(notification.message);
        _tvTime.setText(notification.date.toString());
    }
}

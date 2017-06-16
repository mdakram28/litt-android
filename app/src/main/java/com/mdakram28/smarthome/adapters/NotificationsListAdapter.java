package com.mdakram28.smarthome.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.mdakram28.smarthome.R;
import com.mdakram28.smarthome.adapters.viewholders.NotificationViewHolder;
import com.mdakram28.smarthome.websocket.models.Notification;

import java.util.List;

/**
 * Created by mdakram28 on 13/6/17.
 */

public class NotificationsListAdapter extends RecyclerView.Adapter<NotificationViewHolder> {

    private LayoutInflater mInflater;
    private AdapterView.OnItemClickListener mClickListener;
    private List<Notification> mData;

    public NotificationsListAdapter(Context context, List<Notification> data){
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    public NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_notification, parent, false);
        NotificationViewHolder viewHolder = new NotificationViewHolder(view, mClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NotificationViewHolder holder, int position) {
        Notification notification = mData.get(position);
        holder.setNotification(notification);
        holder.setPosition(position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public Notification getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(AdapterView.OnItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}

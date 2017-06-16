package com.mdakram28.smarthome.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.mdakram28.smarthome.R;
import com.mdakram28.smarthome.adapters.viewholders.RoomViewHolder;
import com.mdakram28.smarthome.websocket.models.Room;

import java.util.List;

/**
 * Created by mdakram28 on 13/6/17.
 */

public class RoomsListAdapter extends RecyclerView.Adapter<RoomViewHolder> {

    private LayoutInflater mInflater;
    private AdapterView.OnItemClickListener mClickListener;
    private List<Room> mData;

    public RoomsListAdapter(Context context, List<Room> data){
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    public RoomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_room, parent, false);
        RoomViewHolder viewHolder = new RoomViewHolder(view, mClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RoomViewHolder holder, int position) {
        Room room = mData.get(position);
        holder.setRoom(room);
        holder.setPosition(position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public Room getItem(int id) {
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

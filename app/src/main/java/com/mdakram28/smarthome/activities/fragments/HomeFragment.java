package com.mdakram28.smarthome.activities.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.mdakram28.smarthome.R;
import com.mdakram28.smarthome.activities.ControlsActivity;
import com.mdakram28.smarthome.adapters.IconsGridAdapter;
import com.mdakram28.smarthome.adapters.RoomsListAdapter;
import com.mdakram28.smarthome.listeners.UIChangeListener;
import com.mdakram28.smarthome.websocket.RoomConfigPersistence;
import com.mdakram28.smarthome.websocket.Socket;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mdakram28 on 12/6/17.
 */

public class HomeFragment extends Fragment implements UIChangeListener, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.recyclerView_rooms)
    RecyclerView _rvRooms;

    @BindView(R.id.recyclerView_iconsGrid)
    RecyclerView _rvIconsGrid;

    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    IconsGridAdapter iconsGridAdapter;
    RoomsListAdapter roomsListAdapter;
    Socket socket;

    public HomeFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        socket = Socket.getSocket();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("TAG", "on create view");
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this,view);

        roomsListAdapter = new RoomsListAdapter(getContext(), RoomConfigPersistence.rooms);
        roomsListAdapter.setClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getContext(),ControlsActivity.class);
                i.putExtra("roomid", RoomConfigPersistence.rooms.get(position).id+"");
                startActivity(i);
            }
        });
        _rvRooms.setLayoutManager(new GridLayoutManager(getContext(), 1));
        _rvRooms.setAdapter(roomsListAdapter);

        iconsGridAdapter = new IconsGridAdapter(getContext(), RoomConfigPersistence.rooms);

        iconsGridAdapter.setClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        _rvIconsGrid.setLayoutManager(new GridLayoutManager(getContext(), 3));
        _rvIconsGrid.setAdapter(iconsGridAdapter);

        RoomConfigPersistence.uiChangeListeners.add(this);

        swipeRefreshLayout.setOnRefreshListener(this);

        return view;
    }

    @Override
    public void updateUI() {

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                iconsGridAdapter.notifyDataSetChanged();
                roomsListAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onRefresh() {
        try {
            socket.requestDeviceConfig();
            socket.requestRoomConfig();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

package com.mdakram28.smarthome.activities.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mdakram28.smarthome.R;
import com.mdakram28.smarthome.adapters.NotificationsListAdapter;
import com.mdakram28.smarthome.listeners.UIChangeListener;
import com.mdakram28.smarthome.websocket.NotificationPersistence;
import com.mdakram28.smarthome.websocket.Socket;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mdakram28 on 12/6/17.
 */

public class NotificationsFragment extends Fragment implements UIChangeListener {
    @BindView(R.id.recyclerView_notifications)
    RecyclerView _rvNotifications;

    NotificationsListAdapter notificationsListAdapter;
    Socket socket;

    public NotificationsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("TAG", "on create view");
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        ButterKnife.bind(this,view);

        notificationsListAdapter = new NotificationsListAdapter(getContext(), NotificationPersistence.notifications);
        _rvNotifications.setLayoutManager(new GridLayoutManager(getContext(), 1));
        _rvNotifications.setAdapter(notificationsListAdapter);

        NotificationPersistence.uiChangeListeners.add(this);

        return view;
    }

    @Override
    public void updateUI() {

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                notificationsListAdapter.notifyDataSetChanged();
            }
        });
    }
}

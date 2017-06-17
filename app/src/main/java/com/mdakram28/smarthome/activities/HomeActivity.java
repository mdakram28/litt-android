package com.mdakram28.smarthome.activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.mdakram28.smarthome.R;
import com.mdakram28.smarthome.activities.fragments.SettingsActivity;
import com.mdakram28.smarthome.util.Preferences;
import com.mdakram28.smarthome.activities.fragments.HomeFragment;
import com.mdakram28.smarthome.activities.fragments.NotificationsFragment;
import com.mdakram28.smarthome.adapters.ViewPagerAdapter;
import com.mdakram28.smarthome.websocket.Socket;
import com.mdakram28.smarthome.listeners.SocketConnectedListener;

import java.io.IOException;

public class HomeActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int[] tabIcons = {
            R.drawable.selector_home,
            R.drawable.selector_home,
            R.drawable.selector_home
    };
    Socket socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        socket = Socket.getSocket();
        socket.init();
        try {
            socket.connect(Preferences.getAccessToken(this));
            socket.addConnectedListener(new SocketConnectedListener() {
                @Override
                public void onConnect() {
                    try {
                        Thread.sleep(1000);
                        socket.requestDeviceConfig();
                        socket.requestRoomConfig();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[0]);
        tabLayout.getTabAt(2).setIcon(tabIcons[0]);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_settings:
                Intent i = new Intent(this, SettingsActivity.class);
                startActivity(i);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        HomeFragment homeFragment = new HomeFragment();
        adapter.addFrag(homeFragment, "Home");

        adapter.addFrag(new NotificationsFragment(), "Notifications");

        homeFragment = new HomeFragment();
        adapter.addFrag(homeFragment, "Home");
        viewPager.setAdapter(adapter);
    }
}

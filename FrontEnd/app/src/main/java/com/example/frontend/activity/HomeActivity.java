package com.example.frontend.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.frontend.R;
import com.example.frontend.api.RequestApi;
import com.example.frontend.config.Constant;
import com.example.frontend.fragment.ChatListFragment;
import com.example.frontend.fragment.CommunityFragment;
import com.example.frontend.fragment.SettingFragment;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import io.socket.client.IO;
import io.socket.client.Socket;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static Socket SOCKET;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        connectSocket();
        JSONObject body = new JSONObject();
        try {
            RequestApi.sendSocketRequest("api/doubleChat/connectWithServer", this,body);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        Toolbar toolbar = findViewById(R.id.toolbar); //Ignore red line errors
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.open_nav,
                R.string.close_nav
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new ChatListFragment())
                    .commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
    }

    private void connectSocket () {
        try {
            SOCKET = IO.socket(Constant.URL+"?__sails_io_sdk_version=0.11.0");
            listenSocketEvent(SOCKET);
            SOCKET.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void listenSocketEvent(Socket socket){
        socket.on(Socket.EVENT_CONNECT, args -> Log.d("SocketIO", "connect"));
        socket.on(Socket.EVENT_CONNECT_ERROR, args -> {
            Exception e = (Exception) args[0];
            Log.d("Socket ConnectError", e.getMessage());
        });

        socket.on("message", args -> {
            Log.d("Socket Message", Arrays.toString(args));
        });

        socket.on("newChatRoom", args -> {
            Log.d("Socket NewChatRoom", Arrays.toString(args));
        });

        socket.on("receiveMessage", args -> {
            Log.d("Socket ReceiveMessage", Arrays.toString(args));
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ChatListFragment()).commit();
                break;
            case R.id.nav_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SettingFragment()).commit();
                break;
            case R.id.nav_friend:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CommunityFragment()).commit();
                break;
            case R.id.nav_logout:
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
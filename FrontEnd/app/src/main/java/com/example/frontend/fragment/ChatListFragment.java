package com.example.frontend.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.VolleyError;
import com.example.frontend.R;
import com.example.frontend.adapter.RoomAdapter;
import com.example.frontend.api.RequestApi;
import com.example.frontend.api.VolleyCallBack;
import com.example.frontend.config.Constant;
import com.example.frontend.config.Utilities;
import com.example.frontend.model.Room;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import io.socket.client.IO;
import io.socket.client.Socket;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class ChatListFragment extends Fragment {

    private Button button;
    public static Socket SOCKET;
    private ArrayList<Room> listRoom = new ArrayList<>();
    private RecyclerView recyclerView;
    private RoomAdapter roomAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        connectSocket();
        getListRoom();
    }

    private void getListRoom() {
        String path = "api/doubleChat/getListRoom";
        Map<String,String> params = new HashMap<>();
        RequestApi.sendRequest(this.getContext(), path, params, new VolleyCallBack() {
            @Override
            public void onSuccess(String json) {
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    if(jsonObject.getInt("err") == 200){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i = 0;i<jsonArray.length(); i++){
                            JSONObject roomJson = jsonArray.getJSONObject(i);
                            Room room = new Room(
                                    roomJson.getString("id"),
                                    roomJson.getLong("updatedAt"),
                                    roomJson.getLong("createdAt"),
                                    roomJson.getString("name"),
                                    roomJson.getString("user1"),
                                    roomJson.getString("user2")
                            );
                            listRoom.add(room);
                        }
                        roomAdapter.notifyDataSetChanged();
                    }else{

                    }
                    Utilities.hideLoading();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
            @Override
            public void onError(VolleyError error) {
                Log.e("ABCD", error.toString());
                Utilities.hideLoading();
            }
        });
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
            Log.d("SocketIO", e.getMessage());
        });

        socket.on("message", args -> {
            Log.d("Message", Arrays.toString(args));
        });

        socket.on("newChatRoom", args -> {
            Log.d("new chat room", Arrays.toString(args));
        });

        socket.on("receiveMessage", args -> {
            Log.d("receive Message", Arrays.toString(args));
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chat_list, container, false);
//        button = rootView.findViewById(R.id.btn_create_room);

//        button.setOnClickListener(v -> {
//            Log.d("SocketIO", "click");
//            try {
//                JSONObject body = new JSONObject();
//                body.put("userId", "64a3e79bf43d5b2d188109b0");
//                JSONObject headers = new JSONObject();
//                headers.put("authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY0YTNlNzliZjQzZDViMmQxODgxMDliMCIsImlhdCI6MTY4ODYxMDQ4NSwiZXhwIjoxNjg4NjQ2NDg1fQ.PfeizagElwZJzziSfQYfIqp-drMiTB0Cvpscj14B9tg");
//
//                JSONObject header = new JSONObject();
//                header.put("url", "http://192.168.0.156:1337/api/room/createRoom");
//                header.put("data", body);
//                header.put("method", "post");
//                header.put("headers", headers);
//
//                socket.emit("post", header);
//            } catch (JSONException e) {
//                throw new RuntimeException(e);
//            }
//        });

        recyclerView = rootView.findViewById(R.id.list_room_recyclerview);
        roomAdapter = new RoomAdapter(getContext(), listRoom);
        recyclerView.setAdapter(roomAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return rootView;
    }
}
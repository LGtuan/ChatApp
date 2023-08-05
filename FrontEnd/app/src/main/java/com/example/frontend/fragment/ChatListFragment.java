package com.example.frontend.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.example.frontend.R;
import com.example.frontend.activity.HomeActivity;
import com.example.frontend.adapter.RoomAdapter;
import com.example.frontend.api.RequestApi;
import com.example.frontend.api.VolleyCallBack;
import com.example.frontend.config.Constant;
import com.example.frontend.config.Utilities;
import com.example.frontend.model.Message;
import com.example.frontend.model.Room;
import com.example.frontend.model.User;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import io.socket.client.IO;
import io.socket.client.Socket;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ChatListFragment extends Fragment {

    public static ArrayList<Room> LIST_ROOM = new ArrayList<>();
    private RecyclerView recyclerView;
    public static RoomAdapter ROOM_ADAPTER;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getListRoom();
        listenSocketEvent();
    }

    private void getListRoom() {
        LIST_ROOM = new ArrayList<>();
        RequestApi.sendRequest(this.getContext(),
                "api/doubleChat/getListRoom",
                new HashMap<>(),
                new VolleyCallBack() {
            @Override
            public void onSuccess(String json) {
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    Log.e("JSON", jsonObject.toString());
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
                                    roomJson.getString("user2"),
                                    roomJson.getString("lastMessage"),
                                    roomJson.getString("image")
                            );
                            LIST_ROOM.add(room);
                        }
                        ROOM_ADAPTER.notifyItemRangeInserted(0, LIST_ROOM.size()-1);
                    }else{
                        Utilities.showErrorMessage(getContext(),jsonObject.getString("message"));
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

    private void listenSocketEvent() {
        HomeActivity.SOCKET.on("receiveMessage", args -> {
            if(getActivity()== null) return;
            getActivity().runOnUiThread(() -> {
                try {
                    JSONObject msgJson = (JSONObject) args[0];
                    String roomId = msgJson.getString("doubleRoom");
                    for (int i = 0; i< LIST_ROOM.size();i++) {
                        if(LIST_ROOM.get(i).getId().equals(roomId)){
                            LIST_ROOM.get(i).setLastMessage(msgJson.getString("content"));
                            Room room = LIST_ROOM.get(i);
                            LIST_ROOM.remove(i);
                            ROOM_ADAPTER.notifyItemRemoved(i);
                            LIST_ROOM.add(0,room);
                            ROOM_ADAPTER.notifyItemInserted(0);
                            break;
                        }
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            });
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chat_list, container, false);

        recyclerView = rootView.findViewById(R.id.list_room_recyclerview);
        ROOM_ADAPTER = new RoomAdapter(getContext(), LIST_ROOM);
        recyclerView.setAdapter(ROOM_ADAPTER);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return rootView;
    }
}
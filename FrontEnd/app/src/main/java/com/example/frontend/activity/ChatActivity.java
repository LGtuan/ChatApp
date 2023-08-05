package com.example.frontend.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.example.frontend.R;
import com.example.frontend.adapter.MessageAdapter;
import com.example.frontend.api.RequestApi;
import com.example.frontend.api.VolleyCallBack;
import com.example.frontend.config.Utilities;
import com.example.frontend.fragment.ChatListFragment;
import com.example.frontend.model.Message;
import com.example.frontend.model.Room;
import com.example.frontend.model.User;
import com.squareup.picasso.Picasso;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    private ImageButton btnBack, btnMore, btnSend;
    private TextView txtName;
    private EditText edtChat;
    private ImageView imageRoom;
    private Room room;
    private String recipientId;
    private RecyclerView recyclerView;
    private ArrayList<Message> listMessage = new ArrayList<>();
    private MessageAdapter messageAdapter;

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initView();

        Intent intent = getIntent();
        room = (Room) intent.getSerializableExtra("room");
        txtName.setText(room.getName());
        Picasso.get().load(intent.getStringExtra("roomImg")).into(imageRoom);

        if (room.getUser1().equals(Utilities.getStringLocalValue(this, "id"))) {
            recipientId = room.getUser2();
        } else recipientId = room.getUser1();

        setUpRecyclerView();
        listenSocketEvent();
        getListMessage(false);
    }

    private void initView() {
        btnBack = findViewById(R.id.btn_back_chat);
        btnMore = findViewById(R.id.btn_more_chat_activity);
        txtName = findViewById(R.id.txt_name_chat_activity);
        btnSend = findViewById(R.id.btn_send_chat_activity);
        edtChat = findViewById(R.id.edt_chat_activity);
        recyclerView = findViewById(R.id.recycler_message);
        imageRoom = findViewById(R.id.image_room_chat);

        btnBack.setOnClickListener(v -> {
            onBackPressed();
        });
        btnSend.setOnClickListener(v -> {
            sendChat();
        });
    }

    private void setUpRecyclerView() {
        messageAdapter = new MessageAdapter(this, listMessage);
        recyclerView.setAdapter(messageAdapter);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
//
//                if (linearLayoutManager != null
//                        && linearLayoutManager.findLastCompletelyVisibleItemPosition() == 0) {
//                    getListMessage(true);
//                }
//            }

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (recyclerView.computeVerticalScrollOffset() == 0 && listMessage.size() >= 20) {
                        getListMessage(true);
                    }
                }
            }
        });
    }

    private void listenSocketEvent() {
        HomeActivity.SOCKET.on("receiveMessage", args -> {
            this.runOnUiThread(() -> {
                try {
                    JSONObject msgJson = (JSONObject) args[0];
                    Log.d("Socket ReceiveMessage", msgJson.toString());
                    JSONObject userJson = msgJson.getJSONObject("user");

                    User user = new User(
                            userJson.getString("id"),
                            userJson.getString("nickName"),
                            userJson.getString("image"));

                    Message message = new Message(
                            msgJson.getString("id"),
                            msgJson.getLong("createdAt"),
                            msgJson.getLong("updatedAt"),
                            msgJson.getString("content"),
                            user,
                            msgJson.getString("doubleRoom")
                    );
                    if (!message.getRoom().equals(room.getId())) return;
                    listMessage.add(message);
                    if (listMessage.size() > 1) {
                        messageAdapter.notifyItemChanged(listMessage.size() - 2);
                    }
                    messageAdapter.notifyItemInserted(listMessage.size() - 1);
                    recyclerView.scrollToPosition(listMessage.size() - 1);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            });
        });
    }

    private void sendChat() {
        if (edtChat.getText().equals("")) return;
        JSONObject body = new JSONObject();
        try {
            body.put("recipientId", recipientId);
            body.put("content", edtChat.getText().toString());
            body.put("roomId", room.getId());
            RequestApi.sendSocketRequest("api/doubleChat/sendMessage", this, body);
            edtChat.setText("");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private void getListMessage(boolean isLoadMore) {
        String path = "api/doubleChat/getListMessage";
        Map<String, String> params = new HashMap<>();
        params.put("user2", recipientId);
        if (room.getId() != null) {
            params.put("roomId", room.getId());
            params.put("skip", String.valueOf(listMessage.size()));
        }

        RequestApi.sendRequest(this, path, params, new VolleyCallBack() {
            @Override
            public void onSuccess(String json) {
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    if (jsonObject.getInt("err") == 200) {
                        String roomId = jsonObject.getString("roomId");
                        if (!roomId.equals("")) room.setId(roomId);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        ArrayList<Message> loadMoreList = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject msgJson = jsonArray.getJSONObject(i);
                            JSONObject userJson = msgJson.getJSONObject("user");
                            User user = new User(
                                    userJson.getString("id"),
                                    userJson.getString("nickName"),
                                    userJson.getString("image"));

                            Message message = new Message(
                                    msgJson.getString("id"),
                                    msgJson.getLong("createdAt"),
                                    msgJson.getLong("updatedAt"),
                                    msgJson.getString("content"),
                                    user,
                                    msgJson.getString("doubleRoom")
                            );
                            if(!isLoadMore){
                                listMessage.add(message);
                            }else{
                                loadMoreList.add(message);
                            }
                        }
                        if(!isLoadMore){
                            messageAdapter.notifyItemRangeInserted(0,listMessage.size()-1);
                        }else{
                            listMessage.addAll(0,loadMoreList);
                            messageAdapter.notifyItemRangeInserted(0, loadMoreList.size()-1);
                        }
                    } else {
                        Utilities.showErrorMessage(ChatActivity.this,jsonObject.getString("message"));
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
}
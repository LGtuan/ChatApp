package com.example.frontend.activity;

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

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    private ImageButton btnBack, btnMore, btnSend;
    private TextView txtName;
    private EditText edtChat;
    private Room room;
    private String recipientId;
    private RecyclerView recyclerView;
    private ArrayList<Message> listMessage= new ArrayList<>();
    private MessageAdapter messageAdapter;

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        btnBack = findViewById(R.id.btn_back_chat);
        btnMore = findViewById(R.id.btn_more_chat_activity);
        txtName = findViewById(R.id.txt_name_chat_activity);
        btnSend = findViewById(R.id.btn_send_chat_activity);
        edtChat = findViewById(R.id.edt_chat_activity);
        recyclerView = findViewById(R.id.recycler_message);

        Intent intent = getIntent();
        room = (Room) intent.getSerializableExtra("room");
        recipientId = intent.getStringExtra("recipientId");

        txtName.setText(room.getName());

        btnBack.setOnClickListener(v-> {
            onBackPressed();
        });
        btnSend.setOnClickListener(v-> {
            sendChat();
        });

        messageAdapter = new MessageAdapter(this, listMessage);
        recyclerView.setAdapter(messageAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getListMessage();
    }

    private void sendChat(){
            try {
                JSONObject body = new JSONObject();
                SharedPreferences sps = this.getSharedPreferences("auth", Context.MODE_PRIVATE);
                body.put("userId", sps.getString("id",""));
                body.put("recipientId", recipientId);
                body.put("content", edtChat.getText().toString());
                JSONObject headers = new JSONObject();
                headers.put("authorization", "Bearer "+sps.getString("accessToken",""));

                JSONObject header = new JSONObject();
                header.put("url", "http://10.0.2.2:1337/api/doubleChat/sendMessage");
                header.put("data", body);
                header.put("method", "post");
                header.put("headers", headers);


                ChatListFragment.SOCKET.emit("post", header);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
    }

    private void getListMessage(){
        String path = "api/doubleChat/getListMessage";
        Map<String,String> params = new HashMap<>();
        params.put("roomId", room.getId());

        RequestApi.sendRequest(this, path, params, new VolleyCallBack() {
            @Override
            public void onSuccess(String json) {
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    if(jsonObject.getInt("err") == 200){
                        Log.d("get list", jsonObject.toString());
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i = 0;i<jsonArray.length(); i++){
                            JSONObject roomJson = jsonArray.getJSONObject(i);
                            Message message = new Message(
                                    roomJson.getString("id"),
                                    roomJson.getLong("createdAt"),
                                    roomJson.getLong("updatedAt"),
                                    roomJson.getString("content"),
                                    roomJson.getString("user"),
                                    roomJson.getString("doubleRoom")
                            );
                            listMessage.add(message);
                        }
                        messageAdapter.notifyDataSetChanged();
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
}
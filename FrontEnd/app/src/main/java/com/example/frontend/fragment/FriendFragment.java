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
import com.example.frontend.adapter.FriendAdapter;
import com.example.frontend.api.RequestApi;
import com.example.frontend.api.VolleyCallBack;
import com.example.frontend.config.Utilities;
import com.example.frontend.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FriendFragment extends Fragment {

    private ArrayList<User> listFriend = new ArrayList<>();
    private FriendAdapter friendAdapter;
    private RecyclerView recyclerView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getListFriend();
        getListPeople();
    }

    private void getListPeople(){
        String path = "api/user/find";
        Map<String, String> params = new HashMap<>();

        RequestApi.sendRequest(this.getContext(), path, params, new VolleyCallBack() {
            @Override
            public void onSuccess(String json) {
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    if(jsonObject.getInt("err") == 200){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i = 0;i<jsonArray.length(); i++){
                            JSONObject roomJson = jsonArray.getJSONObject(i);
                            User user = new User(
                                    roomJson.getString("id"),
                                    roomJson.getString("nickName")
                            );
                            listFriend.add(user);
                        }
                        friendAdapter.notifyDataSetChanged();
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

//    private void getListFriend(){
//        String path = "api/user/findFriend";
//        Map<String,String> params = new HashMap<>();
//        RequestApi.sendRequest(this.getContext(), path, params, new VolleyCallBack() {
//            @Override
//            public void onSuccess(String json) {
//                try {
//                    JSONObject jsonObject = new JSONObject(json);
//                    if(jsonObject.getInt("err") == 200){
//                        JSONArray jsonArray = jsonObject.getJSONArray("data");
//                        for(int i = 0;i<jsonArray.length(); i++){
//                            JSONObject roomJson = jsonArray.getJSONObject(i);
//                            User user = new User(
//                                    roomJson.getString("id"),
//                                    roomJson.getString("nickName")
//                            );
//                            listFriend.add(user);
//                        }
//                        friendAdapter.notifyDataSetChanged();
//                    }else{
//
//                    }
//                    Utilities.hideLoading();
//                } catch (JSONException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//            @Override
//            public void onError(VolleyError error) {
//                Log.e("ABCD", error.toString());
//                Utilities.hideLoading();
//            }
//        });
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_friend, container, false);

        recyclerView = rootView.findViewById(R.id.list_friend_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        friendAdapter = new FriendAdapter(getContext(),listFriend);
        recyclerView.setAdapter(friendAdapter);

        return rootView;
    }
}
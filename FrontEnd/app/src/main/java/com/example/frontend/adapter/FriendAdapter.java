package com.example.frontend.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.frontend.R;
import com.example.frontend.activity.ChatActivity;
import com.example.frontend.config.Constant;
import com.example.frontend.config.Utilities;
import com.example.frontend.model.Room;
import com.example.frontend.model.User;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.ViewHolder> {
    private Context context;
    private ArrayList<User> listUser;

    public FriendAdapter(Context context, ArrayList<User> listUser) {
        this.context = context;
        this.listUser = listUser;
    }

    @NonNull
    @Override
    public FriendAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.friend_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FriendAdapter.ViewHolder holder, int position) {
        holder.txtName.setText(listUser.get(position).getNickName());
        Picasso.get()
                .load(Constant.URL + listUser.get(position).getImage())
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        return listUser.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView txtName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.image_friend_item);
            txtName = itemView.findViewById(R.id.txt_name_friend_item);

            itemView.setOnClickListener(v -> {
                User user = listUser.get(getAdapterPosition());
                Room room = new Room();
                room.setUser1(Utilities.getStringLocalValue(context, "id"));
                room.setUser2(user.getId());
                room.setName(user.getNickName());
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("room", room);
                intent.putExtra("roomImg", Constant.URL + listUser.get(getAdapterPosition()).getImage());
                context.startActivity(intent);
            });
        }
    }
}

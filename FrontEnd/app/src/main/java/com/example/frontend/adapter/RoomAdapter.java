package com.example.frontend.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.frontend.R;
import com.example.frontend.activity.ChatActivity;
import com.example.frontend.model.Room;

import java.util.ArrayList;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Room> list;

    public RoomAdapter(Context context, ArrayList<Room> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.room_item,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("bug", position+"");
        holder.txtName.setText(list.get(position).getName());
        holder.txtChat.setText(list.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView txtName, txtChat;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.image_room_item);
            txtName = itemView.findViewById(R.id.txt_name_room_item);
            txtChat = itemView.findViewById(R.id.txt_chat_room_item);

            itemView.setOnClickListener(v-> {
                Log.d("Room", "click");
                Room room = list.get(getAdapterPosition());
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("room", room);
                context.startActivity(intent);
            });
        }
    }
}

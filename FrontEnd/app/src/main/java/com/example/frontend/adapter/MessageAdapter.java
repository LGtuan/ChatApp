package com.example.frontend.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.frontend.R;
import com.example.frontend.config.Constant;
import com.example.frontend.model.Message;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Message> listMessage;
    private String userId;

    public MessageAdapter(Context context, ArrayList<Message> listMessage) {
        this.context = context;
        this.listMessage = listMessage;
        userId = context.getSharedPreferences("auth", Context.MODE_PRIVATE).getString("id", "");
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.message_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {
        Message message = listMessage.get(position);
        holder.text.setText(message.getContent());
        if (!message.getUser().getId().equals(userId)) {
            holder.view.setVisibility(View.GONE);
            holder.image.setVisibility(View.VISIBLE);
            boolean showAvatar = position == listMessage.size()-1;
            if(position+1 < listMessage.size() && !showAvatar){
                showAvatar = !listMessage.get(position+1).getUser().getId().equals(message.getUser().getId());
            }
            if(showAvatar){
                Picasso.get()
                        .load(Constant.URL+message.getUser().getImage())
                        .into(holder.image);
            }else{holder.image.setVisibility(View.INVISIBLE);}
        }else {
            holder.view.setVisibility(View.INVISIBLE);
            holder.image.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return listMessage.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View view;
        private ImageView image;
        private TextView text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView.findViewById(R.id.view_message_item);
            image = itemView.findViewById(R.id.image_message_item);
            text = itemView.findViewById(R.id.text_message_item);
        }
    }
}

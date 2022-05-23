package com.example.parenthoodandroidapp.ChatsActivities.messages;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.parenthoodandroidapp.ChatsActivities.AllChatsActivity;
import com.example.parenthoodandroidapp.ChatsActivities.chat.Chat;
import com.example.parenthoodandroidapp.ModelClasses.Users;
import com.example.parenthoodandroidapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MyViewHolder> {

    private List<MessagesList> messagesLists;
    //private final Context context;
    Context allChatsActivity;
    ArrayList<Users> usersArrayList;

    public MessagesAdapter(List<MessagesList> messagesLists, Context context) {
        this.messagesLists = messagesLists;
       // this.context = context;
    }

    public MessagesAdapter(AllChatsActivity allChatsActivity, ArrayList<Users> usersArrayList) {
        this.allChatsActivity=allChatsActivity;
        this.usersArrayList=usersArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.messages_adapter_layout, null));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Users users=usersArrayList.get(position);

        if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(users.getUserId())){
            holder.rootLayout.setVisibility(View.GONE );
        }

       // MessagesList list2 = messagesLists.get(position);
        holder.name.setText(users.getName());
        holder.lastMessage.setVisibility(View.VISIBLE);
        holder.lastMessage.setText(users.getStatus());
        Picasso.get()
                .load(users.getImageUri())
                .placeholder(R.drawable.user_icon)
                .into(holder.profilePic);


        holder.rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(allChatsActivity, Chat.class);
                intent.putExtra("name", users.getName());
                intent.putExtra("receiverImg", users.getImageUri());
                intent.putExtra("userId", users.getUserId() );
               // intent.putExtra("chat_key", list2.getChatKey());

                allChatsActivity.startActivity(intent);
            }
        });
    }

    public void updateData(List<MessagesList> messagesLists){
        this.messagesLists = messagesLists;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return usersArrayList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView profilePic;
        private TextView name;
        private TextView lastMessage;
        private TextView unseenMessages;
        private LinearLayout rootLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            profilePic = itemView.findViewById(R.id.profilePic);
            name = itemView.findViewById(R.id.name);
            lastMessage  = itemView.findViewById(R.id.lastMessage);
            unseenMessages = itemView.findViewById(R.id.unseenMessages);
            rootLayout = itemView.findViewById(R.id.rootLayout);
        }
    }
}

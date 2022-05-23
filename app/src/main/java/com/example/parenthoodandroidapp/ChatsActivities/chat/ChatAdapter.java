package com.example.parenthoodandroidapp.ChatsActivities.chat;

import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parenthoodandroidapp.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ChatAdapter extends RecyclerView.Adapter{

    private final Context context;
    private String userMobile;

    ArrayList<ChatLists> chatListsArrayList;
    int ITEM_SEND=1;
    int ITEM_REECEIVE=2;


    public ChatAdapter(Context context, ArrayList<ChatLists> chatListsArrayList) {
        this.context = context;
        this.chatListsArrayList = chatListsArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType==ITEM_SEND){
            View v= LayoutInflater.from(context).inflate(R.layout.chat_adapter_layout,parent, false);
             return new SenderViewHolder(v);
        }else{
            View v= LayoutInflater.from(context).inflate(R.layout.chat_adapter_layout,parent, false);
            return new ReceiverViewHolder(v);
        }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ChatLists list = chatListsArrayList.get(position);

        Log.e("checkValue",list.getMessage());

        if(holder.getClass()==SenderViewHolder.class){
            SenderViewHolder viewHolder =(SenderViewHolder)holder;
            //viewHolder.oppoMessage.setVisibility(View.VISIBLE);
            viewHolder.receiverLayout.setVisibility(View.GONE);

            viewHolder.senderMessage.setText(list.getMessage());
            Calendar cal = Calendar.getInstance(Locale.ENGLISH);
            cal.setTimeInMillis(list.getTimeStamp()* 1000);
            String date = DateFormat.format("EEE, MMM d, hh:mm a", cal).toString();
            viewHolder.senderMsgTime.setText(date+"");
        }
        else{
            ReceiverViewHolder viewHolder =(ReceiverViewHolder)holder;
            viewHolder.senderLayout.setVisibility(View.GONE);
            //viewHolder.myLayout.setVisibility(View.VISIBLE);

            viewHolder.receiverMessage.setText(list.getMessage());
            Calendar cal = Calendar.getInstance(Locale.ENGLISH);
            cal.setTimeInMillis(list.getTimeStamp() * 1000);
            String date = DateFormat.format("EEE, MMM d, hh:mm a", cal).toString();
            viewHolder.receiverMsgTime.setText(date+"");
        }
    }

    @Override
    public int getItemViewType(int position) {
        ChatLists chatLists=chatListsArrayList.get(position);
        if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(chatLists.getSenderId())){
             return ITEM_REECEIVE;
        }else{
            return ITEM_SEND;
        }
    }

    @Override
    public int getItemCount() {
        return chatListsArrayList.size();
    }


//    static class MyViewHolder extends RecyclerView.ViewHolder {
//
//        private LinearLayout oppoLayout, myLayout;
//        private TextView oppoMessage, receiverMessage;
//        private TextView oppoTime, myTime;
//
//        public MyViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//
//            myLayout = itemView.findViewById(R.id.myLayout);
//
//            receiverMessage = itemView.findViewById(R.id.receiverMessage);
//
//            myTime = itemView.findViewById(R.id.myMsgTime);
//        }
//    }

    class SenderViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout senderLayout,receiverLayout ;
        private TextView senderMessage,receiverMessage;
        private TextView senderMsgTime,receiverMsgTime;
        public SenderViewHolder(@NonNull View itemView){
            super(itemView);
            receiverLayout= itemView.findViewById(R.id.receiverLayout);
            senderLayout = itemView.findViewById(R.id.senderLayout);
            senderMessage = itemView.findViewById(R.id.senderMessage);
            senderMsgTime = itemView.findViewById(R.id.senderMsgTime);
            receiverMessage = itemView.findViewById(R.id.receiverMessage);
            receiverMsgTime = itemView.findViewById(R.id.receiverMsgTime);
        }

    }
    class ReceiverViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout  senderLayout,receiverLayout;
        private TextView  receiverMessage,senderMessage;
        private TextView  receiverMsgTime,senderMsgTime;
        public ReceiverViewHolder(@NonNull View itemView){
            super(itemView);
            senderLayout = itemView.findViewById(R.id.senderLayout);
            receiverLayout = itemView.findViewById(R.id.receiverLayout);
            receiverMessage = itemView.findViewById(R.id.receiverMessage);
            receiverMsgTime = itemView.findViewById(R.id.receiverMsgTime);
            senderLayout = itemView.findViewById(R.id.senderLayout);
            senderMessage = itemView.findViewById(R.id.senderMessage);
            senderMsgTime = itemView.findViewById(R.id.senderMsgTime);
        }

    }


}

package com.example.parenthoodandroidapp.ChatsActivities.chat;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parenthoodandroidapp.HelperClasses.APIHelperClasses.ApiUtils;
import com.example.parenthoodandroidapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;

public class Chat extends Activity {

   // DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://fir-chat-660ab-default-rtdb.firebaseio.com/");
    String getUserMobile = "";
    private String chatKey;
    private RecyclerView chattingRecyclerView;
    private ChatAdapter chatAdapter;
    private boolean loadingFirstTime = true;
    String senderImage,receiverImage,senderId,receiverId;


    FirebaseDatabase database;
    FirebaseAuth auth;
    String senderRoom, receiverRoom;

    ArrayList<ChatLists> messagesListArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        final ImageView backBtn = findViewById(R.id.backBtn);
        final TextView nameTV = findViewById(R.id.name);
        final EditText messageEditText = findViewById(R.id.messageEditTxt);
        final CircleImageView profilePic = findViewById(R.id.profilePic);
        final ImageView sendBtn = findViewById(R.id.sendBtn);

        database=FirebaseDatabase.getInstance(ApiUtils.FIREBASE_DB_URL);
        auth=FirebaseAuth.getInstance();
        senderId=auth.getUid();
        chattingRecyclerView = findViewById(R.id.chattingRecyclerView);

        messagesListArrayList=new ArrayList<>();
        // get data from messages adapter class
        final String getName = getIntent().getStringExtra("name");
        receiverImage  = getIntent().getStringExtra("receiverImg");
       // chatKey = getIntent().getStringExtra("chat_key");
        final String getUserId = getIntent().getStringExtra("userId");
        receiverId=getUserId;

        senderRoom=senderId+receiverId;
        receiverRoom=receiverId+senderId;

        Log.e("checkValue","senderRoom- "+senderRoom+"\n"+"receiverRoom- "+receiverRoom);

        DatabaseReference reference=database.getReference().child("user").child(auth.getUid());
        DatabaseReference chatReference=database.getReference().child("chats").child(senderRoom).child("messages");






        
        
        // get user mobile from memory
        //getUserMobile = MemoryData.getData(Chat.this);

        nameTV.setText(getName);
        Picasso.get().load(receiverImage).placeholder(R.drawable.user_icon).into(profilePic);

        chattingRecyclerView.setHasFixedSize(true);
        chattingRecyclerView.setLayoutManager(new LinearLayoutManager(Chat.this));
        chatAdapter = new ChatAdapter( Chat.this,messagesListArrayList);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        chattingRecyclerView.setLayoutManager(linearLayoutManager);
        chattingRecyclerView.setAdapter(chatAdapter);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e("checkValue","pass1  "+dataSnapshot);
                senderImage=dataSnapshot.child("imageUri").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        chatReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                messagesListArrayList.clear();
                Log.e("checkValue","pass2 "+dataSnapshot);
                for(DataSnapshot snapshot : dataSnapshot.getChildren())
                {

                    ChatLists messagesList =snapshot.getValue(ChatLists.class);
                    messagesListArrayList.add(messagesList);
                }
                chatAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                if (chatKey.isEmpty()) {
//                    // generate chat key. by default chatKey is 1
//                    chatKey = "1";
//
//                    if (snapshot.hasChild("chat")) {
//                        chatKey = String.valueOf(snapshot.child("chat").getChildrenCount() + 1);
//                    }
//                }
//
//                if(snapshot.hasChild("chat")){
//
//                    if(snapshot.child("chat").child(chatKey).hasChild("messages")){
//
//                        chatLists.clear();
//
//                        for(DataSnapshot messagesSnapshot : snapshot.child("chat").child(chatKey).child("messages").getChildren()){
//
//                            if(messagesSnapshot.hasChild("msg") && messagesSnapshot.hasChild("mobile")){
//
//                                final String messageTimestamps = messagesSnapshot.getKey();
//                                final String getMobile = messagesSnapshot.child("mobile").getValue(String.class);
//                                final String getMsg = messagesSnapshot.child("msg").getValue(String.class);
//
//                                Timestamp timestamp = new Timestamp(Long.parseLong(messageTimestamps));
//                                Date date = new Date(timestamp.getTime());
//                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
//                                SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("hh:mm aa", Locale.getDefault());
//
//                                ChatList chatList = new ChatList(getMobile, getName, getMsg, simpleDateFormat.format(date), simpleTimeFormat.format(date));
//                                chatLists.add(chatList);
//
//                                if(loadingFirstTime || Long.parseLong(messageTimestamps) > Long.parseLong(MemoryData.getLastMsgTS(Chat.this, chatKey))){
//
//                                    loadingFirstTime = false;
//
//                                    MemoryData.saveLastMsgTS(messageTimestamps, chatKey, Chat.this);
//                                    chatAdapter.updateChatList(chatLists);
//
//                                    chattingRecyclerView.scrollToPosition(chatLists.size() - 1);
//
//                                }
//                            }
//
//                        }
//                    }
//
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("checkValue","pass3");
                final String getTxtMessage = messageEditText.getText().toString();
                if(getTxtMessage.isEmpty()){
                    return;
                }

                // get current timestamps
                long currentTimestamp = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
                ChatLists messagesList=new ChatLists(getName,senderId,getTxtMessage,"",currentTimestamp);
                //getName,senderId ,"","",0,date.getTime()+""
                database=FirebaseDatabase.getInstance(ApiUtils.FIREBASE_DB_URL);
                database.getReference().child("chats")
                        .child(senderRoom)
                        .child("messages")
                        .push()
                        .setValue(messagesList).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        database.getReference().child("chats")
                                .child(receiverRoom )
                                .child("messages")
                                .push()
                                .setValue(messagesList).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        });
                    }
                });
                chatAdapter.notifyDataSetChanged();
//                databaseReference.child("chat").child(chatKey).child("user_1").setValue(getUserMobile);
//                databaseReference.child("chat").child(chatKey).child("user_2").setValue(getMobile);
//                databaseReference.child("chat").child(chatKey).child("messages").child(currentTimestamp).child("msg").setValue(getTxtMessage);
//                databaseReference.child("chat").child(chatKey).child("messages").child(currentTimestamp).child("mobile").setValue(getUserMobile);

                // clear edit text
                messageEditText.setText("");


            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
package com.example.parenthoodandroidapp.ChatsActivities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parenthoodandroidapp.ChatsActivities.messages.MessagesAdapter;
import com.example.parenthoodandroidapp.ChatsActivities.messages.MessagesList;
import com.example.parenthoodandroidapp.HelperClasses.APIHelperClasses.ApiUtils;
import com.example.parenthoodandroidapp.ModelClasses.Users;
import com.example.parenthoodandroidapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AllChatsActivity extends Activity {

    private final List<MessagesList> messagesLists = new ArrayList<>();

    private String mobile;
    private String email;
    private String name;

    private int unseenMessages = 0;
    private String lastMessage = "";
    private String chatKey = "";
    private boolean dataSet = false;
    private RecyclerView messagesRecyclerView;
    private MessagesAdapter messagesAdapter;
    FirebaseAuth auth;
    FirebaseDatabase database;
    ArrayList<Users> usersArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_chats);
        final ImageView backBtn = findViewById(R.id.btn_go_back);
        final CircleImageView userProfilePic = findViewById(R.id.userProfilePic);
        usersArrayList=new ArrayList<>();
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance(ApiUtils.FIREBASE_DB_URL);
        DatabaseReference reference=database.getReference().child("user");

        messagesRecyclerView = findViewById(R.id.messagesRecyclerView);
        messagesRecyclerView.setHasFixedSize(true);
        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // set adapter to recyclerview


        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        DatabaseReference currentUserReference=database.getReference().child("user").child(auth.getUid());

        currentUserReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e("checkValue","pass1  "+dataSnapshot);
               String senderImage=dataSnapshot.child("firebaseioimageUri").getValue().toString();
                Picasso.get()
                        .load( senderImage)
                        .placeholder(R.drawable.user_icon)
                        .into(userProfilePic);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                Users users=dataSnapshot1.getValue(Users.class);
                usersArrayList.add(users);
            }
            messagesAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        messagesAdapter=new MessagesAdapter(AllChatsActivity.this,usersArrayList);
        //messagesAdapter = new MessagesAdapter(messagesLists, AllChatsActivity.this);
        messagesRecyclerView.setAdapter(messagesAdapter);


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
package com.example.parenthoodandroidapp.AppFragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.parenthoodandroidapp.ModelClasses.Users;
import com.example.parenthoodandroidapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileFragment extends Fragment {
    CircleImageView userProfilePic;
    EditText username1;
    FirebaseAuth auth;
    ImageView btn_edit_name;
    private Uri imgData;
    LinearLayout btn_save;
    FirebaseStorage firebaseStorage;
    String dbName,dbemail,dbimgUri,dbStatus,dbUserId;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.edit_profile_fragment, container, false);
        userProfilePic=v.findViewById(R.id.userProfilePic);
        username1=v.findViewById(R.id.username1);
        username1.setEnabled(false);
        btn_save=v.findViewById(R.id.btn_save);
        btn_edit_name=v.findViewById(R.id.btn_edit_name);

        progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("Please wait..");
        progressDialog.setCancelable(false);

        auth=FirebaseAuth.getInstance();
        firebaseStorage=FirebaseStorage.getInstance();
        FirebaseDatabase database= FirebaseDatabase.getInstance("https://cys-android-app-default-rtdb.firebaseio.com/");

        DatabaseReference currentUserReference=database.getReference().child("user").child(auth.getUid());
        currentUserReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e("checkValue","pass1  "+dataSnapshot);
                String senderImage=dataSnapshot.child("imageUri").getValue().toString();
                username1.setText(dataSnapshot.child("name").getValue().toString());
                Picasso.get()
                        .load( senderImage)
                        .placeholder(R.drawable.user_icon)
                        .into(userProfilePic);

                //for Saving data to firebase Server
                dbemail=dataSnapshot.child("email").getValue().toString();
                dbName=dataSnapshot.child("name").getValue().toString();
                dbimgUri=dataSnapshot.child("imageUri").getValue().toString();
                dbStatus=dataSnapshot.child("status").getValue().toString();
                dbUserId=dataSnapshot.child("userId").getValue().toString();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        StorageReference storageReference=firebaseStorage.getReference().child("upload").child(auth.getUid());

        userProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 100);
            }
        });
        btn_edit_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username1.setEnabled(true);
                username1.requestFocus();
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                dbName=username1.getText().toString();
                if(imgData!=null){
                     storageReference.putFile(imgData).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                         @Override
                         public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                             storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                 @Override
                                 public void onSuccess(Uri uri) {
                                     String imgURI=uri.toString();
                                     Users users=new Users(auth.getUid(),dbName,dbemail,imgURI,dbStatus);
                                     currentUserReference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                         @Override
                                         public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    progressDialog.dismiss();
                                                    username1.setEnabled(false);
                                                    Toast.makeText(getContext(),"Profile Updated successfully!!",Toast.LENGTH_LONG).show();
                                                }else{
                                                    progressDialog.dismiss();
                                                    username1.setEnabled(false);
                                                    Toast.makeText(getContext(),"Something went wrong.",Toast.LENGTH_LONG).show();

                                                }
                                         }
                                     });
                                     }
                             });
                         }
                     });
                }else{
                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String imgURI=uri.toString();
                            Users users=new Users(auth.getUid(),dbName,dbemail,imgURI,dbStatus);
                            currentUserReference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        progressDialog.dismiss();
                                        username1.setEnabled(false);
                                       // Toast.makeText(getContext(),"Profile Updated successfully!!",Toast.LENGTH_LONG).show();
                                    }else{
                                        progressDialog.dismiss();
                                        username1.setEnabled(false);
                                        //Toast.makeText(getContext(),"Something went wrong.",Toast.LENGTH_LONG).show();

                                    }
                                }
                            });
                        }
                    });
                }

            }
        });


        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if(data!=null){

                imgData=data.getData();
                Glide
                        .with(this)
                        .load(imgData)
                        .centerCrop()
                        .placeholder(R.drawable.user_icon)
                        .into(userProfilePic);
            }
        }
    }
}
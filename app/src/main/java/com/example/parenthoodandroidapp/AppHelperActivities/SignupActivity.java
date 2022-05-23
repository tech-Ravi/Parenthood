package com.example.parenthoodandroidapp.AppHelperActivities;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.parenthoodandroidapp.HelperClasses.APIHelperClasses.ApiUtils;
import com.example.parenthoodandroidapp.HelperClasses.SharedPrefManager;
import com.example.parenthoodandroidapp.ModelClasses.Users;
import com.example.parenthoodandroidapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class SignupActivity extends Activity {
    Button btnSignup;
    EditText et_password,et_fullname,et_email;
    boolean tempPassword=false;
    ImageView user_profile;
    TextView backToLogin;
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;
    Uri imgData;
    String testImg="https://firebasestorage.googleapis.com/v0/b/cys-android-app.appspot.com/o/user_icon.png?alt=media&token=01c9b2fe-b7e7-4f0a-a7ce-dad5ba16499d";
    String imageUri="";
    ProgressDialog progressDialog;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        et_password=findViewById(R.id.et_password);
        et_fullname=findViewById(R.id.et_fullname);
        et_email=findViewById(R.id.et_email);
        btnSignup=findViewById(R.id.btnSignup);
        backToLogin=findViewById(R.id.backToLogin);
        user_profile=findViewById(R.id.user_profile);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please wait..");
        progressDialog.setCancelable(false);

        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(SignupActivity.this,LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
        auth=FirebaseAuth.getInstance();

        database=FirebaseDatabase.getInstance(ApiUtils.FIREBASE_DB_URL);

        //database=FirebaseDatabase.getReference("Users");
        storage=FirebaseStorage.getInstance();

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            progressDialog.show();
            String status="Hey, there I'm using this app.";

              //otpDialog();
                if(et_fullname.getText().length()==0){
                    et_fullname.setError("Please fill this.");
                    et_fullname.requestFocus();
                    progressDialog.dismiss();
                }else if(et_email.getText().length()==0){
                    et_email.setError("Please fill this.");
                    et_email.requestFocus();
                    progressDialog.dismiss();
                }else if(!et_email.getText().toString().matches(emailPattern)){
                    et_email.setError("Please enter valid email.");
                    et_email.requestFocus();
                    progressDialog.dismiss();
                }else if(et_password.getText().length()==0||et_password.getText().length()<6){
                    et_password.setError("Please fill this.");
                    et_password.requestFocus();
                    progressDialog.dismiss();
                }else{
                    auth.createUserWithEmailAndPassword(et_email.getText().toString(),et_password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){

                                DatabaseReference reference=database.getReference().child("user").child(auth.getUid());
                                StorageReference storageReference=storage.getReference().child("upload").child(auth.getUid());

                                if(imgData!=null){
                                    storageReference.putFile(imgData).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                            if(task.isSuccessful()){
                                                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri uri) {
                                                        imageUri=uri.toString();
                                                        Users users= new Users(auth.getUid(),et_fullname.getText().toString(),et_email.getText().toString(),imageUri,status);
                                                        reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if(task.isSuccessful()){
                                                                    progressDialog.dismiss();
                                                                    SharedPrefManager.getInstans(getApplicationContext()).userLogin(auth.getUid(),et_email.getText().toString(),"");
                                                                    startActivity(new Intent(SignupActivity.this,HomePageActivity.class));
                                                                    finish();
                                                                }else{
                                                                    Toast.makeText(SignupActivity.this,"Error in creating user.",Toast.LENGTH_LONG).show();
                                                                }
                                                            }
                                                        });
                                                    }
                                                });
                                            }
                                        }
                                    });
                                }else{
                                    imageUri="https://firebasestorage.googleapis.com/v0/b/parenthood-demo.appspot.com/o/user_icon.png?alt=media&token=8aa22966-3c64-4348-8491-83c404adca8a";
                                    Users users= new Users(auth.getUid(),et_fullname.getText().toString(),et_email.getText().toString(),imageUri,status);
                                    reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                progressDialog.dismiss();
                                                SharedPrefManager.getInstans(getApplicationContext()).userLogin(auth.getUid(),et_email.getText().toString(),"");
                                                startActivity(new Intent(SignupActivity.this,HomePageActivity.class));
                                                finish();
                                            }else{
                                                progressDialog.dismiss();
                                                Toast.makeText(SignupActivity.this,"Error in creating user.",Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                                }

                                //Toast.makeText(SignupActivity.this,"User signup successfully",Toast.LENGTH_LONG).show();
                            }else {
                                progressDialog.dismiss();
                                Toast.makeText(SignupActivity.this,"User signup failed",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });

        et_password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (et_password.getRight() - et_password.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                        if(!tempPassword){
                            et_password.setTransformationMethod(null);
                            et_password.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_24, 0);
                            tempPassword=true;
                        }else{
                            et_password.setTransformationMethod(new PasswordTransformationMethod());
                            et_password.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_off_24, 0);
                            tempPassword=false;
                        }

                        return true;
                    }
                }
                return false;
            }
        });


        user_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 100);
            }
        });
    }

    private void otpDialog() {
        final Dialog dialog = new Dialog(SignupActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_ACTION_MODE_OVERLAY);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.otp_view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        Button btn_verify_otp = (Button) dialog.findViewById(R.id.btn_verify_otp);
        btn_verify_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent i = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });


        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100) {
            if(data!=null){

                imgData=data.getData();
                Glide
                        .with(this)
                        .load(imgData)
                        .centerCrop()
                        .placeholder(R.drawable.user_icon)
                        .into(user_profile);
            }
        }

    }
}
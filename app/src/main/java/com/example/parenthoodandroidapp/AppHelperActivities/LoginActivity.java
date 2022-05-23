package com.example.parenthoodandroidapp.AppHelperActivities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.parenthoodandroidapp.HelperClasses.SharedPrefManager;
import com.example.parenthoodandroidapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends Activity {
    Button btnLogin;
    ImageView login_logo;
    TextView signuptext,signuptext2;
    EditText et_password,et_email;
    boolean tempPassword=false;
    FirebaseAuth auth;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin=findViewById(R.id.btnLogin);
        login_logo=findViewById(R.id.login_logo);
        signuptext=findViewById(R.id.signuptext);
        signuptext2=findViewById(R.id.signuptext2);
        et_password=findViewById(R.id.et_password);
        et_email=findViewById(R.id.et_email);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please wait..");
        progressDialog.setCancelable(false);

        auth=FirebaseAuth.getInstance();

        if(SharedPrefManager.getInstans(LoginActivity.this).isLogin()){
            Intent i =new Intent(LoginActivity.this, HomePageActivity.class);
            startActivity(i);
            finish();
        }
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                if(et_email.getText().length()==0){
                    et_email.setError("Please fill this.");
                    et_email.requestFocus();
                    progressDialog.dismiss();
                }else if(et_password.getText().length()==0){
                    et_password.setError("Please fill this.");
                    et_password.requestFocus();
                    progressDialog.dismiss();
                }else{
                    auth.signInWithEmailAndPassword(et_email.getText().toString(),et_password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                progressDialog.dismiss();
                                SharedPrefManager.getInstans(getApplicationContext()).userLogin(auth.getUid(),et_email.getText().toString(),"");
                                startActivity(new Intent(LoginActivity.this, HomePageActivity.class));
                                Toast.makeText(LoginActivity.this,"Login successful.",Toast.LENGTH_LONG).show();
                                finish();
                            }else{
                                progressDialog.dismiss();
                                Toast.makeText(LoginActivity.this,"Invalid Email/Password.",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    //Intent i = new Intent(LoginActivity.this, HomePageActivity.class);
                    //startActivity(i);
                }


            }
        });
        login_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(LoginActivity.this, HomePageActivity.class);
//                startActivity(i);
            }
        });
        signuptext2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(i);
                finish();
            }
        });
        signuptext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(i);
                finish();
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
    }
}
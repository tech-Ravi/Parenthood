package com.example.cysandroidapp.AppHelperActivities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cysandroidapp.R;
import com.example.cysandroidapp.TaskList;

public class LoginActivity extends Activity {
Button btnLogin;
ImageView login_logo;
TextView signuptext,signuptext2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin=findViewById(R.id.btnLogin);
        login_logo=findViewById(R.id.login_logo);
        signuptext=findViewById(R.id.signuptext);
        signuptext2=findViewById(R.id.signuptext2);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, HomePageActivity.class);
                startActivity(i);
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
            }
        });
        signuptext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(i);
            }
        });
    }
}
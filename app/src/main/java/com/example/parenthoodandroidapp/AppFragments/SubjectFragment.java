package com.example.parenthoodandroidapp.AppFragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.parenthoodandroidapp.R;

public class SubjectFragment extends Fragment {
CardView btn_9class,btn_10class,btn_11class,btn_12class;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.subject_fragment, container, false);
        btn_9class= v.findViewById(R.id.btn_9class);
        btn_10class= v.findViewById(R.id.btn_10class);
        btn_11class= v.findViewById(R.id.btn_11class);
        btn_12class= v.findViewById(R.id.btn_12class);
        btn_9class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoToURL("http://edudel.nic.in/asg_file/2020_21/class_9_08122020.htm");
            }
        });
        btn_10class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoToURL("http://edudel.nic.in/asg_file/2020_21/class_10_08122020.htm");
            }
        });
        btn_11class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoToURL("http://edudel.nic.in/asg_file/2020_21/class_11_08122020.htm");
            }
        });
        btn_12class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoToURL("http://edudel.nic.in/asg_file/2020_21/class_12_08122020.htm");
            }
        });
//        TextView tv = (TextView) v.findViewById(R.id.tvFragFirst);
//        tv.setText(getArguments().getString("msg"));

        return v;
    }
    void GoToURL(String url){
        if (url.startsWith("https://") || url.startsWith("http://")) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }else{
            Toast.makeText(getActivity(), "Invalid Url", Toast.LENGTH_SHORT).show();
        }
    }
//    public static FirstFragment newInstance(String text) {
//
//        FirstFragment f = new FirstFragment();
//        Bundle b = new Bundle();
//        b.putString("msg", text);
//
//        f.setArguments(b);
//
//        return f;
//    }
}
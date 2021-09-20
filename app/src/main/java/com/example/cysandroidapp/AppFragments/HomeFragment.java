package com.example.cysandroidapp.AppFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.cysandroidapp.AppHelperActivities.HomePageActivity;
import com.example.cysandroidapp.AppHelperActivities.LoginActivity;
import com.example.cysandroidapp.R;
import com.example.cysandroidapp.TaskList;

public class HomeFragment extends Fragment {

    Button btnCommunity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home_fragment, container, false);
        btnCommunity=v.findViewById(R.id.btnCommunity);
        btnCommunity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), TaskList.class);
                startActivity(i);
            }
        });
//        TextView tv = (TextView) v.findViewById(R.id.tvFragFirst);
//        tv.setText(getArguments().getString("msg"));

        return v;
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
package com.example.cysandroidapp.AppHelperActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.os.Bundle;

import com.example.cysandroidapp.AppFragments.BlankFragment;
import com.example.cysandroidapp.AppFragments.HomeFragment;
import com.example.cysandroidapp.R;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

public class HomePageActivity extends FragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add("Home", HomeFragment.class)
                .add("Subject", BlankFragment.class)
                .add("Growing", BlankFragment.class)
                .add("My Account", BlankFragment.class)
                .create());
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);

        SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);
        viewPagerTab.setViewPager(viewPager);

    }


    /*
    //For ON/Off Switch

    LabeledSwitch labeledSwitch = findViewById(R.id.switch);
    labeledSwitch.setOnToggledListener(new OnToggledListener() {
        @Override
        public void onSwitched(LabeledSwitch labeledSwitch, boolean isOn) {
            // Implement your switching logic here
        }
    });

    */
}
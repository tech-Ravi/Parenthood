package com.example.cysandroidapp.AdsBlock;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.cysandroidapp.AdsBlock.games.GameFragment;
import com.example.cysandroidapp.AdsBlock.vpn.VpnFragment;
import com.example.cysandroidapp.AdsBlock.whitelist.WhitelistAppsFragment;
import com.example.cysandroidapp.R;
import com.example.cysandroidapp.TaskList;
import com.google.android.material.navigation.NavigationView;


//import io.fabric.sdk.android.Fabric;


public class BlockAdsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Fabric.with(this, new Answers(), new Crashlytics());
        setContentView(R.layout.activity_main_blockads);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (Build.VERSION.SDK_INT<21) {
            Menu nav_menu = navigationView.getMenu();
            nav_menu.findItem(R.id.nav_whitelist).setVisible(false);
        }

//        WVersionManager versionManager = new WVersionManager(this);
//        versionManager.setVersionContentUrl("https://block-this.com/version.txt");
//        versionManager.setUpdateUrl("https://block-this.com/block-this-latest.apk");
//        versionManager.setDialogCancelable(false);
//        versionManager.setReminderTimer(720);
//        versionManager.setIgnoreThisVersionLabel(""); //make button invisible
//        versionManager.checkVersion();

        //Initiate shared preferences
        sharedPreferences = this.getSharedPreferences("com.savageorgiev.blockthis", Context.MODE_PRIVATE);
        displaySelectedScreen(R.id.nav_home);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Fragment fragment = null;

        //noinspection SimplifiableIfStatement
        switch(id) {
//            case R.id.action_settings:
//                Answers.getInstance().logCustom(new CustomEvent("3.0 Menu").putCustomAttribute("Type", "Settings"));
//                fragment = new SettingsFragment();
               // break;
        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        displaySelectedScreen(item.getItemId());
        return true;
    }

    private void displaySelectedScreen(int itemId) {
        Fragment fragment = null;
        String type = "Home";
        Log.d("TAG", "id: " + itemId );
        switch(itemId) {
            case R.id.nav_home:
                type = "Home";
                fragment = new VpnFragment();
                break;
            case R.id.nav_whitelist:
                type = "Whitelist";
                Intent i= new Intent(BlockAdsActivity.this, TaskList.class);
                startActivity(i);
                //fragment = new WhitelistAppsFragment();
                break;
            case R.id.nav_games:
                type = "Games";
                //fragment = new GameFragment();
                break;
            case R.id.nav_help:
                type = "Help";
                //fragment = new HelpFragment();
                break;
            case R.id.nav_donate:
                type = "Donate";
                //fragment = new DonateFragment();
                break;
        }


        if (itemId == R.id.nav_share){
           // Answers.getInstance().logCustom(new CustomEvent("3.0 Menu").putCustomAttribute("Type", "Share"));
            shareApp();
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //replacing the fragment
            if (fragment != null) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
            }

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);

           // Answers.getInstance().logCustom(new CustomEvent("3.0 Menu").putCustomAttribute("Type", type));
        }
    }

    private void shareApp(){
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        //sharingIntent.putExtra(Intent.EXTRA_TEXT, "https://block-this.com/");
        startActivity(Intent.createChooser(sharingIntent,"Share CYS using.."));
    }

}
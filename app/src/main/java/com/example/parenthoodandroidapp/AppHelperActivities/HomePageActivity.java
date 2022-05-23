package com.example.parenthoodandroidapp.AppHelperActivities;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationManagerCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.VpnService;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parenthoodandroidapp.AdsBlock.vpn.MainVpnService;
import com.example.parenthoodandroidapp.AdsBlock.vpn.VpnStatusReceiver;
import com.example.parenthoodandroidapp.AppFragments.BlankFragment;
import com.example.parenthoodandroidapp.AppFragments.EditProfileFragment;
import com.example.parenthoodandroidapp.AppFragments.HomeFragment;
import com.example.parenthoodandroidapp.AppFragments.SubjectFragment;
import com.example.parenthoodandroidapp.ChatsActivities.AllChatsActivity;
import com.example.parenthoodandroidapp.HelperClasses.APIHelperClasses.ApiUtils;
import com.example.parenthoodandroidapp.HelperClasses.SharedPrefManager;
import com.example.parenthoodandroidapp.R;
import com.example.parenthoodandroidapp.TaskList;
import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.github.angads25.toggle.model.ToggleableView;
import com.github.angads25.toggle.widget.LabeledSwitch;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomePageActivity extends FragmentActivity {
    ImageView btnNavigation,btnNavigationClose,show_dnd_info_button;
    CircleImageView userProfilePic;
    DrawerLayout drawer_view;
    NavigationView navigationView;
    LinearLayout btnChats,btnAbout,btn_rate_us,btnUpdateTime,btnShareApp,btn_contact_us,btn_logout,btnManageApp;
    HorizontalScrollView mode_view;
    TextView btnSelectMode,btnCloseMode,textKidEnable,textStudyEnable,textExamEnable,btn_manage_apps,username1,btn_edit_profile;
    LabeledSwitch studymode,exammode,kidmode,switch_dnd;
    public MainVpnService mainService;
    boolean isBound = false;
    private VpnStatusReceiver vpnStatusReceiver;
    private IntentFilter filter;
    boolean valStudy=false, valExa=false, valKid=false;
    CheckBox cb_study;
    LinearLayout dnd_view;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        btnNavigation=findViewById(R.id.btnNavigation);
        drawer_view=findViewById(R.id.drawer_view);

        navigationView=findViewById(R.id.navigationView);
        mode_view=findViewById(R.id.mode_view);
        btnSelectMode=findViewById(R.id.btnSelectMode);
        btnCloseMode=findViewById(R.id.btnCloseMode);
        studymode=findViewById(R.id.studymode);
        exammode=findViewById(R.id.exammode);
        kidmode=findViewById(R.id.kidmode);
        dnd_view=findViewById(R.id.dnd_view);
        textKidEnable=findViewById(R.id.textKidEnable);
        textExamEnable=findViewById(R.id.textExamEnable);
        switch_dnd=findViewById(R.id.switch_dnd);
        show_dnd_info_button=findViewById(R.id.show_dnd_info_button);
        textStudyEnable=findViewById(R.id.textStudyEnable);
        btn_manage_apps=findViewById(R.id.btn_manage_apps);


        auth=FirebaseAuth.getInstance();

        if(auth.getCurrentUser()==null){
            Intent i =new Intent(HomePageActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
        }
        auth=FirebaseAuth.getInstance();
       FirebaseDatabase database= FirebaseDatabase.getInstance(ApiUtils.FIREBASE_DB_URL);

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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawer_view.setVisibility(View.VISIBLE);
                drawer_view.openDrawer(navigationView);


            }
        });

        View v=findViewById(R.id.navigationlayout);
        btnNavigationClose=v.findViewById(R.id.btnNavigationClose);
        btnAbout=v.findViewById(R.id.btnAbout);
        btnChats=v.findViewById(R.id.btnChats);
        btn_rate_us=v.findViewById(R.id.btn_rate_us);
        btnUpdateTime=v.findViewById(R.id.btnUpdateTime);
        btnShareApp=v.findViewById(R.id.btnShareApp);
        btn_logout=v.findViewById(R.id.btn_logout);
        btn_contact_us=v.findViewById(R.id.btn_contact_us);
        userProfilePic=v.findViewById(R.id.userProfilePic);
        username1=v.findViewById(R.id.username1);
        btnManageApp=v.findViewById(R.id.btnManageApp);
        btn_edit_profile=v.findViewById(R.id.btn_edit_profile);
        btnManageApp.setVisibility(View.GONE);
        btnUpdateTime.setVisibility(View.GONE);
        btnManageApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i =new Intent(HomePageActivity.this, TaskList.class);
                startActivity(i);

            }
        });

        btnNavigationClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer_view.setVisibility(View.GONE);
                drawer_view.closeDrawer(navigationView);

            }
        });
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPrefManager.getInstans(HomePageActivity.this).logout();
                FirebaseAuth.getInstance().signOut();
                Intent i =new Intent(HomePageActivity.this, LoginActivity.class);
                startActivity(i);
                finish();

            }
        });
        btnUpdateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(HomePageActivity.this, UpdateTimeTableActivity.class);
                startActivity(i);

            }
        });
        btnShareApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(HomePageActivity.this, ShareAppActivity.class);
                startActivity(i);

            }
        });
        btnChats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(HomePageActivity.this, AllChatsActivity.class);
                startActivity(i);

            }
        });
        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(HomePageActivity.this, AboutActivity2.class);
                startActivity(i);

            }
        });
        btn_rate_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(HomePageActivity.this, RateUsActivity.class);
                startActivity(i);

            }
        });
        btn_contact_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(HomePageActivity.this, ContactUsActivity.class);
                startActivity(i);

            }
        });


        btnSelectMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mode_view.setVisibility(View.VISIBLE);

            }
        });
        btnCloseMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mode_view.animate().alpha(1.0f);
                mode_view.setVisibility(View.GONE);
            }
        });
        btn_manage_apps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i =new Intent(HomePageActivity.this, TaskList.class);
                startActivity(i);

            }
        });
        show_dnd_info_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(HomePageActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_ACTION_MODE_OVERLAY);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.dnd_mode_custom_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                Button btn_ok = (Button) dialog.findViewById(R.id.btn_ok);
                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


                dialog.show();
            }
        });

        drawer_view.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                Log.e("checkDrawer","Slide");
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                Log.e("checkDrawer","Open");
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                Log.e("checkDrawer","Close");
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                if (newState==2) {
                    drawer_view.setVisibility(View.GONE);
                    drawer_view.closeDrawer(navigationView);
                    Log.e("checkDrawer","Close");
                }else {
                    Log.e("checkDrawer","Open");
                    //drawer_view.openDrawer(GravityCompat.END);
                }
            }
        });
        //drawer_view.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add("Home", HomeFragment.class)
                .add("Subject", SubjectFragment.class)
                .add("Growing", BlankFragment.class)
                .add("My Account", EditProfileFragment.class)
                .create());
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);

        SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);
        viewPagerTab.setViewPager(viewPager);
        String ModeValue=SharedPrefManager.getInstans(HomePageActivity.this).getKEY_ModeValue();
        Log.e("checkKidModeVal1",valKid+"    "+kidmode.isOn()+""+"  ModeValue:- "+ModeValue+"    "+SharedPrefManager.getInstans(getApplicationContext()).getKeyDontShowStudyStatus());

        btn_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        if(ModeValue!=null)
        if(ModeValue.equals("1")){
            btnSelectMode.setText("Study Mode");
            studymode.setOn(true);
            dnd_view.setVisibility(View.VISIBLE);
            btnManageApp.setVisibility(View.VISIBLE);
            btnUpdateTime.setVisibility(View.VISIBLE);
            textStudyEnable.setVisibility(View.VISIBLE);
            exammode.setEnabled(false);
            kidmode.setEnabled(false);
        }
        if(ModeValue!=null)
        if(ModeValue.equals("2")){
            btnSelectMode.setText("Exam Mode");
            exammode.setOn(true);
            studymode.setEnabled(false);
            kidmode.setEnabled(false);
            dnd_view.setVisibility(View.VISIBLE);
            btnManageApp.setVisibility(View.VISIBLE);
            btnUpdateTime.setVisibility(View.VISIBLE);
            textExamEnable.setVisibility(View.VISIBLE);
        }
        if(ModeValue!=null)
        if(ModeValue.equals("3")){
            btnSelectMode.setText("Kids Mode");
            mode_view.setVisibility(View.VISIBLE);
            kidmode.setOn(true);
            exammode.setEnabled(false);
            studymode.setEnabled(false);
            dnd_view.setVisibility(View.GONE);
            btnManageApp.setVisibility(View.GONE);
            btnUpdateTime.setVisibility(View.GONE);
            textKidEnable.setVisibility(View.VISIBLE);
        }
        if (NotificationManagerCompat.getEnabledListenerPackages(HomePageActivity.this).contains(getPackageName())){
            switch_dnd.setOn(true);
        }else{
            switch_dnd.setOn(false);
        }
        switch_dnd.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
              if(switch_dnd.isOn()){
                  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                      if (!NotificationManagerCompat.getEnabledListenerPackages(HomePageActivity.this).contains(getPackageName())) {        //ask for permission
                          Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
                          //intent.putExtra(Settings.EXTRA_NOTIFICATION_LISTENER_COMPONENT_NAME, myComponentName.flattenToString());
                          startActivity(intent);
                      }
                  }
                  //alertDi(getString(R.string.alerttitle), getString(R.string.perminfo), Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS);

              }else{
                  alertDi("Notification Service", "Disable notification service to get upcoming notifications.", Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS);
              }
            }

        });


        studymode.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
               // if(!valKid){
                if(studymode.isOn()){
                    exammode.setOn(false);
                    textStudyEnable.setVisibility(View.VISIBLE);

                    textExamEnable.setVisibility(View.GONE);
                    //kidmode.setOn(false);
                    final Dialog dialog = new Dialog(HomePageActivity.this);
                    dialog.requestWindowFeature(Window.FEATURE_ACTION_MODE_OVERLAY);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.study_mode_custom_dialog);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.getWindow().setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

//                    TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
//                    text.setText("this is test message.");
//

//                    cb_study=dialog.findViewById(R.id.cb_study);
//                    if (cb_study.isChecked())
//                    {
//                        SharedPrefManager.getInstans(getApplicationContext()).DontShowStudyStatus("1");
//                    }
//                    if(SharedPrefManager.getInstans(getApplicationContext()).getKeyDontShowStudyStatus().equals("1")){
//                        cb_study.setVisibility(View.GONE);
//                    }
//                    cb_study.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                        @Override
//                        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//
//                            if (cb_study.isChecked()) {
//                                SharedPrefManager.getInstans(getApplicationContext()).DontShowStudyStatus("1");
//                            } else {
//                                //SharedPrefManager.getInstans(getApplicationContext()).DontShowStudyStatus("0");
//                            }
//
//                        }
//                    });


                    Button btn_ok = (Button) dialog.findViewById(R.id.btn_ok);
                    btn_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            mode_view.animate().alpha(1.0f);
                            mode_view.setVisibility(View.GONE);
                            dnd_view.setVisibility(View.VISIBLE);
                            btnManageApp.setVisibility(View.VISIBLE);
                            btnUpdateTime.setVisibility(View.VISIBLE);
                            exammode.setEnabled(false);
                            kidmode.setEnabled(false);
                            btnSelectMode.setText("Study Mode");
                            SharedPrefManager.getInstans(getApplicationContext()).ModeValue("1");
                            Intent i =new Intent(HomePageActivity.this, TaskList.class);
                            startActivity(i);
                        }
                    });
                    Button btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
                    btn_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            studymode.setOn(false);
                            SharedPrefManager.getInstans(getApplicationContext()).ModeValue("0");
                            textStudyEnable.setVisibility(View.GONE);
                            dnd_view.setVisibility(View.GONE);
                            exammode.setEnabled(true);
                            kidmode.setEnabled(true);
                            Toast.makeText(HomePageActivity.this,"Please Wait,\nDeactivating Study Mode...",Toast.LENGTH_SHORT).show();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    finish();
                                    overridePendingTransition( 0, 0);
                                    startActivity(getIntent());
                                    overridePendingTransition( 0, 0);
                                }
                            }, 1000);
                        }
                    });

                    dialog.show();
                }
                else{
                    dnd_view.setVisibility(View.GONE);
                    btnManageApp.setVisibility(View.GONE);
                    btnUpdateTime.setVisibility(View.GONE);
                    textStudyEnable.setVisibility(View.GONE);
                    exammode.setEnabled(true);
                    kidmode.setEnabled(true);
                    SharedPrefManager.getInstans(getApplicationContext()).ModeValue("0");
                    Toast.makeText(HomePageActivity.this,"Please Wait,\nDeactivating Study Mode...",Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                            overridePendingTransition( 0, 0);
                            startActivity(getIntent());
                            overridePendingTransition( 0, 0);
                        }
                    }, 1000);

                }
//                }else{
//                    Log.e("checkKidModeVal2",valKid+"    "+kidmode.isOn()+"");
//                    if(kidmode.isOn())
//                        studymode.setOn(true);
//                    Toast.makeText(HomePageActivity.this,"Please Deactivate Kids Mode first.",Toast.LENGTH_LONG).show();
//                }
            }

        });

        exammode.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
               // if(!valKid){
                if(exammode.isOn()){
                    studymode.setOn(false);
                    textStudyEnable.setVisibility(View.GONE);
                    textExamEnable.setVisibility(View.VISIBLE);
                    //kidmode.setOn(false);
                    final Dialog dialog = new Dialog(HomePageActivity.this);
                    dialog.requestWindowFeature(Window.FEATURE_ACTION_MODE_OVERLAY);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.exam_mode_custom_dialog);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.getWindow().setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

//                    TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
//                    text.setText("this is test message.");
//
                    Button btn_ok = (Button) dialog.findViewById(R.id.btn_ok);
                    btn_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            mode_view.animate().alpha(1.0f);
                            mode_view.setVisibility(View.GONE);
                            dnd_view.setVisibility(View.VISIBLE);
                            btnManageApp.setVisibility(View.VISIBLE);
                            btnUpdateTime.setVisibility(View.VISIBLE);
                            studymode.setEnabled(false);
                            kidmode.setEnabled(false);
                            btnSelectMode.setText("Exam Mode");
                            SharedPrefManager.getInstans(getApplicationContext()).ModeValue("2");
                            Intent i =new Intent(HomePageActivity.this, TaskList.class);
                            startActivity(i);
                        }
                    });
                    Button btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
                    btn_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            exammode.setOn(false);
                            SharedPrefManager.getInstans(getApplicationContext()).ModeValue("0");
                            textExamEnable.setVisibility(View.GONE);
                            dnd_view.setVisibility(View.GONE);
                            studymode.setEnabled(true);
                            kidmode.setEnabled(true);
                            Toast.makeText(HomePageActivity.this,"Please Wait,\nDeactivating Exam Mode...",Toast.LENGTH_SHORT).show();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    finish();
                                    overridePendingTransition( 0, 0);
                                    startActivity(getIntent());
                                    overridePendingTransition( 0, 0);
                                }
                            }, 1000);
                        }
                    });

                    dialog.show();
                }
                else{
                    dnd_view.setVisibility(View.GONE);
                    btnManageApp.setVisibility(View.GONE);
                    btnUpdateTime.setVisibility(View.GONE);
                    textExamEnable.setVisibility(View.GONE);
                    studymode.setEnabled(true);
                    kidmode.setEnabled(true);
                    SharedPrefManager.getInstans(getApplicationContext()).ModeValue("0");
                    Toast.makeText(HomePageActivity.this,"Please Wait,\nDeactivating Exam Mode...",Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                            overridePendingTransition( 0, 0);
                            startActivity(getIntent());
                            overridePendingTransition( 0, 0);
                        }
                    }, 1000);
                }
//                }else{
//                   // Log.e("checkKidModeVal",kidmode.isOn()+"");
//                    Toast.makeText(HomePageActivity.this,"Please Deactivate Kids Mode first.",Toast.LENGTH_LONG).show();
//                    if(kidmode.isOn())
//                      exammode.setOn(true);
//                }
                }

        });



        kidmode.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                if(kidmode.isOn()){
                    exammode.setEnabled(false);
                    studymode.setEnabled(false);

                    Toast.makeText(HomePageActivity.this,"Please Wait,\nActivating Kids Mode...",Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            SharedPrefManager.getInstans(getApplicationContext()).ModeValue("3");
                            textKidEnable.setVisibility(View.VISIBLE);
                            startVpn();
                            valKid=true;
                            finish();
                            overridePendingTransition( 0, 0);
                            startActivity(getIntent());
                            overridePendingTransition( 0, 0);
                        }
                    }, 1000);

                }else{
                    SharedPrefManager.getInstans(getApplicationContext()).ModeValue("0");
                    valKid=false;
                    textKidEnable.setVisibility(View.GONE);
                    if (mainService != null){
                        mainService.kill();
                    }
                    kidmode.setOn(false);
                    exammode.setEnabled(true);
                    studymode.setEnabled(true);
                    Toast.makeText(HomePageActivity.this,"Please Wait,\nDeactivating Kids Mode...",Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                            overridePendingTransition( 0, 0);
                            startActivity(getIntent());
                            overridePendingTransition( 0, 0);
                        }
                    }, 1000);
                }
            }
        });
        vpnStatusReceiver = new VpnStatusReceiver() {
            @Override
            public void onVpnStartReceived() {
                kidmode.setOn(true);
            }
        };
        filter = new IntentFilter("vpn.start");
        HomePageActivity.this.registerReceiver(vpnStatusReceiver, filter);
        if (MainVpnService.isRunning) {
            HomePageActivity.this.bindService(new Intent(HomePageActivity.this, MainVpnService.class), mainConnection, Context.BIND_AUTO_CREATE);
            kidmode.setOn(true);
        }

    }

    public void alertDi(String Title, String msg, final String action) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(msg);
        builder.setTitle(Title);
        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(action);
                startActivity(intent);

//                SharedPreferences home = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//                startActivity(new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME));
//                ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
//                home.edit().putString("home", am.getRunningTasks(1).get(0).topActivity.getPackageName()).apply();
            }
        });
        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch_dnd.setOn(true);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private ServiceConnection mainConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MainVpnService.MyLocalBinder binder = (MainVpnService.MyLocalBinder) service;
            mainService = binder.getService();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
        }
    };
    public void startVpn(){
        Intent intent = VpnService.prepare(HomePageActivity.this);
        if (intent != null) {
            startActivityForResult(intent, 0);
        } else {
            onActivityResult(0, Activity.RESULT_OK, null);
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        if (isBound) {
            HomePageActivity.this.unbindService(mainConnection);
            isBound = false;
        }
        HomePageActivity.this.unregisterReceiver(vpnStatusReceiver);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (MainVpnService.isRunning && !isBound) {
            HomePageActivity.this.bindService(new Intent(HomePageActivity.this, MainVpnService.class), mainConnection, Context.BIND_AUTO_CREATE);
            //updateConnectedUI();
        } else {
            //updateDisconnectedUI();
        }
        HomePageActivity.this.registerReceiver(vpnStatusReceiver, filter);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Intent i = new Intent(HomePageActivity.this, MainVpnService.class);
            HomePageActivity.this.startService(i);
            HomePageActivity.this.bindService(
                    new Intent(HomePageActivity.this,
                            MainVpnService.class),
                    mainConnection, Context.BIND_AUTO_CREATE);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}



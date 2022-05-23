package com.example.parenthoodandroidapp.AppHelperActivities;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.parenthoodandroidapp.HelperClasses.SharedPrefManager;
import com.example.parenthoodandroidapp.R;
import com.example.parenthoodandroidapp.TaskList;

public class UpdateTimeTableActivity extends Activity {
    ImageView btnBack;
    TextView firstTime,secondTime;
    Button btnNext,btnClear;
    String strFirstTime="",strSecondTime="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_timetable_activity);
        btnBack=findViewById(R.id.btnBack);
        firstTime = findViewById(R.id.firstTime);
        secondTime = findViewById(R.id.secondTime);
        btnNext= findViewById(R.id.btnNext);
        btnClear= findViewById(R.id.btnClear);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefManager.getInstans(getApplicationContext()).storeUpdateTimetable("", "");
                btnClear.setVisibility(View.GONE);
                overridePendingTransition(0, 0);
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);

            }
        });

        if(SharedPrefManager.getInstans(getApplicationContext()).getKeyFirstTime()!=null)
            if(!SharedPrefManager.getInstans(getApplicationContext()).getKeyFirstTime().equals("")){
                btnClear.setVisibility(View.VISIBLE);
                //Toast.makeText(UpdateTimeTableActivity.this,SharedPrefManager.getInstans(getApplicationContext()).getKeyFirstTime(),Toast.LENGTH_SHORT).show();
                String[] separated = SharedPrefManager.getInstans(getApplicationContext()).getKeyFirstTime().split("\\:");
                //Toast.makeText(AppSettingActivity.this,SharedPrefManager.getInstans(getApplicationContext()).getKeyFirstTime()+"    "+separated[0]+" {}{}{}{} "+separated[1],Toast.LENGTH_SHORT).show();

                firstTime.setText(timeFormat(Integer.parseInt(separated[0]),Integer.parseInt(separated[1])));
            }else{
                btnClear.setVisibility(View.GONE);
            }
        if(SharedPrefManager.getInstans(getApplicationContext()).getKeySecondTime()!=null)
            if(!SharedPrefManager.getInstans(getApplicationContext()).getKeySecondTime().equals("")){
                    //Toast.makeText(UpdateTimeTableActivity.this,SharedPrefManager.getInstans(getApplicationContext()).getKeySecondTime(),Toast.LENGTH_SHORT).show();
                String[] separated = SharedPrefManager.getInstans(getApplicationContext()).getKeySecondTime().split("\\:");
                //Toast.makeText(AppSettingActivity.this,SharedPrefManager.getInstans(getApplicationContext()).getKeyFirstTime()+"    "+separated[0]+" {}{}{}{} "+separated[1],Toast.LENGTH_SHORT).show();

                secondTime.setText(timeFormat(Integer.parseInt(separated[0]),Integer.parseInt(separated[1])));
                 }

        //updating starting time to block app
        final TimePickerDialog timePickerDialog1 = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                firstTime.setText(timeFormat(hourOfDay, minute));
                strFirstTime=hourOfDay+":"+minute;
//                myDB.updateData(package_name, "IsPermanent", 0);
//                myDB.updateData(package_name, StartTimeInHours, hourOfDay);
//                myDB.updateData(package_name, StartTimeInMinutes, minute);
            }
        }, 0, 0, false);
        firstTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog1.show();
            }
        });
        //updating ending time to block app
        final TimePickerDialog timePickerDialog2 = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                secondTime.setText(timeFormat(hourOfDay, minute));
                strSecondTime=hourOfDay+":"+minute;
//                myDB.updateData(package_name, "IsPermanent", 0);
//                myDB.updateData(package_name, EndTimeInHours, hourOfDay);
//                myDB.updateData(package_name, EndTimeInMinutes, minute);
                //alertDialog1.show();

            }
        }, 0, 0, false);
        secondTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //weekSelector = false;
                timePickerDialog2.show();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(firstTime.getText().toString().equals("") ){
                    Toast.makeText(UpdateTimeTableActivity.this,"Please select first time to continue.",Toast.LENGTH_SHORT).show();
                }else if(secondTime.getText().toString().equals("")) {
                    Toast.makeText(UpdateTimeTableActivity.this,"Please select second time to continue.",Toast.LENGTH_SHORT).show();
                }else{
                    btnClear.setVisibility(View.VISIBLE);
                    SharedPrefManager.getInstans(getApplicationContext()).storeUpdateTimetable(strFirstTime, strSecondTime);
                    Toast.makeText(UpdateTimeTableActivity.this,"Time saved, Please wait..",Toast.LENGTH_SHORT).show();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent i =new Intent(UpdateTimeTableActivity.this, TaskList.class);
                            startActivity(i);
                        }
                    }, 2000);



                }
            }
        });
    }
    static String timeFormat(int hourOfDay, int minute) {
        String am_pm = "AM";
        if (hourOfDay > 12) {
            hourOfDay = hourOfDay - 12;
            am_pm = "PM";
        } else if (hourOfDay == 12) {
            am_pm = "PM";
        } else if (hourOfDay == 0) {
            hourOfDay = 12;
        }
        return hourOfDay + ":" + minute + ":" + am_pm;
    }
}

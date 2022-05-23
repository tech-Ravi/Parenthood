package com.example.parenthoodandroidapp.HelperClasses;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    private static SharedPrefManager minst;
    private static Context mct;

    private static final String SHARD_PERFNAME="CYS";
    private static final String KEY_ID="userid";
    private static final String KEY_USERNAME="name";
    private static final String KEY_EMAIL="email";
    private static final String KEY_Mode ="0";
    private static final String KEY_DONT_SHOW_STUDY_STATUS ="0";
    private static final String KEY_DONT_SHOW_EXAM_STATUS ="0";
    private static final String KEY_ACCESTOKEN="accestoken";
    private static final String KEY_FIRST_TIME="first_time";
    private static final String KEY_SECOND_TIME="second_time";
    //private static final String KEY_IS_PLAYING="NO";

    private SharedPrefManager(Context context){
        mct=context;
    }
    public static synchronized SharedPrefManager getInstans(Context context){
        if (minst==null){
            minst=new SharedPrefManager(context);
        }
        return minst;
    }
    public boolean userLogin(String userid,String email,String accestoken){
        SharedPreferences sharedPreferences=mct.getSharedPreferences(SHARD_PERFNAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(KEY_ID,userid);
        editor.putString(KEY_EMAIL,email);
        editor.putString(KEY_ACCESTOKEN,accestoken);
        editor.apply();
        return true;
    }
    public boolean storeUpdateTimetable(String key_first_time,String key_second_time){
        SharedPreferences sharedPreferences=mct.getSharedPreferences(SHARD_PERFNAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(KEY_FIRST_TIME,key_first_time);
        editor.putString(KEY_SECOND_TIME,key_second_time);
        editor.apply();
        return true;
    }

    public boolean ModeValue(String position){
        SharedPreferences sharedPreferences=mct.getSharedPreferences(SHARD_PERFNAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(KEY_Mode,position);
        editor.apply();
        return true;
    }
    public boolean DontShowStudyStatus(String status){
        SharedPreferences sharedPreferences=mct.getSharedPreferences(SHARD_PERFNAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(KEY_DONT_SHOW_STUDY_STATUS,status);
        editor.apply();
        return true;
    }
    public boolean DontShowExamStatus(String status){
        SharedPreferences sharedPreferences=mct.getSharedPreferences(SHARD_PERFNAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(KEY_DONT_SHOW_EXAM_STATUS,status);
        editor.apply();
        return true;
    }

    public boolean userLoginName(String name){
        SharedPreferences sharedPreferences=mct.getSharedPreferences(SHARD_PERFNAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(KEY_USERNAME,name);
        editor.apply();
        return true;
    }

    public boolean isLogin(){
        SharedPreferences sharedPreferences=mct.getSharedPreferences(SHARD_PERFNAME,Context.MODE_PRIVATE);
        if (sharedPreferences.getString(KEY_ID,null)!=null){
            return true;
        }
        return false;
    }
    public boolean logout(){
        SharedPreferences sharedPreferences=mct.getSharedPreferences(SHARD_PERFNAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;

    }
    public boolean clearStoredTimeData(){
        SharedPreferences sharedPreferences=mct.getSharedPreferences(SHARD_PERFNAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;

    }
    public String getKEY_ModeValue(){
        SharedPreferences sharedPreferences=mct.getSharedPreferences(SHARD_PERFNAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_Mode,null);

    }
    public String getKeyEmail(){
        SharedPreferences sharedPreferences=mct.getSharedPreferences(SHARD_PERFNAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EMAIL,null);

    }
    public String getKeyFirstTime(){
        SharedPreferences sharedPreferences=mct.getSharedPreferences(SHARD_PERFNAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_FIRST_TIME,null);

    }
    public String getKeySecondTime(){
        SharedPreferences sharedPreferences=mct.getSharedPreferences(SHARD_PERFNAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_SECOND_TIME,null);

    }
    public String getUserId(){
        SharedPreferences sharedPreferences=mct.getSharedPreferences(SHARD_PERFNAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_ID,null);

    }
    public String getUsername(){
        SharedPreferences sharedPreferences=mct.getSharedPreferences(SHARD_PERFNAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME,null);

    }
    public String getKeyAccestoken(){
        SharedPreferences sharedPreferences=mct.getSharedPreferences(SHARD_PERFNAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_ACCESTOKEN,null);

    }
    public String getKeyDontShowStudyStatus(){
        SharedPreferences sharedPreferences=mct.getSharedPreferences(SHARD_PERFNAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_DONT_SHOW_STUDY_STATUS,null);


    }
    public String getKeyDontShowExamStatus(){
        SharedPreferences sharedPreferences=mct.getSharedPreferences(SHARD_PERFNAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_DONT_SHOW_EXAM_STATUS,null);


    }
}
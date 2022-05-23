package com.example.parenthoodandroidapp.HelperClasses.APIHelperClasses;

public class ApiUtils {

    public static final String BASE_URL = "";
    public static final String FIREBASE_DB_URL="https://parenthood-demo-default-rtdb.firebaseio.com/";

    public static UserService getUserService(){
        return RetrofitClient.getClient(BASE_URL).create(UserService.class);
    }
}
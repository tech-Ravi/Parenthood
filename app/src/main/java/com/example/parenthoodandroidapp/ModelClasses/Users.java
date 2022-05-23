package com.example.parenthoodandroidapp.ModelClasses;

public class Users {

    String userId;
    String name;
    String email;
    String ImageUri;
    String status;

    public Users() {

    }

    public Users(String userId, String name, String email, String imageUri,String status) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.ImageUri = imageUri;
        this.status=status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUri() {
        return ImageUri;
    }

    public void setImageUri(String imageUri) {
        ImageUri = imageUri;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        status = status;
    }



}

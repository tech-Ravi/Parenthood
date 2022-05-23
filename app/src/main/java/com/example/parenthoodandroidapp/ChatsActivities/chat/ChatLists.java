package com.example.parenthoodandroidapp.ChatsActivities.chat;

public class ChatLists {

    private String name, senderId, message, profilePic;
    long timeStamp;

    public ChatLists(){

    }

    public ChatLists(String name, String senderId, String message, String profilePic, long timeStamp) {
        this.name = name;
        this.senderId = senderId;
        this.message = message;
        this.profilePic = profilePic;
        this.timeStamp = timeStamp;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }




}

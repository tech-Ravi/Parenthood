package com.example.parenthoodandroidapp.HelperClasses;

import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

public class NotificationListener extends NotificationListenerService {

    @Override
    public void onNotificationPosted(StatusBarNotification statusBarNotification) {
        cancelNotification(statusBarNotification.getKey());
    }
}
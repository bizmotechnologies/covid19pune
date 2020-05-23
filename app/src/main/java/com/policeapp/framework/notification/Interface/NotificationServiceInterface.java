package com.policeapp.framework.notification.Interface;


import com.policeapp.framework.notification.Bean.PushNotificationRegistrationRequestBean;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * By Bizmo Technologies
 */

public interface NotificationServiceInterface {

    @POST("/notification-service/notification/register/{appUserId}")
    public void regsiterForPushNotification(@Path("appUserId") String id, @Body PushNotificationRegistrationRequestBean bean, Callback<?> response);
}

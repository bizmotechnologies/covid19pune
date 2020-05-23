package com.policeapp.framework.notification.Bean;

import java.io.Serializable;

/**
 * By Bizmo Technologies
 */

public class PushNotificationRegistrationRequestBean implements Serializable {

    private String notificationRegistrationSource = "MOBILE";

    private String deviceToken,preferredLangaugeName,preferredLanguageId,registeredRole;

    public String getNotificationRegistrationSource() {
        return notificationRegistrationSource;
    }

    public void setNotificationRegistrationSource(String notificationRegistrationSource) {
        this.notificationRegistrationSource = notificationRegistrationSource;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getPreferredLangaugeName() {
        return preferredLangaugeName;
    }

    public void setPreferredLangaugeName(String preferredLangaugeName) {
        this.preferredLangaugeName = preferredLangaugeName;
    }

    public String getPreferredLanguageId() {
        return preferredLanguageId;
    }

    public void setPreferredLanguageId(String preferredLanguageId) {
        this.preferredLanguageId = preferredLanguageId;
    }

    public String getRegisteredRole() {
        return registeredRole;
    }

    public void setRegisteredRole(String registeredRole) {
        this.registeredRole = registeredRole;
    }
}

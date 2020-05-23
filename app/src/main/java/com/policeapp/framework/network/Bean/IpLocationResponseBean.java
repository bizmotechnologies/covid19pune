package com.policeapp.framework.network.Bean;

import java.io.Serializable;

/**
 * Created by Bizmo Technologies
 */
public class IpLocationResponseBean implements Serializable {

    private String latitude,longitude,country;

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}

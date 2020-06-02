package com.policeapp.source.postlogin.features.records.bean;

import java.io.Serializable;

public class ContactedPersonBean implements Serializable {
    private String name;
    private String number;
    private String address;

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getAddress() {
        return address;
    }
}

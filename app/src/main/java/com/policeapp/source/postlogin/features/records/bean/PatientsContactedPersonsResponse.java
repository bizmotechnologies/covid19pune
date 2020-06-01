package com.policeapp.source.postlogin.features.records.bean;

import com.policeapp.framework.network.Bean.CommonResponse;

import java.io.Serializable;
import java.util.ArrayList;

public class PatientsContactedPersonsResponse extends CommonResponse implements Serializable {
    private ArrayList<ContactedPersonBean> data;

    public ArrayList<ContactedPersonBean> getData() {
        return data;
    }
}

package com.policeapp.source.postlogin.features.records.bean;

import com.policeapp.framework.network.Bean.CommonResponse;

import java.io.Serializable;
import java.util.ArrayList;

public class PatientByStationResponse extends CommonResponse implements Serializable {
    private ArrayList<PatientByStationBean> data;

    public ArrayList<PatientByStationBean> getData() {
        return data;
    }
}

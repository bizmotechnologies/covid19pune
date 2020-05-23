package com.policeapp.source.postlogin.features.locate_patient.bean;

import com.policeapp.framework.network.Bean.CommonResponse;

import java.io.Serializable;
import java.util.ArrayList;

public class StationResponseBean extends CommonResponse implements Serializable {
    private ArrayList<StationBean> data;

    public ArrayList<StationBean> getData() {
        return data;
    }

    public void setData(ArrayList<StationBean> data) {
        this.data = data;
    }
}

package com.policeapp.source.postlogin.features.locate_patient.bean;

import com.policeapp.framework.network.Bean.CommonResponse;

import java.io.Serializable;
import java.util.ArrayList;

public class StationRecordResponseBean extends CommonResponse implements Serializable {
    private ArrayList<StationRecordBean> data;

    public ArrayList<StationRecordBean> getData() {
        return data;
    }
}

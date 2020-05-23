package com.policeapp.source.postlogin.features.locate_patient.interfaces;

import com.policeapp.source.postlogin.features.locate_patient.bean.ContactedPersonBean;

import java.util.ArrayList;

public interface ContactPersonListener {
    void onDelete(int position);
    void onEdit(int position, ContactedPersonBean bean);
    void onDialogDismiss(ArrayList<ContactedPersonBean> list);
    void onLocationCapture(String Latitude, String Longitude);
}

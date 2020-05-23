package com.policeapp.source.postlogin.features.locate_patient.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.policeapp.R;
import com.policeapp.framework.Utils.Utils;
import com.policeapp.framework.Widgets.AppBottomSheetDialogFragment;
import com.policeapp.source.postlogin.HomeActivity;
import com.policeapp.source.postlogin.features.locate_patient.adapter.ContactedPersonAdapter;
import com.policeapp.source.postlogin.features.locate_patient.bean.ContactedPersonBean;
import com.policeapp.source.postlogin.features.locate_patient.interfaces.ContactPersonListener;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactPersonFragment extends BottomSheetDialogFragment implements ContactPersonListener, View.OnClickListener{
    private ArrayList<ContactedPersonBean> list;
    private RecyclerView recyclerView;
    private EditText mAddress, mName, mContact;
    private Button mAdd,mDone;
    private ContactPersonListener listener;
    private ContactedPersonAdapter adapter;
    private View mParent;
    private int selectedIndex = -1;
    public ContactPersonFragment(ArrayList<ContactedPersonBean> list,ContactPersonListener listener) {
        // Required empty public constructor
        this.list = list;
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mParent = inflater.inflate(R.layout.fragment_contact_person, container, false);
        return mParent;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((HomeActivity) Objects.requireNonNull(getActivity())).hideBottomMenu();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        recyclerView    = view.findViewById(R.id.recycler);
//        mClose          = view.findViewById(R.id.close_dialog);
        mName           = view.findViewById(R.id.person_name);
        mContact        = view.findViewById(R.id.person_contact);
        mAddress        = view.findViewById(R.id.person_address);
        mDone           = view.findViewById(R.id.btn_done);
        mAdd            = view.findViewById(R.id.btn_add_person);

        mAdd.setOnClickListener(this);
        mDone.setOnClickListener(this);

        adapter = new ContactedPersonAdapter(this,list,true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerView.setAdapter(adapter);

//        mAddress.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if(actionId== EditorInfo.IME_ACTION_DONE){
//                    addPerson();
//                }
//                return false;
//            }
//        });
    }

    @Override
    public void onDelete(int position) {

    }

    @Override
    public void onEdit(int position, ContactedPersonBean bean) {
        mAddress.setText(bean.getAddress());
        mName.setText(bean.getName());
        mContact.setText(bean.getNumber());
        selectedIndex = position;
    }

    @Override
    public void onDialogDismiss(ArrayList<ContactedPersonBean> list) {

    }

    @Override
    public void onLocationCapture(String Latitude, String Longitude) {

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_add_person:
                if (Utils.isEmptyOrNull(mName)){
                    Utils.showSnackBarNotificationError("Person name is required",mParent);
                    return;
                }
                if (Utils.isEmptyOrNull(mContact)){
                    Utils.showSnackBarNotificationError("Person contact is required",mParent);
                    return;
                }
                if (mContact.getText().toString().length()!=10){
                    Utils.showSnackBarNotificationError("Invalid contact number",mParent);
                    return;
                }
                if (Utils.isEmptyOrNull(mAddress)){
                    Utils.showSnackBarNotificationError("Please address required",mParent);
                    return;
                }
                addPerson();
                closeFragment();
                break;
            case R.id.close_dialog:
                closeFragment();
                break;
            case R.id.btn_done:
                closeFragment();
                break;
        }
    }

    private void closeFragment(){
        if(listener!=null)
            listener.onDialogDismiss(adapter.getList());
//        getActivity().onBackPressed();
        dismiss();
    }

    private void addPerson(){
        String name     = mName.getText().toString().trim();
        String contact  = mContact.getText().toString().trim();
        String address  = mAddress.getText().toString().trim();

        ContactedPersonBean bean = new ContactedPersonBean();
        bean.setAddress(address);
        bean.setName(name);
        bean.setNumber(contact);

        if (selectedIndex == -1)
            adapter.addItem(bean);
        else
            adapter.updateItem(bean,selectedIndex);
        selectedIndex = -1;
        mName.setText("");
        mAddress.setText("");
        mContact.setText("");
        mName.requestFocus();
    }
}

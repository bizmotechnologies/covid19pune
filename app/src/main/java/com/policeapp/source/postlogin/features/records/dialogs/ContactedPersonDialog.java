package com.policeapp.source.postlogin.features.records.dialogs;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.policeapp.R;
import com.policeapp.source.postlogin.features.records.adapter.ContactPersonListAdapter;
import com.policeapp.source.postlogin.features.records.bean.ContactedPersonBean;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactedPersonDialog extends BottomSheetDialogFragment {
    private ArrayList<ContactedPersonBean> list;
    private ContactPersonListAdapter adapter;
    private RecyclerView recycler;
    private TextView emptyMessage,mTitle;
    private String name;
    public ContactedPersonDialog(ArrayList<ContactedPersonBean> list,String name) {
        // Required empty public constructor
        this.list = list;
        this.name = name;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contacted_person_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        recycler = view.findViewById(R.id.recycler);
        mTitle = view.findViewById(R.id.personName);
        emptyMessage = view.findViewById(R.id.emptyMessage);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ContactPersonListAdapter(list);
        recycler.setAdapter(adapter);
        if (list.size()>0){
            emptyMessage.setVisibility(View.GONE);
        }
        mTitle.setText(name);
    }
}

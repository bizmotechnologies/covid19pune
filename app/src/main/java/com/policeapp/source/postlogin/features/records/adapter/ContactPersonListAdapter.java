package com.policeapp.source.postlogin.features.records.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.policeapp.R;
import com.policeapp.source.postlogin.features.records.bean.ContactedPersonBean;

import java.util.ArrayList;

public class ContactPersonListAdapter extends RecyclerView.Adapter<ContactPersonListAdapter.Holder> {
    private ArrayList<ContactedPersonBean> list;

    public ContactPersonListAdapter(ArrayList<ContactedPersonBean> list) {
        this.list = list;
    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_person_item_view,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.mName.setText(list.get(position).getPatientName());
        holder.mContactNo.setText(list.get(position).getEmailId());
        holder.mAddress.setText(list.get(position).getAddress());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        private TextView mName, mAddress, mContactNo;

        public Holder(@NonNull View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.personName);
            mAddress = itemView.findViewById(R.id.person_address);
            mContactNo = itemView.findViewById(R.id.personMobile);
        }
    }
}

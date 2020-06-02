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
        holder.mName.setText(list.get(position).getName());
        holder.mAddress.setText(list.get(position).getAddress());
        if (list.get(position).getNumber()==null || list.get(position).getNumber().isEmpty() || list.get(position).getNumber().equals("0"))
        {
            holder.mContactNo.setVisibility(View.GONE);
        }
        else
        {
            holder.mContactNo.setText(list.get(position).getNumber());
            holder.mContactNo.setVisibility(View.VISIBLE);
        }
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

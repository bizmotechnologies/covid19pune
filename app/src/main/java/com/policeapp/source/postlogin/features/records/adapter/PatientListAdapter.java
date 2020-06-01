package com.policeapp.source.postlogin.features.records.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.policeapp.R;
import com.policeapp.source.postlogin.features.records.bean.PatientByStationBean;
import com.policeapp.source.postlogin.features.records.interfaces.PatientInterface;

import java.util.ArrayList;

public class PatientListAdapter extends RecyclerView.Adapter<PatientListAdapter.Holder> {
    private ArrayList<PatientByStationBean> list;
    private PatientInterface listener;

    public PatientListAdapter(ArrayList<PatientByStationBean> list, PatientInterface listener) {
        this.list = list;
        this.listener = listener;
    }

    public void setList(ArrayList<PatientByStationBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_list_item_view,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.mParent.setTag(position);
        holder.mName.setText(list.get(position).getPatientName());
        holder.mContactNo.setText(list.get(position).getStationName());
        holder.mAddress.setText(list.get(position).getAddress() +", "+list.get(position).getCity()+", "+list.get(position).getPincode());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        private TextView mName, mAddress, mContactNo;
        private LinearLayout mParent;

        public Holder(@NonNull View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.personName);
            mAddress = itemView.findViewById(R.id.person_address);
            mContactNo = itemView.findViewById(R.id.personMobile);
            mParent = itemView.findViewById(R.id.parent);

            mParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) v.getTag();
                    if (listener!=null)
                        listener.onClickPatient(list.get(position).getPatient_id(),list.get(position).getPatientName());
                }
            });
        }
    }
}

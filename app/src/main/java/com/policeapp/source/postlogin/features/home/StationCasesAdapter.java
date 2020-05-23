package com.policeapp.source.postlogin.features.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.policeapp.R;
import com.policeapp.framework.Widgets.AppRegularTextView;
import com.policeapp.source.postlogin.features.locate_patient.bean.StationRecordBean;

import java.util.ArrayList;

public class StationCasesAdapter extends RecyclerView.Adapter<StationCasesAdapter.Holder> {
    private ArrayList<StationRecordBean> recordList;

    public StationCasesAdapter(ArrayList<StationRecordBean> recordList) {
        this.recordList = recordList;
    }

    public void setRecordList(ArrayList<StationRecordBean> recordList) {
        this.recordList = recordList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_record_list_item_view,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.mCasesCount.setText(recordList.get(position).getPatientCount());
        holder.mStationName.setText(recordList.get(position).getStationName());
    }

    @Override
    public int getItemCount() {
        return recordList.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        private AppRegularTextView mStationName, mCasesCount;
        public Holder(@NonNull View itemView) {
            super(itemView);
            mStationName = itemView.findViewById(R.id.txt_station_name);
            mCasesCount = itemView.findViewById(R.id.txt_cases_count);
        }
    }
}

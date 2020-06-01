package com.policeapp.source.postlogin.features.records.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.policeapp.R;
import com.policeapp.framework.Widgets.AppRegularTextView;
import com.policeapp.source.postlogin.features.locate_patient.bean.StationRecordBean;
import com.policeapp.source.postlogin.features.records.interfaces.RecordInterface;

import java.util.ArrayList;

public class RecordsAdapter extends RecyclerView.Adapter<RecordsAdapter.Holder> {
    private ArrayList<StationRecordBean> recordList;
    private RecordInterface listener;

    public RecordsAdapter(ArrayList<StationRecordBean> recordList, RecordInterface listener) {
        this.recordList = recordList;
        this.listener = listener;
    }

    public void setRecordList(ArrayList<StationRecordBean> recordList) {
        this.recordList = recordList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.records_list_item_view,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        String totalRecord = "No cases found";
        if (!recordList.get(position).getPatientCount().equals("0"))
            totalRecord = "Total cases "+recordList.get(position).getPatientCount();
        holder.mCasesCount.setText(totalRecord);
        holder.mStationName.setText(recordList.get(position).getStationName());
        holder.mMoreDetails.setTag(position);
    }

    @Override
    public int getItemCount() {
        return recordList.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        private AppRegularTextView mStationName, mCasesCount;
        private LinearLayout mMoreDetails;
        public Holder(@NonNull View itemView) {
            super(itemView);
            mStationName = itemView.findViewById(R.id.txt_station_name);
            mCasesCount = itemView.findViewById(R.id.txt_cases_count);
            mMoreDetails = itemView.findViewById(R.id.more);

            mMoreDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) v.getTag();
                    if (listener!=null)
                        listener.onItemClick(recordList.get(position));
                }
            });
        }
    }
}


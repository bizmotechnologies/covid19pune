package com.policeapp.source.postlogin.features.locate_patient.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.policeapp.R;
import com.policeapp.source.postlogin.features.locate_patient.bean.ContactedPersonBean;
import com.policeapp.source.postlogin.features.locate_patient.interfaces.ContactPersonListener;

import java.util.ArrayList;

public class ContactedPersonAdapter extends RecyclerView.Adapter<ContactedPersonAdapter.Holder> {
    private ContactPersonListener listener;
    private ArrayList<ContactedPersonBean> list;
    private boolean isEditable;
    public ContactedPersonAdapter(ContactPersonListener listener, ArrayList<ContactedPersonBean> list, boolean flag) {
        this.listener = listener;
        this.list = list;
        this.isEditable = flag;
    }

    public ArrayList<ContactedPersonBean> getList() {
        return list;
    }

    public void setList(ArrayList<ContactedPersonBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void updateItem(ContactedPersonBean bean, int position) {
        this.list.set(position,bean);
        notifyDataSetChanged();
    }

    public void addItem(ContactedPersonBean bean) {
        this.list.add(0,bean);
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        this.list.remove(position);
        notifyItemRemoved(position);
        notifyItemChanged(position,this.list.size());
    }

    public ContactedPersonBean getItem(int position) {
        return this.list.get(position);
    }



    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_person_list_item_view,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.mName.setText(getItem(position).getName());
        holder.mAddress.setText(getItem(position).getAddress());
        holder.mContact.setText(getItem(position).getNumber());
        holder.mDelete.setTag(position);
        holder.mEdit.setTag(position);

        if (this.isEditable)
            holder.mEdit.setVisibility(View.VISIBLE);
        else
            holder.mEdit.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        private TextView mName, mAddress, mContact;
        private ImageView mDelete, mEdit;
        public Holder(@NonNull View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.name);
            mAddress = itemView.findViewById(R.id.address);
            mContact = itemView.findViewById(R.id.number);
            mDelete = itemView.findViewById(R.id.btn_delete);
            mEdit = itemView.findViewById(R.id.btn_edit);

            mEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) v.getTag();
                    if (listener!=null){
                        listener.onEdit(position,getItem(position));
                    }
                }
            });

            mDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) v.getTag();
                    if (listener!=null){
                        removeItem(position);
                    }
                }
            });
        }
    }
}

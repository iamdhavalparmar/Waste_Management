package com.example.wastemanagement.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wastemanagement.Models.StaffData;
import com.example.wastemanagement.R;

import java.util.List;

public class ManageStaffAdapter extends RecyclerView.Adapter<ManageStaffAdapter.ViewHolder> {
    List itemList;
    Context context;


    public ManageStaffAdapter(List itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.manage_bins_row, parent, false);
        return new ManageStaffAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StaffData  sd = (StaffData) itemList.get(position);
        holder.name.setText(sd.name);
        holder.phone.setText(sd.phone);
    }


    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,phone;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.binid);
            phone = itemView.findViewById(R.id.binname);
        }
    }
}

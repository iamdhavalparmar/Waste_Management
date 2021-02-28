package com.example.wastemanagement.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wastemanagement.Models.BinsData;
import com.example.wastemanagement.R;

import java.util.List;

public class ManageBinsAdapter extends RecyclerView.Adapter<ManageBinsAdapter.ViewHolder>{
    List itemList;
    Context context;


    public ManageBinsAdapter(List itemList, Context context) {
        this.itemList = itemList;
        this.context = context;

    }

    public interface MyitemOnClickLister{
        void onItemClick(BinsData item);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.manage_bins_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BinsData binsData1 = (BinsData) itemList.get(position);
        holder.imageView.setImageResource(R.drawable.ic_black_bins);
        holder.binid.setText(binsData1.getBinId());
        holder.binname.setText(binsData1.getBinAddress());

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView binid,binname;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.bin);
            binid = itemView.findViewById(R.id.binid);
            binname = itemView.findViewById(R.id.binname);
        }
    }
}

package com.example.wastemanagement.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wastemanagement.Dashboard;
import com.example.wastemanagement.DashboardData;
import com.example.wastemanagement.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.ViewHolder> {
    List itemlist;
    Context context;

    public DashboardAdapter(List itemlist, Context context) {
        this.itemlist = itemlist;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dashboard_raw, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DashboardData dashboardData = (DashboardData) itemlist.get(position);
        holder.dashboard_text.setText(dashboardData.getText());
        holder.dashboard_image.setImageResource(dashboardData.getImage());
    }


    @Override
    public int getItemCount() {
        return itemlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView dashboard_image;
        TextView dashboard_text;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dashboard_image = (ImageView)itemView.findViewById(R.id.dashboard_image);
            dashboard_text = itemView.findViewById(R.id.dashboard_text);
        }
    }
}

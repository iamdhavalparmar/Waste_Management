package com.example.wastemanagement.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wastemanagement.Admin.ManageStaff;
import com.example.wastemanagement.Models.StaffData;
import com.example.wastemanagement.R;

import java.util.List;

public class ManageStaffAdapter extends RecyclerView.Adapter<ManageStaffAdapter.ViewHolder> {
    List itemList;
    Context context;
    MyStaffListener listener;
    AlertDialog.Builder builder;
    public ManageStaffAdapter(List itemList, Context context, MyStaffListener listener) {
        this.itemList = itemList;
        this.context = context;
        this.listener = listener;
    }

    public interface MyStaffListener{
        void onItemClick(StaffData staffData);
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
        holder.bin.setImageResource(R.drawable.ic_my_profile);
        holder.cardstaffitemview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialog(position,sd);


            }
        });


    }
    void showDialog(int position, StaffData sd){

        builder = new AlertDialog.Builder(context,R.style.CustomDialog);
        builder.setTitle("Are You Sure To Delete ");
        builder.setMessage("");


        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                itemList.remove(position);
                listener.onItemClick(sd);
                notifyDataSetChanged();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.show();

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,phone;
        CardView cardstaffitemview;
        ImageView bin;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.binid);
            phone = itemView.findViewById(R.id.binname);
            bin = itemView.findViewById(R.id.bin);
            cardstaffitemview = itemView.findViewById(R.id.cardstaffitemview);
        }
    }
}

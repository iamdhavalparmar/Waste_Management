package com.example.wastemanagement.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wastemanagement.Models.BinsData;
import com.example.wastemanagement.Models.UserListData;
import com.example.wastemanagement.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UsersListAdapter extends RecyclerView.Adapter<UsersListAdapter.ViewHolder> {
    List itemlist;
    Context context;

    public UsersListAdapter(List itemlist, Context context) {
        this.itemlist = itemlist;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.users_raw, parent, false);
        return new UsersListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserListData userListData = (UserListData) itemlist.get(position);
        holder.imageView.setImageResource(R.drawable.ic_manage_profile);
        holder.name.setText(userListData.getName());
        holder.email.setText(userListData.getEmail());
    }

    @Override
    public int getItemCount() {
        return itemlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name,email;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView  = itemView.findViewById(R.id.user_image);
            name = itemView.findViewById(R.id.user_name);
            email = itemView.findViewById(R.id.user_email);

        }
    }
}

package com.example.wastemanagement.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wastemanagement.Models.FeedbackComplainData;
import com.example.wastemanagement.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FeedbackComplainAdapter extends RecyclerView.Adapter<FeedbackComplainAdapter.ViewHolder> {
    List itemlist;
    Context context;

    public FeedbackComplainAdapter(List itemlist, Context context) {
        this.itemlist = itemlist;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.feedback_complain_raw, parent, false);
        return new FeedbackComplainAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FeedbackComplainData fc = (FeedbackComplainData) itemlist.get(position) ;
        holder.textView.setText(fc.getText());
    }

    @Override
    public int getItemCount() {
        return itemlist.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.feedback_complain_text);
        }
    }
}

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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class FeedbackComplainAdapter extends RecyclerView.Adapter<FeedbackComplainAdapter.ViewHolder> {
    List itemlist;
    Context context;
    ComplaintListener listener;
    public FeedbackComplainAdapter(List itemlist, Context context, ComplaintListener listener) {
        this.itemlist = itemlist;
        this.listener = listener;
        this.context = context;
    }
    public interface ComplaintListener{
        void OnItemClick(FeedbackComplainData complainData);
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
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnItemClick(fc);
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemlist.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.complaint_cardview);
            textView = itemView.findViewById(R.id.feedback_complain_text_message);
        }
    }
}

package com.example.cardiacrecorder;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    private List<Measurement> measurementList;
    public MyAdapter(List<Measurement> measurementList) {
        this.measurementList = measurementList;
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    private OnItemClickListener itemClickListener;

    public interface OnItemClickListener {
        void onItemDeleteClick(int position);
        void onItemEditClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        itemClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Measurement measurement = measurementList.get(position);

        // Bind measurement data to the ViewHolder's views
        holder.tvSystolic.setText(String.valueOf(measurement.getSystolic()));
        holder.tvDiastolic.setText(String.valueOf(measurement.getDiastolic()));
        holder.tvHeartRate.setText(String.valueOf(measurement.getHeartRate()));
        holder.tvDate.setText(measurement.getDate());
        holder.tvTime.setText(measurement.getTime());
        holder.tvComment.setText(measurement.getComment());


        if(measurement.getSystolic()>=90 && measurement.getSystolic()<=140){
            if(measurement.getDiastolic()>=60 && measurement.getDiastolic()<=90){
                holder.ivStatus.setImageResource(R.drawable.ok);
            }
            else{
                holder.ivStatus.setImageResource(R.drawable.error);

            }

        }

        else{
            holder.ivStatus.setImageResource(R.drawable.error);

        }



        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onItemDeleteClick(position);
                }
            }
        });

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onItemEditClick(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return measurementList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvSystolic, tvDiastolic, tvHeartRate, tvDate, tvTime, tvComment,txtStatus;
        Button btnDelete,btnEdit;
        ImageView ivStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSystolic = itemView.findViewById(R.id.tvSystolic);
            tvDiastolic = itemView.findViewById(R.id.tvDiastolic);
            tvHeartRate = itemView.findViewById(R.id.tvHeartRate);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvComment = itemView.findViewById(R.id.tvComment);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            ivStatus = itemView.findViewById(R.id.ivStatus);
        }
    }
}
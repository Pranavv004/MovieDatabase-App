package com.example.movie;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.myViewHolder> {

    Context context;
    ArrayList<DataClass> arrayList;
    int layoutid;

    private onItemClickListener onItemClickListener;


    public Adapter(Context context, ArrayList<DataClass> arrayList,int layoutid) {
        this.context = context;
        this.arrayList = arrayList;
        this.layoutid=layoutid;
    }

    public  interface onItemClickListener{
        public void onItemClick(int position);
    }
    public void setOnItemClickListener(Adapter.onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }









    @NonNull
    @Override
    public Adapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(layoutid,null,true);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.myViewHolder holder, int position) {
        Log.d("url3", arrayList.get(position).getUrl());
        if (arrayList.get(position).getUrl() != null) {
            Log.d("url3", arrayList.get(position).getUrl());
            Glide.with(context)
                    .load(arrayList.get(position).getUrl())
                    .centerCrop()
                    .into(holder.imageView);
        }

        // Set click listener for the imageView
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {

        return arrayList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.poster);

        }
    }
}

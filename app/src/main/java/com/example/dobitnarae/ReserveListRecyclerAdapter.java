package com.example.dobitnarae;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ReserveListRecyclerAdapter extends RecyclerView.Adapter<ReserveListRecyclerAdapter.ViewHolder> {
    Context context;
    ArrayList<Reserve> reserves;

    public ReserveListRecyclerAdapter(Context context, ArrayList<Reserve> reserves) {
        this.context = context;
        this.reserves = reserves;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.component_store_list_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Reserve item = reserves.get(position);
        // TODO  서버에서 이미지 받아야함
        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.sejong);
        holder.image.setBackground(drawable);

        holder.name.setText("세종대학교");
        holder.time.setText(item.getRentalDate());
        holder.storeView.setId(item.getId());
        holder.storeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "" + holder.storeView.getId(), Toast.LENGTH_SHORT);
            }
        });

        Drawable successLayoutDrawable;
        int successStatus = 1;

        if(successStatus == 0){
            holder.successText.setText("승인 대기");
            holder.successText.setTextColor(Color.parseColor("#8f8f8f"));
            successLayoutDrawable = context.getResources().getDrawable(R.drawable.border_all_layout_item_gray);
        }
        else if(successStatus == 1){
            holder.successText.setText("대여 승인");
            holder.successText.setTextColor(Color.parseColor("#339738"));
            successLayoutDrawable = context.getResources().getDrawable(R.drawable.border_all_layout_item_green);
        }
        else {
            holder.successText.setText("거절");
            holder.successText.setTextColor(Color.parseColor("#f94c4c"));
            successLayoutDrawable = context.getResources().getDrawable(R.drawable.border_all_layout_item_red);
        }
        holder.reserveSccessBorder.setBackground(successLayoutDrawable);
    }

    @Override
    public int getItemCount() {
        return this.reserves.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name, time, successText;
        LinearLayout storeView, reserveSccessBorder;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.reserve_list_img);
            name = (TextView) itemView.findViewById(R.id.reserve_name);
            time = (TextView) itemView.findViewById(R.id.reserve_time);
            storeView = (LinearLayout) itemView.findViewById(R.id.reserve_list_item);
            successText = (TextView) itemView.findViewById(R.id.reserve_success_text);
            reserveSccessBorder = (LinearLayout)itemView.findViewById(R.id.reserve_success_layout);
        }
    }
}
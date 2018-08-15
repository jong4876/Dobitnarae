package com.example.dobitnarae;

import android.content.Context;
import android.content.Intent;
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

import java.util.ArrayList;
import java.util.List;

public class ClothesCategoryListRecyclerAdapter extends RecyclerView.Adapter<ClothesCategoryListRecyclerAdapter.ViewHolder> {
    private Context context;
    private List<Clothes> originItems;
    private ClothesListRecyclerAdapter mAdapter;

    public ClothesCategoryListRecyclerAdapter(Context context, List<Clothes> originItems, ClothesListRecyclerAdapter mAdapter) {
        this.context = context;
        this.originItems = originItems;
        this.mAdapter = mAdapter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.component_clothes_category, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.image.setImageResource(R.drawable.tshirt);
        holder.text.setText(Constant.category[position]);
        holder.layout.setTag(position);
        holder.layout.setOnClickListener(new CategoryOnclickListener(holder, mAdapter, originItems));
    }

    @Override
    public int getItemCount() {
        return Constant.category_cnt;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView text;
        LinearLayout layout;

        public ViewHolder(View itemView) {
            super(itemView);
            layout = (LinearLayout) itemView.findViewById(R.id.clothes_category_layout);
            image = (ImageView) itemView.findViewById(R.id.clothes_category_img);
            text = (TextView) itemView.findViewById(R.id.clothes_category_txt);
        }
    }
}
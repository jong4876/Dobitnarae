package com.example.dobitnarae;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ClothesListRecyclerAdapter extends RecyclerView.Adapter<ClothesListRecyclerAdapter.ViewHolder> {
    Context context;
    ArrayList<Store> stores;

    public ClothesListRecyclerAdapter(Context context, ArrayList<Store> stores) {
        this.context = context;
        this.stores = stores;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview, null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // TODO 뷰 형태 바꿔야함
        final Store item = stores.get(position);
        // TODO  서버에서 이미지 받아야함
        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.sejong);
        holder.image.setBackground(drawable);
        holder.name.setText(item.getName());
        holder.price.setText("" + item.getAddress());
        holder.cardview.setId(item.getId());
        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, StoreActivity.class);
                intent.putExtra("store", item);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.stores.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name, price;
        CardView cardview;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            name = (TextView) itemView.findViewById(R.id.clothes_name);
            price = (TextView) itemView.findViewById(R.id.clothes_price);
            cardview = (CardView) itemView.findViewById(R.id.cardview);
        }
    }

    public void setClothes(ArrayList<Store> stores) {
        this.stores = stores;
        this.notifyDataSetChanged();
    }
}
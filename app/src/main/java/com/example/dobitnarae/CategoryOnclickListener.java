package com.example.dobitnarae;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class CategoryOnclickListener implements View.OnClickListener{
    private ClothesCategoryListRecyclerAdapter.ViewHolder holder;
    private List<Clothes> originItems;
    private ClothesListRecyclerAdapter mAdapter;

    public CategoryOnclickListener(ClothesCategoryListRecyclerAdapter.ViewHolder holder,
                                   ClothesListRecyclerAdapter mAdapter, List<Clothes> clothes){
        this.holder = holder;
        this.mAdapter = mAdapter;
        this.originItems = clothes;
    }

    @Override
    public void onClick(View v) {
        List<Clothes> clothes = getClothesList((int)v.getTag());
        mAdapter.setClothes(clothes);
        holder.text.setTextColor(v.getResources().getColor(R.color.appMainColor));
        holder.layout.setSelected(true);
    }

    public List<Clothes> getClothesList(int category){
        List<Clothes> tmp = new ArrayList<>();
        // 분류: 전체
        if(category == 0)
            return originItems;

        for(int i=0; i<originItems.size(); i++){
            Clothes item = originItems.get(i);
            if(item.getCategory() == category)
                tmp.add(item);
        }
        return tmp;
    }
}

package com.example.dobitnarae;

import android.annotation.SuppressLint;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class StoreClothes extends Fragment {
    // TODO 이미지를 받아오지 말고 다른 방법 찾기
    ImageView storeImg;
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public StoreClothes(ImageView storeImg) {
        this.storeImg = storeImg;
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static StoreClothes newInstance(int sectionNumber, ImageView storeImg) {
        StoreClothes fragment = new StoreClothes(storeImg);
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_store, container, false);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_clothes);
        //LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        LinearLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(!recyclerView.canScrollVertically(-1)) {
                    Log.e("상단","ㅁㄴㅇ");
                }
                if(!recyclerView.canScrollVertically(1)) {
                    Log.e("하단","ㅁㄴㅇ");
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    Log.e("UP","" + dy);
                    storeImg.setY(storeImg.getY() - dy);
                } else {
                    Log.e("down", "" + dy);
                    storeImg.setY(storeImg.getY() - dy);
                }
                // TODO 이미지 크기 변경 대신 레이아웃 크기 변경으로 수정하기
                /*
                storeImg.getLayoutParams().height -= dy;
                if (dy > 0) {
                    Log.e("UP","" + dy);
                    if(storeImg.getLayoutParams().height < 0)
                        storeImg.getLayoutParams().height = 0;
                } else {
                    Log.e("down", "" + dy);
                    if(storeImg.getLayoutParams().height > storeImg.getMaxHeight())
                        storeImg.getLayoutParams().height = storeImg.getMaxHeight();
                }
                storeImg.requestLayout();
                */
            }
        });

        // 옷추가
        final int ITEM_SIZE = 9;
        List<Clothes> items = new ArrayList<>();
        Clothes[] item = new Clothes[ITEM_SIZE];
        item[0] = new Clothes(R.drawable.gobchang, "불곱창1", 20000);
        item[1] = new Clothes(R.drawable.gobchang, "불곱창2", 20000);
        item[2] = new Clothes(R.drawable.gobchang, "불곱창3", 20000);
        item[3] = new Clothes(R.drawable.gobchang, "불곱창4", 20000);
        item[4] = new Clothes(R.drawable.gobchang, "불곱창5", 20000);
        item[5] = new Clothes(R.drawable.gobchang, "불곱창1", 20000);
        item[6] = new Clothes(R.drawable.gobchang, "불곱창2", 20000);
        item[7] = new Clothes(R.drawable.gobchang, "불곱창3", 20000);
        item[8] = new Clothes(R.drawable.gobchang, "불곱창4", 20000);

        for (int i = 0; i < ITEM_SIZE; i++) {
            items.add(item[i]);
        }

        recyclerView.setAdapter(new RecyclerAdapter(getContext(), items, R.layout.fragment_store_clothes));

        return rootView;
    }

}

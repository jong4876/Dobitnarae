package com.example.dobitnarae;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SuppressLint("ValidFragment")
public class StoreClothesFragment extends Fragment {
    List<Clothes> originItems, items;
    ClothesListRecyclerAdapter mAdapter;
    ClothesCategoryListRecyclerAdapter cAdapter;
    Store store;
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public StoreClothesFragment(List<Clothes> items, Store store) {
        this.originItems = items;
        this.items = items;
        this.store = store;
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static StoreClothesFragment newInstance(int sectionNumber, List<Clothes> items, Store store) {
        StoreClothesFragment fragment = new StoreClothesFragment(items, store);
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_store_clothes_list, container, false);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_clothes);
        LinearLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        //옷추가
        mAdapter = new ClothesListRecyclerAdapter(getContext(), items, store, R.layout.fragment_store_clothes_list);
        recyclerView.setAdapter(mAdapter);

        // 옷 종류 선택 메뉴
        RecyclerView recyclerViewCategory = (RecyclerView) rootView.findViewById(R.id.clothes_category);
        LinearLayoutManager layoutManagerCategory = new LinearLayoutManager(getContext());

        layoutManagerCategory.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewCategory.setLayoutManager(layoutManagerCategory);

//        LinearLayout.OnClickListener onClickListener = new CategoryOnclickListener(){
//            @Override
//            public void onClick(View v) {
//                items = getClothesList((int)v.getTag());
//                mAdapter.setClothes(items);
//            }
//        };

        cAdapter = new ClothesCategoryListRecyclerAdapter(getContext(), originItems, mAdapter);
        recyclerViewCategory.setAdapter(cAdapter);

        return rootView;
    }


}
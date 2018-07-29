package com.example.dobitnarae;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class StoreClothes extends Fragment {

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public StoreClothes() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static StoreClothes newInstance(int sectionNumber) {
        StoreClothes fragment = new StoreClothes();
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

        // 옷추가
        final int ITEM_SIZE = 5;
        List<Clothes> items = new ArrayList<>();
        Clothes[] item = new Clothes[ITEM_SIZE];
        item[0] = new Clothes(R.drawable.gobchang, "불곱창1", 20000);
        item[1] = new Clothes(R.drawable.gobchang, "불곱창2", 20000);
        item[2] = new Clothes(R.drawable.gobchang, "불곱창3", 20000);
        item[3] = new Clothes(R.drawable.gobchang, "불곱창4", 20000);
        item[4] = new Clothes(R.drawable.gobchang, "불곱창5", 20000);

        for (int i = 0; i < ITEM_SIZE; i++) {
            items.add(item[i]);
        }

        recyclerView.setAdapter(new RecyclerAdapter(getContext(), items, R.layout.fragment_store_clothes));

        return rootView;
    }

}

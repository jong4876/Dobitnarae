package com.example.dobitnarae;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class StoreClothesFragment extends Fragment {
    List<Clothes> originItems, items;
    RecyclerAdapter mAdapter;
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public StoreClothesFragment(List<Clothes> items) {
        this.originItems = items;
        this.items = items;
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static StoreClothesFragment newInstance(int sectionNumber, List<Clothes> items) {
        StoreClothesFragment fragment = new StoreClothesFragment(items);
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_store, container, false);

        final RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_clothes);
        LinearLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        //옷추가
        mAdapter = new RecyclerAdapter(getContext(), items, R.layout.fragment_store);
        recyclerView.setAdapter(mAdapter);

        // 옷 종류 선택 메뉴
        LinearLayout clothesCategory = (LinearLayout) rootView.findViewById(R.id.clothes_category);

        final String category[] = {"전체", "상의", "하의", "모자", "신발", "장신구"};
        ImageView[] imageViews = new ImageView[Constant.category_cnt];

        for(int i=0; i<Constant.category_cnt; i++){
            imageViews[i] = new ImageView(getContext());
            imageViews[i].setImageResource(R.drawable.ic_clothes_list);
            imageViews[i].setId(i);
            imageViews[i].setLayoutParams(new LinearLayout.LayoutParams(100, 100));
            imageViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = v.getId();
                    items = getClothesList(id);
                    mAdapter.setClothes(items);
                    Toast.makeText(getContext(), category[v.getId() % Constant.category_cnt], Toast.LENGTH_SHORT).show();
                }
            });
            clothesCategory.addView(imageViews[i]);
        }
        return rootView;
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

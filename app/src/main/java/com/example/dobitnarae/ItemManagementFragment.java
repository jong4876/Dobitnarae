package com.example.dobitnarae;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class ItemManagementFragment extends Fragment {
    ArrayList<Clothes> originItems, items;
    ItemListRecyclerAdapter mAdapter;
    Store store;

    private ImageButton btn_edit;

    public ItemManagementFragment(ArrayList<Clothes> items, Store store) {
        this.originItems = items;
        this.items = items;
        this.store = store;
    }

    private static final String ARG_SECTION_NUMBER = "section_number";
    public static ItemManagementFragment newInstance(int sectionNumber, ArrayList<Clothes> items, Store store) {
        ItemManagementFragment fragment = new ItemManagementFragment(items, store);
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_store, container, false);

        final RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_clothes);
        LinearLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        //옷추가
        mAdapter = new ItemListRecyclerAdapter(getContext(), items, store, R.layout.fragment_store);
        recyclerView.setAdapter(mAdapter);

        // 옷 종류 선택 메뉴
        LinearLayout clothesCategory = (LinearLayout) rootView.findViewById(R.id.clothes_category);

        final String category[] = {"전체", "상의", "하의", "모자", "신발", "장신구"};
        ImageView[] imageViews = new ImageView[Constant.category_cnt];

        for(int i = 0; i< Constant.category_cnt; i++) {
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
                }
            });
            clothesCategory.addView(imageViews[i]);
        }

        // 부모액티비티 툴바 요소인 이미지 버튼에 접근
        btn_edit = ((AdminActivity)getActivity()).getImageButton();
        btn_edit.setVisibility(View.VISIBLE);

        return rootView;
    }

    public ArrayList<Clothes> getClothesList(int category){
        ArrayList<Clothes> tmp = new ArrayList<>();
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

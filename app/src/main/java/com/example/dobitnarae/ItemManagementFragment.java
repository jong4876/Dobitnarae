package com.example.dobitnarae;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class ItemManagementFragment extends Fragment {
    ArrayList<Clothes> originItems, originItems2, items;
    ItemListRecyclerAdapter mAdapter;
    Store store;

    public ArrayList<Clothes> deleteList;

    private ArrayAdapter<String> spinnerAdapter;
    private ArrayList<String> dataList;

    public ItemManagementFragment(Store store) {
        this.originItems = JSONTask.getInstance().getClothesAll("jong4876");
        this.originItems2 = Clothes.getAllInstanceList();
        originItems2.clear();
        this.items = new ArrayList<Clothes>();
        for (Clothes item:originItems) {
            originItems2.add(item);
            items.add(item);
        }
        this.store = store;

        deleteList = new ArrayList<Clothes>();
    }

    private static final String ARG_SECTION_NUMBER = "section_number";
    public static ItemManagementFragment newInstance(int sectionNumber, Store store) {
        ItemManagementFragment fragment = new ItemManagementFragment(store);
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
        mAdapter = new ItemListRecyclerAdapter(getActivity(), items, store, R.layout.fragment_store){
            @Override
            public void onBindViewHolder(final ViewHolder holder, final int position) {
                super.onBindViewHolder(holder, position);

                holder.cardview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(holder.clicked == 0) {
                            holder.clicked = 1;
                            if(!deleteList.contains(items.get(position)))
                                deleteList.add(items.get(position));
                            holder.layout_cardview.setBackgroundResource(R.drawable.cardview_border);
                        }
                        else {
                            holder.clicked = 0;
                            if(deleteList.contains(items.get(position)))
                                deleteList.remove(items.get(position));
                            holder.layout_cardview.setBackgroundResource(R.drawable.cardview_bordernone);
                        }
                    }
                });
            }
        };
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

        // 스피너 드롭다운
        dataList = new ArrayList<String>();
        dataList.add("메       뉴");
        dataList.add("추       가");
        dataList.add("삭       제");
        dataList.add("새로고침");

        spinnerAdapter = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, dataList){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View v =  super.getView(position, convertView, parent);

                Typeface externalFont = Typeface.createFromAsset(getActivity().getAssets(), "font/NanumSquareR.ttf");
                ((TextView) v).setTypeface(externalFont);
                ((TextView) v).setTextSize(18);

                return v;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View v =  super.getDropDownView(position, convertView, parent);

                Typeface externalFont = Typeface.createFromAsset(getActivity().getAssets(), "font/NanumSquareR.ttf");
                ((TextView) v).setTypeface(externalFont);
                v.setBackgroundColor(Color.WHITE);
                ((TextView) v).setTextColor(Color.BLACK);
                ((TextView) v).setGravity(Gravity.CENTER);

                return v;
            }
        };
        spinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        final Spinner spinner = ((AdminActivity)getActivity()).getSpinner();
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spinner.getItemIdAtPosition(position) == 1){
                    // 추가
                    Intent intent = new Intent(getContext(), ItemAddActivity.class);
                    startActivity(intent);

                } else if(spinner.getItemIdAtPosition(position) == 2){
                    // 삭제
                    if(deleteList.size()!=0) {
                        originItems2 = Clothes.getAllInstanceList();
                        for (Clothes tmp : deleteList
                                ) {
                            originItems2.remove(tmp);
                            JSONTask.getInstance().deleteCloth(tmp.getCloth_id());
                        }
                        items = originItems;
                        mAdapter.notifyDataSetChanged();
                        Toast.makeText(getActivity(), deleteList.size() + "개 항목이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                        deleteList.clear();
                    } else {
                        Toast.makeText(getActivity(), "삭제할 항목을 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                } else if(spinner.getItemIdAtPosition(position) == 3) {
                    // 새로고침
                    originItems2 = Clothes.getAllInstanceList();
                    items = getClothesList(0);
                    mAdapter.setClothes(items);
                    mAdapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), "새로고침 되었습니다.", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return rootView;
    }

    public ArrayList<Clothes> getClothesList(int category){
        ArrayList<Clothes> tmp = new ArrayList<>();
        // 분류: 전체
        if(category == 0)
            return originItems2;

        for(int i=0; i<originItems2.size(); i++){
            Clothes item = originItems2.get(i);
            if(item.getCategory() == category)
                tmp.add(item);
        }
        return tmp;
    }
}

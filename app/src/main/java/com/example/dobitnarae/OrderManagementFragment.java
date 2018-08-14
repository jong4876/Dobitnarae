package com.example.dobitnarae;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SuppressLint("ValidFragment")
public class OrderManagementFragment extends Fragment {
    private ArrayList<Order> items;
    private List<Order> confirmedDatas;
    private ListView mListView = null;
    private ListViewAdapter mAdapter = null;

    private Basket basket;

    public OrderManagementFragment(ArrayList<Order> items, List<Order> items2) {
        this.items = items;
        this.confirmedDatas = items2;
        this.basket = Basket.getInstance();
    }

    // 어댑터 2개쓰고 전환시키자
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_management_order, container, false);

        mListView = (ListView) rootView.findViewById(R.id.listView);

        mAdapter = new ListViewAdapter(getActivity());
        mListView.setAdapter(mAdapter);

        // 내림차순 정렬된 순서로 데이터 삽입
        //Collections.sort(items);

        for (Order item:items) {
            mAdapter.addItem(item);
        }

        // 미승인 데이터 조회
        Button btnNotconfirmed = (Button) rootView.findViewById(R.id.btn_notconfirmed_list);
        btnNotconfirmed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 어댑터 데이터 초기화
                mAdapter.clear();
                Collections.sort(items);
                for (Order item:items) {
                    mAdapter.addItem(item);
                }

                mAdapter.dataChange();
            }
        });

        // 승인 데이터 조회
        Button btnconfirmed = (Button) rootView.findViewById(R.id.btn_confirmed_list);
        btnconfirmed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 어댑터 데이터 초기화
                mAdapter.clear();
                // 승인 / 거절 데이터 또한 내림차순으로 정렬
                Collections.sort(confirmedDatas);
                for (Order item:confirmedDatas) {
                    mAdapter.addItem(item);
                }
                mAdapter.dataChange();
            }
        });
        return rootView;
    }

    private class ViewHolder {
        public String mNo;
        public ImageView imageView;
        public TextView mBasket;
        public TextView mDate;
        public TextView mStore;
        public LinearLayout linearLayout;
    }

    private class ListViewAdapter extends BaseAdapter {
        private Context mContext = null;
        private ArrayList<OrderInfoData> mListData = new ArrayList<OrderInfoData>();

        public ListViewAdapter(Context mContext) {
            super();
            this.mContext = mContext;
        }

        @Override
        public int getCount() {
            return mListData.size();
        }

        @Override
        public Object getItem(int position) {
            return mListData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();

                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.listview_info_confirmedorder, null);

                holder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
                holder.mBasket = (TextView) convertView.findViewById(R.id.txt_basket);
                holder.mDate = (TextView) convertView.findViewById(R.id.txt_date);
                holder.mStore = (TextView) convertView.findViewById(R.id.txt_store);
                holder.linearLayout = (LinearLayout) convertView.findViewById(R.id.listView_order);

                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }

            final OrderInfoData mData = mListData.get(position);

            // 서버에서 이미지 받아야함
            Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.gobchang);

            //holder.mNo = mData.getOrderNo();
            //holder.imageView.setBackground(drawable);
            //holder.mBasket.setText(basket.getBasket().get(position).getClothes().getName() + " 외 " + basket.getBasket().get(position).getClothes().getCount()+ "벌");
            //holder.mDate.setText(basket.getRentalDate());

            holder.mNo = mData.getOrderNo();
            holder.imageView.setBackground(drawable);
            holder.mBasket.setText(mData.getOrderBasket());
            holder.mDate.setText(mData.getOrderDate());
            // 매장이름필요
            holder.mStore.setText(mData.getOrderNo());
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, SpecificOrderActivity.class);
                    intent.putExtra("order", items.get(position));
                    mContext.startActivity(intent);
                }
            });

            return convertView;
        }

        public void addItem(Order item){
            OrderInfoData addInfo = null;
            addInfo = new OrderInfoData();
            addInfo.setOrderNo(String.valueOf(item.getOrderNo()));
            // 고객 아이디가 아닌 고객 이름을 보여지게 해야함
            addInfo.setOrderName(item.getUserID());
            // 고객 주문데이터로 수정필요
            addInfo.setOrderBasket(item.getAdminID());
            addInfo.setOrderDate(item.getOrderDate());
            mListData.add(addInfo);
        }

        public void remove(int position){
            mListData.remove(position);
            dataChange();
        }


        public void clear(){
            mListData.clear();
            dataChange();
        }

        public void dataChange(){
            mAdapter.notifyDataSetChanged();
        }
    }
}

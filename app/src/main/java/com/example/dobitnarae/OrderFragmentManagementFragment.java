package com.example.dobitnarae;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

@SuppressLint("ValidFragment")
public class OrderFragmentManagementFragment extends Fragment {
    private ArrayList<Order> items;
    private ListView mListView = null;
    private ListViewAdapter mAdapter = null;

    private Basket basket;

    public OrderFragmentManagementFragment() {
        this.items = Order.getncInstanceList();
        this.basket = Basket.getInstance();
    }

    private static final String ARG_SECTION_NUMBER = "section_number";
    public static OrderFragmentManagementFragment newInstance(int sectionNumber) {
        OrderFragmentManagementFragment fragment = new OrderFragmentManagementFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_management_fragment_order, container, false);

        mListView = (ListView) rootView.findViewById(R.id.listView);

        mAdapter = new ListViewAdapter(getContext());
        mListView.setAdapter(mAdapter);

        // 내림차순 정렬된 순서로 데이터 삽입
        //Collections.sort(items);

        for (Order item:items) {
            mAdapter.addItem(item);
        }
        return rootView;
    }

    private class ViewHolder {
        public String mNo;
        public ImageView imageView;
        public TextView mBasket;
        public TextView mDate;
        public LinearLayout linearLayout;
    }

    private class ListViewAdapter extends BaseAdapter {
        private Context mContext = null;
        public ArrayList<OrderInfoData> mListData = new ArrayList<OrderInfoData>();

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
                convertView = inflater.inflate(R.layout.component_listview_order, null);

                holder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
                holder.mBasket = (TextView) convertView.findViewById(R.id.txt_basket);
                holder.mDate = (TextView) convertView.findViewById(R.id.txt_date);
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
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, OrderSpecificActivity.class);
                    intent.putExtra("order", position);
                    intent.putExtra("id", 0);
                    mContext.startActivity(intent);
                }
            });

            return convertView;
        }

        public void addItem(Order item){
            OrderInfoData addInfo = null;

            int sum = 0;
            for(int i = 0; i < basket.getBasket().size(); i++){
                sum += basket.getBasket().get(i).getCnt() ;
            }
            sum -= 1;

            addInfo = new OrderInfoData();
            addInfo.setOrderNo(String.valueOf(item.getOrderNo()));
            // 고객 아이디가 아닌 고객 이름을 보여지게 해야함
            addInfo.setOrderName(item.getUserID());
            // 고객 주문데이터로 수정필요
            if(basket.getBasket().size()!=0 && sum != 0)
                addInfo.setOrderBasket(basket.getBasket().get(0).getClothes().getName() + " 외 " + sum + "벌");
            else if(sum==0)
                addInfo.setOrderBasket(basket.getBasket().get(0).getClothes().getName() + " 1벌");
            else
                addInfo.setOrderBasket("비어있음");
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

    public void dataUpdate(){
        items = Order.getncInstanceList();
        mAdapter.clear();
        for (Order item:items)
            mAdapter.addItem(item);
        mAdapter.notifyDataSetChanged();
    }
}

package com.example.dobitnarae;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
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
    private List<Order> items;
    private List<Order> confirmedDatas;
    private ListView mListView = null;
    private ListViewAdapter mAdapter = null;

    public OrderManagementFragment(List<Order> items, List<Order> items2) {
        this.items = items;
        this.confirmedDatas = items2;
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
        Collections.sort(items);

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

                mAdapter.viewChange(false, v);
                mAdapter.layoutClear();
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
                mAdapter.viewChange(true, v);
                mAdapter.layoutClear();
                mAdapter.dataChange();
            }
        });

        return rootView;
    }

    private class ViewHolder {
        public TextView mNo;
        public TextView mName;
        public TextView mBasket;
        public TextView mDate;
        public TextView mPrice;

        public TextView confirmNo;
        public TextView confirmName;
        public TextView confirmBasket;
        public TextView confirmDate;
        public TextView confirmPrice;
    }

    private class ListViewAdapter extends BaseAdapter {
        private Context mContext = null;
        private ArrayList<OrderInfoData> mListData = new ArrayList<OrderInfoData>();

        public List<LinearLayout> linearLayout = new ArrayList<LinearLayout>();
        public List<LinearLayout> linearLayout2 = new ArrayList<LinearLayout>();

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
                convertView = inflater.inflate(R.layout.listview_info_order, null);

                holder.mNo = (TextView) convertView.findViewById(R.id.txt_order_no);
                holder.mName = (TextView) convertView.findViewById(R.id.txt_order_name);
                holder.mBasket = (TextView) convertView.findViewById(R.id.txt_order_basket);
                holder.mDate = (TextView) convertView.findViewById(R.id.txt_order_date);
                holder.mPrice = (TextView) convertView.findViewById(R.id.txt_order_price);

                holder.confirmNo = (TextView) convertView.findViewById(R.id.txt_confirm_no);
                holder.confirmName = (TextView) convertView.findViewById(R.id.txt_confirm_name);
                holder.confirmBasket = (TextView) convertView.findViewById(R.id.txt_confirm_basket);
                holder.confirmDate = (TextView) convertView.findViewById(R.id.txt_confirm_date);
                holder.confirmPrice = (TextView) convertView.findViewById(R.id.txt_confirm_price);

                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }

            OrderInfoData mData = mListData.get(position);

            holder.mNo.setText(mData.getOrderNo());
            holder.mName.setText(mData.getOrderName());
            holder.mBasket.setText(mData.getOrderBasket());
            holder.mDate.setText(mData.getOrderDate());
            holder.mPrice.setText(mData.getOrderPrice());

            holder.confirmNo.setText(mData.getOrderNo());
            holder.confirmName.setText(mData.getOrderName());
            holder.confirmBasket.setText(mData.getOrderBasket());
            holder.confirmDate.setText(mData.getOrderDate());
            holder.confirmPrice.setText(mData.getOrderPrice());


            linearLayout.add((LinearLayout) convertView.findViewById(R.id.layout_nonConfirm));
            linearLayout2.add((LinearLayout)convertView.findViewById(R.id.layout_confirm));


            // 리스트뷰 버튼 이벤트
            // 승인 버튼 클릭 시
            Button btnRegister = (Button) convertView.findViewById(R.id.btn_confirm);
            btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snackbar.make(v, items.get(position).getUserID() + "고객의 주문이 승인되었습니다.", Snackbar.LENGTH_LONG).show();
                    items.get(position).setAcceptStatus(1); // 승인
                    // 승인된 리스트에 삽입
                    confirmedDatas.add(items.get(position));
                    items.remove(position);
                    mAdapter.remove(position);
                    // 데이터 변경됨을 반영
                    mAdapter.dataChange();
                }
            });

            // 거절 버튼 클릭 시
            Button btnReject = (Button) convertView.findViewById(R.id.btn_reject);
            btnReject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snackbar.make(v, items.get(position).getUserID() + "고객의 주문이 거절되었습니다.", Snackbar.LENGTH_LONG).show();
                    items.get(position).setAcceptStatus(2); // 거절
                    confirmedDatas.add(items.get(position));
                    items.remove(position);
                    mAdapter.remove(position);
                    mAdapter.dataChange();
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
            // 고객 주문데이터 총가격 수정필요
            // 고객 주문데이터 총가격 수정필요
            if(item.getAcceptStatus()==0)
                addInfo.setOrderPrice("대기");
            else if(item.getAcceptStatus()==1)
                addInfo.setOrderPrice("승인");
            else if(item.getAcceptStatus()==2)
                addInfo.setOrderPrice("거절");
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

        public void layoutClear(){
            linearLayout.clear();
            linearLayout2.clear();
        }

        public void viewChange(boolean visible, View v){
            // 승인 데이터 보기
            if(visible){
                //Snackbar.make(v, "승인버튼 클릭", Snackbar.LENGTH_LONG).show();
                Log.d("visible1", String.valueOf(linearLayout.size()));
                Log.d("visible2", String.valueOf(linearLayout2.size()));
                for (LinearLayout item : linearLayout) {
                    item.setVisibility(View.GONE);
                }
                for (LinearLayout item : linearLayout2) {
                    item.setVisibility(View.VISIBLE);
                }
            } else { // 미승인 데이터 보기
                //Snackbar.make(v, "거절버튼 클릭", Snackbar.LENGTH_LONG).show();
                Log.d("invisible1", String.valueOf(linearLayout.size()));
                Log.d("invisible2", String.valueOf(linearLayout2.size()));
                for (LinearLayout item : linearLayout) {
                    item.setVisibility(View.VISIBLE);
                }
                for (LinearLayout item : linearLayout2) {
                    item.setVisibility(View.GONE);
                }
            }
        }
    }
}

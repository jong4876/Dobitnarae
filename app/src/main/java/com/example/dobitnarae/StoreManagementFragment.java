package com.example.dobitnarae;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class StoreManagementFragment extends Fragment {
    private ListView mListView = null;
    private ListViewAdapter mAdapter = null;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_management_store, container, false);

        mListView = (ListView) rootView.findViewById(R.id.listView);

        mAdapter = new ListViewAdapter(getActivity());
        mListView.setAdapter(mAdapter);

        mAdapter.addItem("아이디", "1231");
        mAdapter.addItem("이름", "두빛나래");
        mAdapter.addItem("위치", "1");
        mAdapter.addItem("설명", "어서오세요~ 앱만드는거 드럽게 어렵네요^^");

        return rootView;
    }

    private class ViewHolder {
        public TextView mText;

        public TextView mContext;
    }

    private class ListViewAdapter extends BaseAdapter {
        private Context mContext = null;
        private ArrayList<StoreInfoData> mListData = new ArrayList<StoreInfoData>();

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
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();

                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.listview_info_store, null);

                holder.mText = (TextView) convertView.findViewById(R.id.mText);
                holder.mContext = (TextView) convertView.findViewById(R.id.mContext);

                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }

            StoreInfoData mData = mListData.get(position);


            holder.mText.setText(mData.mText);
            holder.mContext.setText(mData.mContext);

            return convertView;
        }

        public void addItem(String mText, String mContext){
            StoreInfoData addInfo = null;
            addInfo = new StoreInfoData();
            addInfo.mText = mText;
            addInfo.mContext = mContext;

            mListData.add(addInfo);
        }

        public void remove(int position){
            mListData.remove(position);
            dataChange();
        }

        public void dataChange(){
            mAdapter.notifyDataSetChanged();
        }
    }
}

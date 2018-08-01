package com.example.dobitnarae;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

@SuppressLint("ValidFragment")
public class StoreInfoFragment extends Fragment {
    Store store;
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public StoreInfoFragment(Store store) {
        this.store = store;
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static StoreInfoFragment newInstance(int sectionNumber, Store store) {
        StoreInfoFragment fragment = new StoreInfoFragment(store);
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_store_info, container, false);

        TextView intro = (TextView)rootView.findViewById(R.id.content_intro);
        intro.setText(store.getIntroduction());
        TextView info = (TextView)rootView.findViewById(R.id.content_info);
        info.setText(store.getInformation());
        TextView sales_info = (TextView)rootView.findViewById(R.id.content_sales_info);
        sales_info.setText(store.getPhoneNumber());
        TextView owner_info = (TextView)rootView.findViewById(R.id.content_owner_info);
        owner_info.setText(store.getOwnerName());
        TextView address = (TextView)rootView.findViewById(R.id.content_address);
        address.setText(store.getAddress());

        return rootView;
    }

}

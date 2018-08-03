package com.example.dobitnarae;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ItemManagementFragment extends Fragment {
    public ItemManagementFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_management_item, container, false);

        String[] list_menu = {"상의", "하의", "신발"};

        ListView listView = (ListView) rootView.findViewById(R.id.itemList);

        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_list_item_1, list_menu);

        listView.setAdapter(listViewAdapter);

        return rootView;
    }
}

package com.example.dobitnarae;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nhn.android.maps.NMapContext;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapOverlayItem;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;

@SuppressLint("ValidFragment")
public class StoreInfoFragment extends Fragment {
    Store store;

    private NMapController mMapController;
    private NMapView mMapView;// 지도 화면 View
    private NMapContext mMapContext;
    private final String CLIENT_ID = "kq6ZsHG_bYYKmox3mPqw";// 애플리케이션 클라이언트 아이디 값

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

        TextView name = (TextView)rootView.findViewById(R.id.content_name);
        name.setText(store.getName());
        TextView intro = (TextView)rootView.findViewById(R.id.content_intro);
        intro.setText(store.getIntro());
        TextView info = (TextView)rootView.findViewById(R.id.content_info);
        info.setText(store.getInform());
        TextView sales_info = (TextView)rootView.findViewById(R.id.content_sales_info);
        sales_info.setText(store.getTel());
        TextView owner_info = (TextView)rootView.findViewById(R.id.content_owner_info);
        owner_info.setText(store.getAdmin_id());
        TextView address = (TextView)rootView.findViewById(R.id.content_address);
        address.setText(store.getAddress());

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMapContext =  new NMapContext(super.getActivity());
        mMapContext.onCreate();
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mMapView = (NMapView)getView().findViewById(R.id.mapView);
        mMapView.setClientId(CLIENT_ID);// 클라이언트 아이디 설정
        mMapContext.setupMapView(mMapView);

        mMapController = mMapView.getMapController();
        mMapController.setMapCenter(new NGeoPoint(store.getLongitude(), store.getLatitude()), 12);
    }
    @Override
    public void onStart(){
        super.onStart();
        mMapContext.onStart();
    }
    @Override
    public void onResume() {
        super.onResume();
        mMapContext.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        mMapContext.onPause();
    }
    @Override
    public void onStop() {
        mMapContext.onStop();
        super.onStop();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
    @Override
    public void onDestroy() {
        mMapContext.onDestroy();
        super.onDestroy();
    }
}

package com.example.dobitnarae;

import android.content.res.Configuration;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AdminActivity extends AppCompatActivity {
    private Store store;
    private List<Order> orderedDatas;
    private List<Order> orderedDatas2;
    private List<Basket> baskets;
    private List<Clothes> items;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        // 특정 인덴트에서 store 키값을 받아와
        // 서버로 통신 하여 `가게정보, 판매중인 옷` 데이터 받아옴
        store = new Store("세종대학교","서울특별시 광진구 군자동 능동로 209",
                "세종대학교는 대한민국 서울특별시 광진구 군자동에 위치한 사립 종합대학이다." +
                        " 세종대나 SJU의 약칭으로 불리기도 한다. 10개의 단과 대학, 1개의 교양 대학," +
                        " 1개의 독립학부, 1개의 일반대학원, 1개의 전문대학원, 5개의 특수대학원과 57개의 연구소," +
                        " 8개의 BK21사업팀으로 구성되어 있다. 학교법인 대양학원에 의해 운영된다. 현재 총장은 화학 박사 신구이다. ",
                "24시간 영업","02-3408-3114", "신구",
                37.550278,127.073114);

        // 예약정보
        int ITEM_SIZE = 8;
        orderedDatas = new ArrayList<>();
        Order[] item = new Order[ITEM_SIZE];
        for(int i=0; i<ITEM_SIZE; i++){
            item[i] = new Order(i,"kang123"+i, "jong123", 0, "2018-08-08");
            orderedDatas.add(item[i]);
        }

        // 예약정보
        int ITEM_SIZE2 = 1;
        orderedDatas2 = new ArrayList<>();
        Order[] item2 = new Order[ITEM_SIZE2];
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_example_swipe_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            // 액티비티 만들어서 케이스 문에다가 넣어주면 됩니다
            // 탭 이름은 values/string 에 들어있어요
            // 아마 클래스 타입은 무조건 PlaceholderFragment 여야 할꺼같아요

            switch(position) {
                case 0:
                    return new StoreManagementFragment(store);
                case 1:
                    return new ItemManagementFragment();
                case 2:
                    return new OrderManagementFragment(orderedDatas, orderedDatas2);
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // 탭 개수
            return 3;
        }
    }

    // 액티비티는 새로만들어지지 않고 유지
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //Toast.makeText(this, "가로방향", Toast.LENGTH_LONG).show();
        } else if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            //Toast.makeText(this, "세로방향", Toast.LENGTH_LONG).show();
        }
    }
}

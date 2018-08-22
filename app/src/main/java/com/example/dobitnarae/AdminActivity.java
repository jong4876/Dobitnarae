package com.example.dobitnarae;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import java.util.List;
import java.util.Objects;

public class AdminActivity extends AppCompatActivity {
     private Store store;
     private ArrayList<Store> storeList = new ArrayList<Store>();
     private ArrayList<Clothes> clothesList = new ArrayList<Clothes>();
     private ImageButton imageButton;
     private Spinner spinner;
     private ImageButton refreshBtn;
     private StoreManagementFragment storeManagementFragment;
    public AdminActivity() {

    }

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
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        setContentView(R.layout.activity_admin);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar_edit);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        ImageButton backButton = (ImageButton) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    imageButton.setVisibility(View.VISIBLE);
                    spinner.setVisibility(View.GONE);
                    refreshBtn.setVisibility(View.GONE);
                }
                else if (position == 1) {
                    imageButton.setVisibility(View.GONE);
                    spinner.setVisibility(View.VISIBLE);
                    refreshBtn.setVisibility(View.GONE);
                }
                else if(position == 2) {
                    imageButton.setVisibility(View.GONE);
                    spinner.setVisibility(View.GONE);
                    refreshBtn.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        // 특정 인덴트에서 store 키값을 받아와
        // 서버로 통신 하여 `가게정보, 판매중인 옷` 데이터 받아옴
        storeList = JSONTask.getStoreAll("jong4876");// JSON형태의 store정보들을 분류하여 arrayList에 저장
        store = storeList.get(0);
        clothesList = JSONTask.getClothesAll(1);

        TextView textView = (TextView) findViewById(R.id.toolbar_title);
        textView.setText(store.getName());

        imageButton = findViewById(R.id.editButton);
        spinner = findViewById(R.id.edit_spinner);
        refreshBtn = findViewById(R.id.refreshButton);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
            newConfig.orientation = Configuration.ORIENTATION_PORTRAIT;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_example_swipe_menu, menu);
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
                    storeManagementFragment = StoreManagementFragment.newInstance(0, store);
                    return storeManagementFragment;
                case 1:
                    return ItemManagementFragment.newInstance(1, clothesList, store);
                case 2:
                    return OrderManagementFragment.newInstance(2);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (storeManagementFragment != null)
            ((StoreManagementFragment) storeManagementFragment).onActivityResult(requestCode, resultCode, data);

    }

    public ImageButton getImageButton(){
        return imageButton;
    }

    public Spinner getSpinner() {
        return spinner;
    }

    public ImageButton getRefreshBtn(){
        return refreshBtn;
    }
}

package com.ghl.biz_track.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.ghl.biz_track.R;
import com.ghl.biz_track.dummy.DummyContent;
import com.ghl.biz_track.fragment.ExpendListViewFragment;
import com.ghl.biz_track.fragment.ItemFragment;
import com.ghl.biz_track.fragment.ListItemFragment;
import com.ghl.common.constants.Constants;
import com.ghl.router_annotation.Route;
import com.google.android.material.tabs.TabLayout;

@Route(Constants.Tab_Activity)
public class TabActivity extends AppCompatActivity implements ItemFragment.OnListFragmentInteractionListener {

    ViewPager mViewPager;

    Fragment mFragment1;

    Fragment mFragment2;

    Fragment mFragment3;

    PagerAdapter mPagerAdapter;

    private TabLayout mTabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        initView(savedInstanceState);

    }


    private void initView(Bundle savedInstanceState) {

        mViewPager = findViewById(R.id.view_pager);
        mViewPager.setOffscreenPageLimit(2);
        mTabLayout = findViewById(R.id.toolbar_tab);

        if (savedInstanceState == null) {
            mFragment1 = ItemFragment.newInstance("RecycleView1");
            mFragment2 = ExpendListViewFragment.newInstance("ExpendListview");
            mFragment3 = ListItemFragment.newInstance("ListView");
        }

        mPagerAdapter = new PagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        TabLayout.OnTabSelectedListener listener = new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        };
        mTabLayout.addOnTabSelectedListener(listener);
//        mTabLayout.setOnTabSelectedListener();
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }

    public class PagerAdapter extends FragmentPagerAdapter {
        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return mFragment1;
            } else if (position == 1) {
                return mFragment2;
            } else if (position == 2) {
                return mFragment3;
            }

            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

    }
}

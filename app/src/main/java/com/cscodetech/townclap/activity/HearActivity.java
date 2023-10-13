package com.cscodetech.townclap.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.cscodetech.townclap.R;
import com.cscodetech.townclap.fragment.Hear1Fragment;
import com.cscodetech.townclap.fragment.Hear2Fragment;
import com.cscodetech.townclap.model.CatlistItem;
import com.merhold.extensiblepageindicator.ExtensiblePageIndicator;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class HearActivity extends BasicActivity {

    ViewPager vpPager;
    MyPagerAdapter adapterViewPager;
    ArrayList<CatlistItem> cats;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hear);
        ButterKnife.bind(this);

        Bundle b = this.getIntent().getExtras();
        cats = b.getParcelableArrayList("cat_list");

        vpPager = findViewById(R.id.vpPager);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);

        ExtensiblePageIndicator extensiblePageIndicator = (ExtensiblePageIndicator) findViewById(R.id.flexibleIndicator);
        extensiblePageIndicator.initViewPager(vpPager);

    }
    @OnClick({R.id.txt_yes})
    public void onClick(View view) {
        if (view.getId() == R.id.txt_yes) {

            Intent i = new Intent(this, HearCategotyActivity.class);
            Bundle b = new Bundle();
            b.putParcelableArrayList("cat_list", cats);
            i.putExtras(b);
            startActivity(i);

        }
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {
        private int numItems = 2;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            return numItems;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return Hear1Fragment.newInstance();
                case 1:
                    return Hear2Fragment.newInstance();
                default:
                    return null;
            }

        }

        @Override
        public CharSequence getPageTitle(int position) {
            Log.e("page", "" + position);
            return "Page " + position;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            return fragment;
        }

    }
}
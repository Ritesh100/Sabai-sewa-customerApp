package com.cscodetech.townclap.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.cscodetech.townclap.MainActivity;
import com.cscodetech.townclap.R;
import com.cscodetech.townclap.fragment.Info1Fragment;
import com.cscodetech.townclap.fragment.Info2Fragment;
import com.cscodetech.townclap.fragment.Info3Fragment;
import com.cscodetech.townclap.utils.SessionManager;
import com.merhold.extensiblepageindicator.ExtensiblePageIndicator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IntroActivity extends BasicActivity {


    @BindView(R.id.rl_one)
    RelativeLayout rlOne;
    @BindView(R.id.txt_login)
    TextView txtLogin;
    @BindView(R.id.txt_register)
    TextView txtRegister;
    int selectPage = 0;
    SessionManager sessionManager;
    public static ViewPager vpPager;
    MyPagerAdapter adapterViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        ButterKnife.bind(this);

        vpPager = findViewById(R.id.vpPager);
        sessionManager = new SessionManager(IntroActivity.this);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        if (sessionManager.getBooleanData(SessionManager.login) || sessionManager.getBooleanData(SessionManager.intro)) {
            startActivity(new Intent(IntroActivity.this, MainActivity.class));
            finish();
        }
        vpPager.setAdapter(adapterViewPager);
        ExtensiblePageIndicator extensiblePageIndicator = (ExtensiblePageIndicator) findViewById(R.id.flexibleIndicator);
        extensiblePageIndicator.initViewPager(vpPager);
        vpPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.e("data", "jsadlj");
            }

            @Override
            public void onPageSelected(int position) {
                selectPage = position;


            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.e("sjlkj", "sjahdal");
            }
        });
    }

    @OnClick({R.id.txt_login, R.id.txt_register,})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.txt_login:
                startActivity(new Intent(IntroActivity.this, LoginActivity.class));
                break;

            case R.id.txt_register:
                startActivity(new Intent(IntroActivity.this, LoginActivity.class));

                break;
            default:
                break;
        }
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {
        private int numItems = 3;

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
                    return Info1Fragment.newInstance();
                case 1:
                    return Info2Fragment.newInstance();
                case 2:
                    return Info3Fragment.newInstance();
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

package com.cscodetech.townclap.activity;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.cscodetech.townclap.R;
import com.cscodetech.townclap.fragment.HomeFragment;
import com.cscodetech.townclap.utils.Utility;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends BasicActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setContentView(R.layout.activity_home);

        requestPermissions(new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        final LocationManager manager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER) && Utility.hasGPSDevice(this)) {
            Toast.makeText(this, "Gps not enabled", Toast.LENGTH_SHORT).show();
            Utility.enableLoc(this);
        }
        loadFragment(new HomeFragment());
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
    }
    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.page_1:
                fragment = new HomeFragment();
                break;
            case R.id.page_2:
                startActivity(new Intent(this, BookingActivity.class));
                break;
            case R.id.page_3:
                startActivity(new Intent(HomeActivity.this, WalletActivity.class));
                break;
            case R.id.page_4:
                startActivity(new Intent(this, NotificationActivity.class));
                break;
            case R.id.page_5:
                startActivity(new Intent(this, SettingActivity.class));
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + item.getItemId());
        }

        return loadFragment(fragment);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
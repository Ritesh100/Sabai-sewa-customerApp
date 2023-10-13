package com.cscodetech.townclap.activity;

import static com.cscodetech.townclap.utils.SessionManager.languages;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.cscodetech.townclap.R;
import com.cscodetech.townclap.utils.SessionManager;

import java.util.Locale;

public class BasicActivity extends AppCompatActivity {
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);
        sessionManager = new SessionManager(this);
        if (sessionManager.getStringData(languages) == null) {
            sessionManager.setStringData(languages, "en");
        }
        setApplicationlanguage(sessionManager.getStringData(languages));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void setApplicationlanguage(String language) {
        try {
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                conf.setLocale(new Locale(language)); // API 17+ only.
            } else {
                conf.locale = new Locale(language);
            }
            res.updateConfiguration(conf, dm);
        } catch (Exception e) {
            Log.e("Error for RTL ", "-->" + e.getMessage());
        }

    }

}
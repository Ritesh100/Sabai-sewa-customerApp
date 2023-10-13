package com.cscodetech.townclap;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.cscodetech.townclap.activity.BasicActivity;

public class MainActivity extends BasicActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}
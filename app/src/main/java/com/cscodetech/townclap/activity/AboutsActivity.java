package com.cscodetech.townclap.activity;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.cscodetech.townclap.R;
import com.cscodetech.townclap.utils.SessionManager;

import butterknife.BindView;
import butterknife.ButterKnife;



public class AboutsActivity extends BasicActivity {

    @BindView(R.id.txt_about)
    TextView txtAbout;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abouts);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sessionManager=new SessionManager(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            txtAbout.setText(Html.fromHtml(sessionManager.getStringData(SessionManager.about), Html.FROM_HTML_MODE_COMPACT));
        } else {
            txtAbout.setText(Html.fromHtml(sessionManager.getStringData(SessionManager.about)));
        }
    }
}

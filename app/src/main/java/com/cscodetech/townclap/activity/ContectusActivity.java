package com.cscodetech.townclap.activity;

import static com.cscodetech.townclap.utils.SessionManager.contact;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.cscodetech.townclap.R;
import com.cscodetech.townclap.utils.SessionManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContectusActivity extends BasicActivity {
    @BindView(R.id.txt_contac)
    TextView txtContac;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contectus);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            txtContac.setText(Html.fromHtml(sessionManager.getStringData(contact), Html.FROM_HTML_MODE_COMPACT));
        } else {
            txtContac.setText(Html.fromHtml(sessionManager.getStringData(contact)));
        }
    }
}

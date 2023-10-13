package com.cscodetech.townclap.activity;

import static com.cscodetech.townclap.utils.SessionManager.terms;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.cscodetech.townclap.R;
import com.cscodetech.townclap.utils.SessionManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TramsAndConditionActivity extends BasicActivity {
    @BindView(R.id.txt_privacy)
    TextView txtPrivacy;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trams_and_condition);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sessionManager = new SessionManager(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            txtPrivacy.setText(Html.fromHtml(sessionManager.getStringData(terms), Html.FROM_HTML_MODE_COMPACT));
        } else {
            txtPrivacy.setText(Html.fromHtml(sessionManager.getStringData(terms)));
        }
    }
}

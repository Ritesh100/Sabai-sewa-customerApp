package com.cscodetech.townclap.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.cscodetech.townclap.R;
import com.cscodetech.townclap.model.PartnerListDataItem;
import com.cscodetech.townclap.retrofit.APIClient;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExportActivity extends BasicActivity {

    @BindView(R.id.txt_image)
    ImageView txtImage;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.txt_type)
    TextView txtType;
    @BindView(R.id.txt_rating)
    TextView txtRating;
    @BindView(R.id.totale)
    TextView totale;
    @BindView(R.id.txt_jobc)
    TextView txtJobc;

    @BindView(R.id.txt_bio)
    TextView txtBio;

    PartnerListDataItem partner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export);

        ButterKnife.bind(this);
        partner=  getIntent().getParcelableExtra("myclass");
        getSupportActionBar().setTitle("Expert Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Glide.with(this).load(APIClient.baseUrl + "/" + partner.getPimg()).into(txtImage);
        txtName.setText(""+partner.getName());
        txtType.setText(""+partner.getCatName());
        txtRating.setText(""+partner.getRate());
        totale.setText(""+partner.getTotalEarn());
        txtJobc.setText(""+partner.getTotalComplete());
        txtBio.setText(""+partner.getBio());


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
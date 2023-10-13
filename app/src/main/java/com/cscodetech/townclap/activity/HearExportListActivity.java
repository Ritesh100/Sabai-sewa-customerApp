package com.cscodetech.townclap.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cscodetech.townclap.R;
import com.cscodetech.townclap.adepter.HearExportAdapter;
import com.cscodetech.townclap.model.Partner;
import com.cscodetech.townclap.model.PartnerListDataItem;
import com.cscodetech.townclap.model.User;
import com.cscodetech.townclap.retrofit.APIClient;
import com.cscodetech.townclap.retrofit.GetResult;
import com.cscodetech.townclap.utils.CustPrograssbar;
import com.cscodetech.townclap.utils.SessionManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

public class HearExportListActivity extends BasicActivity implements HearExportAdapter.RecyclerTouchListener, GetResult.MyListener {

    @BindView(R.id.recycler_export)
    RecyclerView recyclerExport;
    @BindView(R.id.txt_notfound)
    TextView txtNotfound;

    CustPrograssbar custPrograssbar;
    User user;
    SessionManager sessionManager;

    String cid;
    String sid;
    String name;
    String named;
    String videoUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hear_exportlist);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(this);
        user = sessionManager.getUserDetails("");
        custPrograssbar = new CustPrograssbar();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        cid = getIntent().getStringExtra("cid");
        sid = getIntent().getStringExtra("sid");
        name = getIntent().getStringExtra("name");
        named = getIntent().getStringExtra("named");
        videoUrl = getIntent().getStringExtra("vurl");
        getSupportActionBar().setTitle(name);
        recyclerExport.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerExport.setItemAnimator(new DefaultItemAnimator());
        getExport();

    }

    private void getExport() {
        custPrograssbar.prograssCreate(this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid", user.getId());
            jsonObject.put("cid", cid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestBody bodyRequest = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        Call<JsonObject> call = APIClient.getInterface().getPartner(bodyRequest);
        GetResult getResult = new GetResult();
        getResult.setMyListener(this);
        getResult.callForLogin(call, "1");

    }

    @Override
    public void callback(JsonObject result, String callNo) {
        try {
            custPrograssbar.closePrograssBar();
            if (callNo.equalsIgnoreCase("1")) {
                Gson gson = new Gson();
                Partner partner = gson.fromJson(result.toString(), Partner.class);
                if (partner.getResult().equalsIgnoreCase("true")) {
                    if (partner.getPartnerListData().size() != 0) {
                        recyclerExport.setVisibility(View.VISIBLE);
                        txtNotfound.setVisibility(View.GONE);
                        HearExportAdapter categoryAdapter = new HearExportAdapter(this, partner.getPartnerListData(), this);
                        recyclerExport.setAdapter(categoryAdapter);

                    } else {
                        recyclerExport.setVisibility(View.GONE);
                        txtNotfound.setVisibility(View.VISIBLE);
                        txtNotfound.setText("" + partner.getResponseMsg());
                    }

                } else {
                    recyclerExport.setVisibility(View.GONE);
                    txtNotfound.setVisibility(View.VISIBLE);
                    txtNotfound.setText("" + partner.getResponseMsg());
                }
            }
        } catch (Exception e) {
            e.toString();
        }

    }


    @Override
    public void onClickPartnerItem(PartnerListDataItem partner, int position) {
        if (position == 10) {
            sessionManager.setStringData("pid", partner.getId());
            sessionManager.setStringData("pname", partner.getName());
            sessionManager.setStringData("pimage", partner.getPimg());
            if (sid.equalsIgnoreCase("0")) {
                startActivity(new Intent(this, SubCategoryActivity.class)
                        .putExtra("vurl", videoUrl)
                        .putExtra("name", name)
                        .putExtra("named", named)
                        .putExtra("cid", cid)
                        .putExtra("sid", "0"));
            } else {
                startActivity(new Intent(this, SubCategoyTypeActivity.class)
                        .putExtra("cid", cid));
            }
        } else {
            startActivity(new Intent(HearExportListActivity.this, ExportActivity.class).putExtra("myclass", partner));

        }
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
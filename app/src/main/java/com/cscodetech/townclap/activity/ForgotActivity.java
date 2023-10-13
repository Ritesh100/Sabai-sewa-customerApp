package com.cscodetech.townclap.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cscodetech.townclap.R;
import com.cscodetech.townclap.model.ContryCode;
import com.cscodetech.townclap.model.ResponseMessge;
import com.cscodetech.townclap.retrofit.APIClient;
import com.cscodetech.townclap.retrofit.GetResult;
import com.cscodetech.townclap.utils.CustPrograssbar;
import com.cscodetech.townclap.utils.SessionManager;
import com.cscodetech.townclap.utils.Utility;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

 

public class ForgotActivity extends BasicActivity implements GetResult.MyListener {
    @BindView(R.id.btn_send)
    TextView btnSend;
    SessionManager sessionManager;
    CustPrograssbar custPrograssbar;
    @BindView(R.id.ed_mobile)
    EditText edMobile;


    @BindView(R.id.at_code)
    AutoCompleteTextView atCode;

    String codeSelect;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {// app icon in action bar clicked; go home
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        custPrograssbar = new CustPrograssbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sessionManager = new SessionManager( ForgotActivity.this);

        getCode();

    }

    @OnClick({R.id.btn_send})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.btn_send) {
            chackUser();
        }
    }

    private void getCode() {
        JSONObject jsonObject = new JSONObject();

        RequestBody bodyRequest = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        Call<JsonObject> call = APIClient.getInterface().getCodelist(bodyRequest);
        GetResult getResult = new GetResult();
        getResult.setMyListener(this);
        getResult.callForLogin(call, "2");
    }

    public boolean validation() {
        if (edMobile.getText().toString().isEmpty()) {
            edMobile.setError("Enter Mobile");
            return false;
        }
        return true;
    }

    private void chackUser() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mobile", edMobile.getText().toString());
            jsonObject.put("ccode", codeSelect);
            RequestBody bodyRequest = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
            Call<JsonObject> call = APIClient.getInterface().getMobileCheck(bodyRequest);
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.callForLogin(call, "1");
            custPrograssbar.prograssCreate( ForgotActivity.this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void callback(JsonObject result, String callNo) {
        try {
            custPrograssbar.closePrograssBar();
            if (callNo.equalsIgnoreCase("1")) {
                Gson gson = new Gson();
                ResponseMessge response = gson.fromJson(result.toString(), ResponseMessge.class);
                if (!response.getResult().equals("true")) {
                    Utility.isvarification = 0;
                    startActivity(new Intent( ForgotActivity.this, VerifyPhoneActivity.class).putExtra("code", atCode.getText().toString()).putExtra("phone", edMobile.getText().toString()));
                } else {
                    Toast.makeText( ForgotActivity.this, "" + response.getResponseMsg(), Toast.LENGTH_LONG).show();

                }
            } else if (callNo.equalsIgnoreCase("2")) {
                Gson gson = new Gson();
                ContryCode contryCode = gson.fromJson(result.toString(), ContryCode.class);
                ArrayList<String> countries = new ArrayList<>();
                if (contryCode.getResult().equalsIgnoreCase("true")) {
                    countries = new ArrayList<>();
                    for (int i = 0; i < contryCode.getCountryCode().size(); i++) {
                        countries.add(contryCode.getCountryCode().get(i).getCcode());
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>
                        (this, android.R.layout.simple_list_item_1, countries);
                atCode.setAdapter(adapter);
                atCode.setThreshold(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

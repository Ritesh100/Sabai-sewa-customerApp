package com.cscodetech.townclap.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cscodetech.townclap.R;
import com.cscodetech.townclap.model.ResponseMessge;
import com.cscodetech.townclap.retrofit.APIClient;
import com.cscodetech.townclap.retrofit.GetResult;
import com.cscodetech.townclap.utils.CustPrograssbar;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

public class ChanegPasswordActivity extends BasicActivity implements GetResult.MyListener {
    @BindView(R.id.ed_password)
    EditText edPassword;
    @BindView(R.id.ed_conpassword1)
    EditText edConPassword;
    @BindView(R.id.btn_submit)
    TextView btnSubmit;
    String phoneNumber;
    CustPrograssbar custPrograssbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chaneg_password);
        ButterKnife.bind(this);
        phoneNumber = getIntent().getStringExtra("phone");
        custPrograssbar = new CustPrograssbar();
    }
    @OnClick(R.id.btn_submit)
    public void onClick() {
        if (validation())
            setPassword();
    }
    public boolean validation() {

        if (edPassword.getText().toString().isEmpty()) {
            edPassword.setError("Enter Password");
            return false;
        }
        if (edConPassword.getText().toString().isEmpty()) {
            edConPassword.setError("Enter Confirm");
            return false;
        }
        if (!edConPassword.getText().toString().equals(edPassword.getText().toString())) {
            edConPassword.setError("Mismatch Password");
            edPassword.setError("Mismatch Password");
            return false;
        }
        return true;
    }

    private void setPassword() {
        custPrograssbar.prograssCreate( ChanegPasswordActivity.this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mobile", phoneNumber);
            jsonObject.put("password", edPassword.getText().toString());
            RequestBody bodyRequest = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
            Call<JsonObject> call = APIClient.getInterface().getForgot(bodyRequest);
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.callForLogin(call, "1");


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void callback(JsonObject result, String callNo) {
        try {
            custPrograssbar.closePrograssBar();
            Gson gson = new Gson();
            ResponseMessge response = gson.fromJson(result.toString(), ResponseMessge.class);
            Toast.makeText( ChanegPasswordActivity.this, "" + response.getResponseMsg(), Toast.LENGTH_LONG).show();
            if (response.getResult().equals("true")) {
                Intent intent = new Intent( ChanegPasswordActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
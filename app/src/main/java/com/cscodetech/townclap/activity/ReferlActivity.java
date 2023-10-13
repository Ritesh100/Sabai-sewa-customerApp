package com.cscodetech.townclap.activity;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cscodetech.townclap.BuildConfig;
import com.cscodetech.townclap.R;
import com.cscodetech.townclap.model.ResponseMessge;
import com.cscodetech.townclap.model.User;
import com.cscodetech.townclap.retrofit.APIClient;
import com.cscodetech.townclap.retrofit.GetResult;
import com.cscodetech.townclap.utils.CustPrograssbar;
import com.cscodetech.townclap.utils.SessionManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

public class ReferlActivity extends BasicActivity implements GetResult.MyListener {

    @BindView(R.id.txt_t1)
    TextView txtT1;
    @BindView(R.id.txt_t2)
    TextView txtT2;
    @BindView(R.id.txt_t3)
    TextView txtT3;
    @BindView(R.id.txt_code)
    TextView txtCode;
    @BindView(R.id.txt_share)
    TextView txtShare;

    @BindView(R.id.txt_copy)
    TextView txtCopy;

    User user;
    SessionManager sessionManager;
    CustPrograssbar custPrograssbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_referl);
        ButterKnife.bind(this);
        getSupportActionBar().setTitle(R.string.referearn);
        sessionManager = new SessionManager(ReferlActivity.this);
        custPrograssbar = new CustPrograssbar();
        user = sessionManager.getUserDetails("");
        getData();
    }

    private void getData() {
        try {
            custPrograssbar.prograssCreate(ReferlActivity.this);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("uid", user.getId());
            RequestBody bodyRequest = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
            Call<JsonObject> call = APIClient.getInterface().getRefercode(bodyRequest);
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
            if (callNo.equalsIgnoreCase("1")) {
                Gson gson = new Gson();
                ResponseMessge restResponse = gson.fromJson(result.toString(), ResponseMessge.class);
                if (restResponse.getResult().equalsIgnoreCase("true")) {
                    txtT2.setText(getString(R.string.r1)+" " + sessionManager.getStringData(SessionManager.currency) + restResponse.getRefercredit() +" "+ getString(R.string.r2));
                    txtT3.setText(getString(R.string.r3) +" "+ sessionManager.getStringData(SessionManager.currency) + restResponse.getSignupcredit() + getString(R.string.wall));
                    txtCode.setText("" + restResponse.getCode());

                }

            }
        } catch (Exception e) {
            Log.e("Error", "" + e.toString());
        }
    }

    @OnClick({R.id.txt_share, R.id.txt_code, R.id.txt_copy})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_share:
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                    String shareMessage = "Hey! Now use our app to share with your family or friends. User will get wallet amount on your 1st successful order. Enter my referral code *" + txtCode.getText().toString() + "* & Enjoy your shopping !!!";
                    shareMessage = shareMessage + " https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch (Exception e) {
                    Log.e("error", Objects.requireNonNull(e.getMessage()));
                }
                break;
            case R.id.txt_code:

                break;
            case R.id.txt_copy:
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(txtCode.getText().toString());
                Toast.makeText(ReferlActivity.this, getString(R.string.textcoyu), Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }
}
package com.cscodetech.townclap.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cscodetech.townclap.R;
import com.cscodetech.townclap.adepter.TrazectionAdapter;
import com.cscodetech.townclap.model.User;
import com.cscodetech.townclap.model.Wallet;
import com.cscodetech.townclap.retrofit.APIClient;
import com.cscodetech.townclap.retrofit.GetResult;
import com.cscodetech.townclap.utils.CustPrograssbar;
import com.cscodetech.townclap.utils.SessionManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

public class WalletActivity extends BasicActivity implements GetResult.MyListener {
    @BindView(R.id.txt_wallet)
    TextView txtWallet;
    @BindView(R.id.txt_income)
    TextView txtIncome;
    @BindView(R.id.txt_expence)
    TextView txtExpence;
    @BindView(R.id.recycle_trazection)
    RecyclerView recycleTrazection;

    SessionManager sessionManager;
    User user;
    CustPrograssbar custPrograssbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        getSupportActionBar().setTitle(R.string.mywallet);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recycleTrazection.setLayoutManager(new LinearLayoutManager(this));
        recycleTrazection.setItemAnimator(new DefaultItemAnimator());


        sessionManager = new SessionManager(WalletActivity.this);
        user = sessionManager.getUserDetails("");
        custPrograssbar = new CustPrograssbar();
        getWallet();

    }

    private void getWallet() {
        custPrograssbar.prograssCreate(WalletActivity.this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid", user.getId());
            RequestBody bodyRequest = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
            Call<JsonObject> call = APIClient.getInterface().getWallet(bodyRequest);
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
                Wallet wallet = gson.fromJson(result.toString(), Wallet.class);
                if (wallet.getResult().equalsIgnoreCase("true")) {
                    txtWallet.setText(sessionManager.getStringData(SessionManager.currency) + wallet.getWallets());
                    txtExpence.setText(sessionManager.getStringData(SessionManager.currency) + wallet.getDebittotal());
                    txtIncome.setText(sessionManager.getStringData(SessionManager.currency) + wallet.getCredittotal());
                    sessionManager.setStringData(SessionManager.wallet,wallet.getWallets());
                    TrazectionAdapter adapter = new TrazectionAdapter(this, wallet.getWalletitem());
                    recycleTrazection.setAdapter(adapter);
                }
            }

        } catch (Exception e) {
            Log.e("Error", "" + e.toString());


        }

    }
}
package com.cscodetech.townclap.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cscodetech.townclap.R;
import com.cscodetech.townclap.adepter.AddonsAdapter;
import com.cscodetech.townclap.model.AddOnDataItem;
import com.cscodetech.townclap.model.Addon;
import com.cscodetech.townclap.model.User;
import com.cscodetech.townclap.retrofit.APIClient;
import com.cscodetech.townclap.retrofit.GetResult;
import com.cscodetech.townclap.utils.CustPrograssbar;
import com.cscodetech.townclap.utils.SessionManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

public class AddOnsActivity extends BasicActivity implements AddonsAdapter.RecyclerTouchListener, GetResult.MyListener {

    @BindView(R.id.recycle_addone)
    RecyclerView recycleAddone;
    @BindView(R.id.lvl_next)
    LinearLayout lvlNext;

    String cid;
    String name;
    CustPrograssbar custPrograssbar;
    SessionManager sessionManager;
    User user;
    AddonsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ons);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name = getIntent().getStringExtra("name");
        getSupportActionBar().setTitle(name);
        custPrograssbar = new CustPrograssbar();
        sessionManager = new SessionManager(this);
        user = sessionManager.getUserDetails("");
        cid = getIntent().getStringExtra("cid");
        getAdon();
        recycleAddone.setLayoutManager(new GridLayoutManager(this, 2));
        recycleAddone.setItemAnimator(new DefaultItemAnimator());

    }


    private void getAdon() {
        custPrograssbar.prograssCreate(this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid", user.getId());
            jsonObject.put("cid", cid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestBody bodyRequest = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        Call<JsonObject> call = APIClient.getInterface().listaddon(bodyRequest);
        GetResult getResult = new GetResult();
        getResult.setMyListener(this);
        getResult.callForLogin(call, "1");

    }

    @Override
    public void onClickAddonsItem(String title, int position) {


    }

    @Override
    public void callback(JsonObject result, String callNo) {
        try {
            custPrograssbar.closePrograssBar();
            if (callNo.equalsIgnoreCase("1")) {
                Gson gson = new Gson();
                Addon addon = gson.fromJson(result.toString(), Addon.class);

                adapter = new AddonsAdapter(AddOnsActivity.this, addon.getAddondata(), this);
                recycleAddone.setAdapter(adapter);
            }
        } catch (Exception e) {
            Log.e("error", "-->" + e.toString());
        }
    }


    @OnClick({R.id.lvl_next})
    public void onClick(View view) {
        if (view.getId() == R.id.lvl_next) {
            ArrayList<AddOnDataItem> list = adapter.getSelectItem();
            ArrayList<AddOnDataItem> listq = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).isSelected()) {
                    listq.add(list.get(i));
                }
            }
            if (!listq.isEmpty()) {
                startActivity(new Intent(AddOnsActivity.this, CartActivity.class)
                        .putExtra("name", name)
                        .putExtra("cid", cid)
                        .putParcelableArrayListExtra("slist", listq));
            } else {
                Toast.makeText(AddOnsActivity.this, R.string.selectone, Toast.LENGTH_LONG).show();
            }
        }
    }
}
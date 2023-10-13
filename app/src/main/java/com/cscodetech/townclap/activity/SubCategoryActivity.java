package com.cscodetech.townclap.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cscodetech.townclap.R;
import com.cscodetech.townclap.adepter.SubCategoryAdapter;
import com.cscodetech.townclap.model.Chiald;
import com.cscodetech.townclap.model.ChildcatItem;
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
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

public class SubCategoryActivity extends BasicActivity implements SubCategoryAdapter.RecyclerTouchListener, GetResult.MyListener {
    @BindView(R.id.plyer)
    VideoView plyer;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.txt_named)
    TextView txtNameD;
    @BindView(R.id.txt_servicettile)
    TextView txtServicettile;
    @BindView(R.id.my_recycler_subcat)
    RecyclerView myRecyclerSubcat;
    String cid;
    String sid;
    String name;
    String named;
    String videoUrl;
    CustPrograssbar custPrograssbar;
    SessionManager sessionManager;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setContentView(R.layout.activity_sub_category);
        ButterKnife.bind(this);
        custPrograssbar = new CustPrograssbar();
        sessionManager = new SessionManager(SubCategoryActivity.this);
        user = sessionManager.getUserDetails("");
        cid = getIntent().getStringExtra("cid");
        sid = getIntent().getStringExtra("sid");
        name = getIntent().getStringExtra("name");
        named = getIntent().getStringExtra("named");
        videoUrl = getIntent().getStringExtra("vurl");

        txtName.setText("" + name);
        txtNameD.setText("" + named);
        plyer.setVideoURI(Uri.parse(APIClient.baseUrl + "/" + videoUrl));
        plyer.requestFocus();


        plyer.setOnCompletionListener(mp -> plyer.start());
        plyer.setOnPreparedListener(mp -> plyer.start());

        myRecyclerSubcat.setLayoutManager(new GridLayoutManager(SubCategoryActivity.this, 3));
        myRecyclerSubcat.setItemAnimator(new DefaultItemAnimator());

        getData();
    }

    private void getData() {
        custPrograssbar.prograssCreate(this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid", user.getId());
            jsonObject.put("cid", cid);
            jsonObject.put("sid", sid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestBody bodyRequest = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        Call<JsonObject> call = APIClient.getInterface().sChildList(bodyRequest);
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
                Chiald chiald = gson.fromJson(result.toString(), Chiald.class);
                if (chiald.getResult().equalsIgnoreCase("true")) {
                    if (chiald.getChildcat().size() != 0) {
                        SubCategoryAdapter adapter = new SubCategoryAdapter(this, chiald.getChildcat(), this);
                        myRecyclerSubcat.setAdapter(adapter);
                        txtServicettile.setText(getResources().getString(R.string.select_a_service));
                    } else {
                        txtServicettile.setText("" + chiald.getResponseMsg());
                    }
                }else {
                    txtServicettile.setText("" + chiald.getResponseMsg());
                }

            }

        } catch (Exception e) {
            Log.e("Error", "" + e.toString());

        }
    }

    @OnClick({R.id.img_back})
    public void onClick(View view) {
        if (view.getId() == R.id.img_back) {
            finish();
        }
    }

    @Override
    public void onClickSubCategoryItem(ChildcatItem item, int position) {

        startActivity(new Intent(this, ItemListActivity.class)
                .putExtra("name", name)
                .putExtra("cid", cid)
                .putExtra("sid", sid));
    }


}
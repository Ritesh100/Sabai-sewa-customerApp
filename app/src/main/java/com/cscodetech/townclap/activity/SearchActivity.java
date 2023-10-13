package com.cscodetech.townclap.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cscodetech.townclap.R;
import com.cscodetech.townclap.adepter.SearchAdapter;
import com.cscodetech.townclap.model.Search;
import com.cscodetech.townclap.model.SearchChildcatdataItem;
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

public class SearchActivity extends BasicActivity implements GetResult.MyListener, SearchAdapter.RecyclerTouchListener {

    @BindView(R.id.lvl_actionsearch)
    LinearLayout lvlActionsearch;
    @BindView(R.id.ed_search)
    EditText edSearch;
    @BindView(R.id.img_search)
    ImageView imgSearch;
    @BindView(R.id.recycler_product)
    RecyclerView recyclerProduct;
    CustPrograssbar custPrograssbar;
    SessionManager sessionManager;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        custPrograssbar = new CustPrograssbar();
        sessionManager = new SessionManager(SearchActivity.this);
        user = sessionManager.getUserDetails("");
        recyclerProduct.setLayoutManager(new LinearLayoutManager(this));
        edSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (!edSearch.getText().toString().isEmpty()) {
                    getSearch(edSearch.getText().toString());
                }
                return true;
            }
            return false;
        });

    }

    private void getSearch(String s) {
        custPrograssbar.prograssCreate(SearchActivity.this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid", user.getId());
            jsonObject.put("keyword", s);

        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestBody bodyRequest = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        Call<JsonObject> call = APIClient.getInterface().getSearch(bodyRequest);
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
                Search search = gson.fromJson(result.toString(), Search.class);
                SearchAdapter adapter = new SearchAdapter( search.getSearchChildcatdata(), this);
                recyclerProduct.setAdapter(adapter);

            }
        } catch (Exception e) {
            Log.e("Error","-->"+e.toString());
        }
    }

    @Override
    public void onClickSearch(SearchChildcatdataItem dataItem, int position) {

        startActivity(new Intent(this, ItemListActivity.class)
                .putExtra("name", dataItem.getTitle())
                .putExtra("cid", dataItem.getCategoryId())
                .putExtra("sid", dataItem.getSubcatId()));
    }
}
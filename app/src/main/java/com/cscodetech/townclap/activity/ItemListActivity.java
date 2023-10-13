package com.cscodetech.townclap.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cscodetech.townclap.R;
import com.cscodetech.townclap.adepter.ItemAdapter;
import com.cscodetech.townclap.model.ServiceListItem;
import com.cscodetech.townclap.model.ServicelistdataItem;
import com.cscodetech.townclap.model.Services;
import com.cscodetech.townclap.model.User;
import com.cscodetech.townclap.retrofit.APIClient;
import com.cscodetech.townclap.retrofit.GetResult;
import com.cscodetech.townclap.utils.CustPrograssbar;
import com.cscodetech.townclap.utils.MyDatabase;
import com.cscodetech.townclap.utils.SessionManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

public class ItemListActivity extends BasicActivity implements ItemAdapter.RecyclerTouchListener, GetResult.MyListener {

    @BindView(R.id.lvl_title)
    LinearLayout lvlTitle;
    @BindView(R.id.lvl_cart)
    LinearLayout lvlCart;
    @BindView(R.id.lvl_item)
    LinearLayout lvlItem;
    @BindView(R.id.txt_qty)
    TextView txtQty;
    @BindView(R.id.txt_totle)
    TextView txtTotle;
    CustPrograssbar custPrograssbar;
    SessionManager sessionManager;
    User user;
    String cid;
    String sid;
    String name;
    public static ItemListActivity itemListActivity;
    MyDatabase myDatabase;

    public static ItemListActivity getInstance() {
        return itemListActivity;
    }

    public void cardData(List<ServiceListItem> list) {
        if (list.isEmpty()) {
            lvlCart.setVisibility(View.GONE);
        } else {
            double totle = 0.0;
            int qty = 0;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getServiceCatId().equalsIgnoreCase(cid)) {
                    double res3 = (list.get(i).getServicePrice() / 100.0f) * list.get(i).getServiceDiscount();
                    double pp = list.get(i).getServicePrice() - res3;
                    pp = pp * Integer.parseInt(list.get(i).getServiceQty());
                    totle = totle + pp;
                    qty = qty + 1;
                    lvlCart.setVisibility(View.VISIBLE);
                }
            }
            txtQty.setText("" + qty);
            txtTotle.setText(sessionManager.getStringData(SessionManager.currency) + totle);


        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        ButterKnife.bind(this);
        itemListActivity = this;
        sessionManager = new SessionManager(this);
        custPrograssbar = new CustPrograssbar();
        myDatabase = new MyDatabase(ItemListActivity.this);
        user = sessionManager.getUserDetails("");
        cid = getIntent().getStringExtra("cid");
        sid = getIntent().getStringExtra("sid");
        name = getIntent().getStringExtra("name");
        getSupportActionBar().setTitle(name);
        cardData(myDatabase.getCData(cid));


        getServices();


    }

    private void getServices() {
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
        Call<JsonObject> call = APIClient.getInterface().serviceList(bodyRequest);
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
                Services services = gson.fromJson(result.toString(), Services.class);
                setServicerList(lvlItem, services.getServicelistdata());
            }
        } catch (Exception e) {
            Log.e("Erorr->", "-->" + e.toString());
        }
    }

    RecyclerView recyclerViewList;

    private void setServicerList(LinearLayout lnrView, List<ServicelistdataItem> dataList) {
        lnrView.removeAllViews();
        for (int i = 0; i < dataList.size(); i++) {
            LayoutInflater inflater = LayoutInflater.from(this);
            View view = inflater.inflate(R.layout.itemfull_service, null);
            TextView itemTitle = view.findViewById(R.id.itemTitle);
            recyclerViewList = view.findViewById(R.id.recycler_view_list);
            itemTitle.setText("" + dataList.get(i).getTitle());
            ItemAdapter itemAdp = new ItemAdapter(this, dataList.get(i).getServiceList(), this);
            recyclerViewList.setLayoutManager(new GridLayoutManager(this, 1));
            recyclerViewList.setAdapter(itemAdp);
            recyclerViewList.smoothScrollToPosition(i);
            lnrView.addView(view);

        }


    }


    @Override
    public void onClickItem(String titel, int position) {

    }

    @OnClick({R.id.lvl_cart})
    public void onClick(View view) {
        if (view.getId() == R.id.lvl_cart) {
            startActivity(new Intent(ItemListActivity.this, AddOnsActivity.class)
                    .putExtra("name", name)
                    .putExtra("cid", cid));
        }
    }


}
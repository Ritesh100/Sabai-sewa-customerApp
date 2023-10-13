package com.cscodetech.townclap.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cscodetech.townclap.R;
import com.cscodetech.townclap.adepter.CartAdapter;
import com.cscodetech.townclap.model.AddOnDataItem;
import com.cscodetech.townclap.model.ServiceListItem;
import com.cscodetech.townclap.utils.MyDatabase;
import com.cscodetech.townclap.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CartActivity extends BasicActivity {
    @BindView(R.id.txt_totle_itemD)
    TextView txtTotleItemD;
    @BindView(R.id.txt_totle_item)
    TextView txtTotleItem;
    @BindView(R.id.txt_total)
    TextView txtTotalF;
    @BindView(R.id.lvl_adon)
    LinearLayout lvlAdon;
    @BindView(R.id.txt_sftcharge)
    TextView txtSftcharge;
    @BindView(R.id.txt_adontotle)
    TextView txtAdontotle;
    @BindView(R.id.lvl_continue)
    LinearLayout lvlContinue;
    @BindView(R.id.recycle_service)
    RecyclerView recycleService;
    @BindView(R.id.ftotle)
    TextView ftotle;
    @BindView(R.id.txt_saving)
    TextView txtSaving;
    @BindView(R.id.lvl_wallet)
    LinearLayout lvlWallet;

    @BindView(R.id.checkwallet)
    CheckBox checkWallet;
    ArrayList<AddOnDataItem> list;
    MyDatabase myDatabase;
    SessionManager sessionManager;
    List<ServiceListItem> serviceList;
    double addontotle = 0.0;
    double totleItem = 0.0;
    double totleItemD = 0.0;
    String name;
    String cid;

    public static CartActivity cartActivity;

    public static CartActivity getInstance() {
        return cartActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ButterKnife.bind(this);
        cartActivity = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myDatabase = new MyDatabase(CartActivity.this);
        sessionManager = new SessionManager(CartActivity.this);
        name = getIntent().getStringExtra("name");
        getSupportActionBar().setTitle(name);
        cid = getIntent().getStringExtra("cid");
        list = getIntent().getParcelableArrayListExtra("slist");
        serviceList = myDatabase.getCData(cid);

        recycleService.setLayoutManager(new GridLayoutManager(this, 1));

        CartAdapter cartAdapter = new CartAdapter(CartActivity.this, serviceList);
        recycleService.setAdapter(cartAdapter);
        setList(lvlAdon, list);
        String w=sessionManager.getStringData(SessionManager.wallet);

        if (w.isEmpty() || Integer.parseInt(w) == 0) {
            lvlWallet.setVisibility(View.GONE);
        }else {
            checkWallet.setOnCheckedChangeListener((buttonView, isChecked) -> totelDisply());
            checkWallet.setText(sessionManager.getStringData(SessionManager.currency) + sessionManager.getStringData(SessionManager.wallet));
        }

    }

    private void setList(LinearLayout lnrView, List<AddOnDataItem> dataList) {

        lnrView.removeAllViews();

        for (int i = 0; i < dataList.size(); i++) {
            if (!dataList.get(i).getPrice().equalsIgnoreCase("0")) {
                LayoutInflater inflater = LayoutInflater.from(this);
                View view = inflater.inflate(R.layout.item_adon, null);
                TextView itemTitle = view.findViewById(R.id.txt_title);
                TextView txtPrice = view.findViewById(R.id.txt_price);
                itemTitle.setText("" + dataList.get(i).getTitle());
                txtPrice.setText(sessionManager.getStringData(SessionManager.currency) + dataList.get(i).getPrice());
                addontotle = addontotle + Double.parseDouble(dataList.get(i).getPrice());
                lnrView.addView(view);
            }

        }
        txtAdontotle.setText(sessionManager.getStringData(SessionManager.currency) + addontotle);
        cardData(serviceList);
    }


    public void cardData(List<ServiceListItem> list) {

        totleItem = 0.0;
        totleItemD = 0.0;

        int qty = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getServiceCatId().equalsIgnoreCase(cid)) {
                double res3 = (list.get(i).getServicePrice() / 100.0f) * list.get(i).getServiceDiscount();
                double pp = list.get(i).getServicePrice() - res3;
                pp = pp * Integer.parseInt(list.get(i).getServiceQty());
                totleItemD = totleItemD + pp;
                totleItem = totleItem + (list.get(i).getServicePrice() * Integer.parseInt(list.get(i).getServiceQty()));
                qty = qty + 1;

            }
        }
        txtTotleItemD.setText(sessionManager.getStringData(SessionManager.currency) + totleItem);
        txtTotleItemD.setPaintFlags(txtTotleItemD.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        txtTotleItem.setText(sessionManager.getStringData(SessionManager.currency) + totleItemD);


        totelDisply();


    }

    double totalFinel;
    double tempWallet;
    public void totelDisply() {
        totalFinel = 0;
        tempWallet = 0;
        totalFinel = totleItemD + addontotle;
        double tt;
        if (totleItem >= totleItemD) {
            tt = totleItem - totleItemD;
        } else {
            tt = totleItemD - totleItem;
        }
        if (checkWallet.isChecked()) {
            if (Double.parseDouble(sessionManager.getStringData(SessionManager.wallet)) <= totalFinel) {

                totalFinel =  totalFinel-Double.parseDouble(sessionManager.getStringData(SessionManager.wallet)) ;
            } else {
                tempWallet = Double.parseDouble(sessionManager.getStringData(SessionManager.wallet)) - totalFinel;
                totalFinel = 0;
                checkWallet.setText(sessionManager.getStringData(SessionManager.currency) + tempWallet);

            }
        } else {
            checkWallet.setText(sessionManager.getStringData(SessionManager.currency) + sessionManager.getStringData(SessionManager.wallet));
        }
        Log.e("Totoel", "-->" + totalFinel);
        txtTotalF.setText(sessionManager.getStringData(SessionManager.currency) + totalFinel);
        ftotle.setText(sessionManager.getStringData(SessionManager.currency) + totalFinel);
        txtSaving.setText(getString(R.string.youraacountsave)+" " + sessionManager.getStringData(SessionManager.currency) + tt);
    }

    @OnClick({R.id.lvl_continue})
    public void onClick(View view) {
        if (view.getId() == R.id.lvl_continue) {
            String wallet;
            if (checkWallet.isChecked()) {
                double d = Double.parseDouble(sessionManager.getStringData(SessionManager.wallet)) - tempWallet;
                wallet = String.valueOf(d);
            } else {
                wallet = "0";
            }
            startActivity(new Intent(CartActivity.this, TimeSlotActivity.class)
                    .putExtra("name", name)
                    .putExtra("cid", cid)
                    .putExtra("total", String.valueOf(totalFinel))
                    .putExtra("wallet", wallet)
                    .putParcelableArrayListExtra("slist", list));
        }
    }
}
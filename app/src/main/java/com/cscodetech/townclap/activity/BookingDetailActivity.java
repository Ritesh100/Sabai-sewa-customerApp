package com.cscodetech.townclap.activity;

import static com.cscodetech.townclap.utils.Utility.paymentId;
import static com.cscodetech.townclap.utils.Utility.paymentsucsses;
import static com.cscodetech.townclap.utils.Utility.tragectionID;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cscodetech.townclap.R;
import com.cscodetech.townclap.model.AddOnDataItem;
import com.cscodetech.townclap.model.CustomerorderlistItem;
import com.cscodetech.townclap.model.OrderProductDataItem;
import com.cscodetech.townclap.model.Payment;
import com.cscodetech.townclap.model.PaymentItem;
import com.cscodetech.townclap.model.ResponseMessge;
import com.cscodetech.townclap.model.User;
import com.cscodetech.townclap.retrofit.APIClient;
import com.cscodetech.townclap.retrofit.GetResult;
import com.cscodetech.townclap.utils.CustPrograssbar;
import com.cscodetech.townclap.utils.SessionManager;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

public class BookingDetailActivity extends BasicActivity implements GetResult.MyListener {

    @BindView(R.id.txt_proceed)
    public TextView txtProceed;
    @BindView(R.id.txt_orderid)
    TextView txtOrderid;
    @BindView(R.id.txt_status)
    TextView txtStatus;
    @BindView(R.id.txt_data)
    TextView txtData;
    @BindView(R.id.txt_subtotal)
    TextView txtSubtotal;
    @BindView(R.id.txt_addon)
    TextView txtAddon;
    @BindView(R.id.txt_total)
    TextView txtTotal;
    @BindView(R.id.txt_address)
    TextView txtAddress;
    @BindView(R.id.txt_paymentmethod)
    TextView txtPaymentmethod;
    @BindView(R.id.recycleview_service)
    RecyclerView recycleviewService;
    @BindView(R.id.lvl_addon)
    LinearLayout lvlAddon;
    @BindView(R.id.lvl_wallet)
    LinearLayout lvlWallet;
    @BindView(R.id.txt_wallet)
    TextView txtWallet;
    @BindView(R.id.crd_export)
    CardView crdExport;
    @BindView(R.id.crd_addon)
    CardView crdAddon;
    @BindView(R.id.crd_extra_addon)
    CardView crdExtraAddon;

    @BindView(R.id.img_export)
    ImageView imgExport;
    @BindView(R.id.txt_pname)
    TextView txtPname;
    @BindView(R.id.txt_pmobile)
    TextView txtPmobile;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_price)
    TextView txtPrice;

    CustomerorderlistItem item;
    ArrayList<OrderProductDataItem> orderProductData;
    ArrayList<AddOnDataItem> addOnData;
    SessionManager sessionManager;
    CustPrograssbar custPrograssbar;
    List<PaymentItem> paymentList = new ArrayList<>();
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_detail);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        custPrograssbar = new CustPrograssbar();
        sessionManager = new SessionManager(this);
        user = sessionManager.getUserDetails("");
        item = getIntent().getParcelableExtra("myclass");
        addOnData = getIntent().getParcelableArrayListExtra("addon");
        orderProductData = getIntent().getParcelableArrayListExtra("itemlist");

        txtOrderid.setText("" + item.getOrderId());
        txtStatus.setText("" + item.getOrderStatus());
        txtData.setText("" + item.getOrderDateslot() + " " + item.getOrderTime());
        txtSubtotal.setText(sessionManager.getStringData(SessionManager.currency) + item.getOrderSubTotal());
        txtAddon.setText(sessionManager.getStringData(SessionManager.currency) + item.getAddTotal());
        txtTotal.setText(sessionManager.getStringData(SessionManager.currency) + item.getOrderTotal());

        txtPaymentmethod.setText("" + item.getPMethodName());
        txtAddress.setText("" + item.getCustomerAddress());
        if (item.getWallet().equalsIgnoreCase("0")) {
            lvlWallet.setVisibility(View.GONE);
        } else {
            lvlWallet.setVisibility(View.VISIBLE);
            txtWallet.setText(sessionManager.getStringData(SessionManager.currency) + item.getWallet());
        }
        if (item.getPartnerName() != null && !item.getPartnerName().isEmpty()) {
            crdExport.setVisibility(View.VISIBLE);
            txtPname.setText("" + item.getPartnerName());
            txtPmobile.setText("" + item.getPartnerMobile());
            Glide.with(this).load(APIClient.baseUrl + "/" + item.getPartnerImg()).centerCrop().into(imgExport);

        }

        if (item.getAddDesc() == null || item.getAddDesc().equalsIgnoreCase("")) {
            crdExtraAddon.setVisibility(View.GONE);
        } else {
            getPayment();
            crdExtraAddon.setVisibility(View.VISIBLE);
            txtTitle.setText("" + item.getAddDesc());
            txtPrice.setText(sessionManager.getStringData(SessionManager.currency) + item.getAddPrice());
        }
        if (item.getOrderStatus().equalsIgnoreCase("Completed")) {
            txtProceed.setVisibility(View.GONE);
        }
        setAddonList(lvlAddon, addOnData);
        recycleviewService.setLayoutManager(new LinearLayoutManager(this));
        cartAdapter cartAdapter = new cartAdapter(this, orderProductData);
        recycleviewService.setAdapter(cartAdapter);

    }

    private void getPayment() {
        custPrograssbar.prograssCreate(this);

        JSONObject jsonObject = new JSONObject();
        RequestBody bodyRequest = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        Call<JsonObject> call = APIClient.getInterface().getPaymentList(bodyRequest);
        GetResult getResult = new GetResult();
        getResult.setMyListener(this);
        getResult.callForLogin(call, "2");

    }

    private void orderComplete() {
        custPrograssbar.prograssCreate(this);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid", user.getId());
            jsonObject.put("pay_id", paymentId);
            jsonObject.put("t_id", tragectionID);
            jsonObject.put("oid", item.getOrderId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestBody bodyRequest = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        Call<JsonObject> call = APIClient.getInterface().orderComplete(bodyRequest);
        GetResult getResult = new GetResult();
        getResult.setMyListener(this);
        getResult.callForLogin(call, "3");

    }

    private void setAddonList(LinearLayout lnrView, List<AddOnDataItem> dataList) {
        lnrView.removeAllViews();
        for (int i = 0; i < dataList.size(); i++) {
            if (!dataList.get(i).getPrice().equalsIgnoreCase("0")) {
                LayoutInflater inflater = LayoutInflater.from(this);
                View view = inflater.inflate(R.layout.item_adon, null);
                TextView itemTitle = view.findViewById(R.id.txt_title);
                TextView txtPrice = view.findViewById(R.id.txt_price);
                itemTitle.setText("" + dataList.get(i).getTitle());
                txtPrice.setText(sessionManager.getStringData(SessionManager.currency) + dataList.get(i).getPrice());
                lnrView.addView(view);
            }

        }

    }

    BottomSheetDialog mBottomSheetDialog;

    public void bottonPaymentList() {
        mBottomSheetDialog = new BottomSheetDialog(this);
        View sheetView = getLayoutInflater().inflate(R.layout.custome_payment, null);
        LinearLayout listView = sheetView.findViewById(R.id.lvl_list);
        TextView txtTotal = sheetView.findViewById(R.id.txt_total);

        txtTotal.setText("item total " + sessionManager.getStringData(SessionManager.currency) + item.getAddPrice());
        for (int i = 0; i < paymentList.size(); i++) {
            LayoutInflater inflater = LayoutInflater.from(BookingDetailActivity.this);
            PaymentItem paymentItem = paymentList.get(i);
            View view = inflater.inflate(R.layout.custome_paymentitem, null);
            ImageView imageView = view.findViewById(R.id.img_icon);
            TextView txtTitle = view.findViewById(R.id.txt_title);
            TextView txtSubtitel = view.findViewById(R.id.txt_subtitel);
            txtTitle.setText("" + paymentList.get(i).getmTitle());
            txtSubtitel.setText("" + paymentList.get(i).getSubtitle());
            Glide.with(BookingDetailActivity.this).load(APIClient.baseUrl + "/" + paymentList.get(i).getmImg()).thumbnail(Glide.with(BookingDetailActivity.this).load(R.drawable.ezgifresize)).into(imageView);
            int finalI = i;
            view.setOnClickListener(v -> {
                paymentId = paymentList.get(finalI).getmId();
                try {
                    switch (paymentList.get(finalI).getmTitle()) {
                        case "Razorpay":
                            int temtoal = (int) Math.round(Double.parseDouble(item.getAddPrice()));
                            startActivity(new Intent(this, RazerpayActivity.class).putExtra("amount", temtoal).putExtra("detail", paymentItem));
                            break;
                        case "Paypal":
                            startActivity(new Intent(this, PaypalActivity.class).putExtra("amount", Double.parseDouble(item.getAddPrice())).putExtra("detail", paymentItem));
                            break;
                        case "Stripe":
                            startActivity(new Intent(this, StripPaymentActivity.class).putExtra("amount", Double.parseDouble(item.getAddPrice())).putExtra("detail", paymentItem));
                            break;
                        case "FlutterWave":
                            mBottomSheetDialog.cancel();
                            startActivity(new Intent(this, FlutterwaveActivity.class).putExtra("amount", item.getAddPrice()));
                            break;
                        case "Paytm":
                            mBottomSheetDialog.cancel();
                            startActivity(new Intent(this, PaytmActivity.class).putExtra("amount", item.getAddPrice()));
                            break;
                        case "SenangPay":
                            mBottomSheetDialog.cancel();
                            startActivity(new Intent(this, SenangpayActivity.class).putExtra("amount", item.getAddPrice()).putExtra("detail", paymentItem));
                            break;
                        case "PayStack":
                            mBottomSheetDialog.cancel();
                            startActivity(new Intent(this, PaystackActivity.class).putExtra("amount", item.getAddPrice()).putExtra("detail", paymentItem));
                            break;
                        default:
                            break;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            listView.addView(view);
        }
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();
    }

    @Override
    public void callback(JsonObject result, String callNo) {

        try {
            custPrograssbar.closePrograssBar();
            if (callNo.equalsIgnoreCase("2")) {
                Gson gson = new Gson();
                Payment payment = gson.fromJson(result.toString(), Payment.class);
                for (int i = 0; i < payment.getData().size(); i++) {
                    if (payment.getData().get(i).getwShow().equalsIgnoreCase("1")) {
                        paymentList.add(payment.getData().get(i));
                    }
                }

            } else if (callNo.equalsIgnoreCase("3")) {
                Gson gson = new Gson();
                ResponseMessge responseMessge = gson.fromJson(result.toString(), ResponseMessge.class);
                if (responseMessge.getResult().equalsIgnoreCase("true")) {
                    mBottomSheetDialog.cancel();
                    startActivity(new Intent(BookingDetailActivity.this, HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));


                }

            }

        } catch (Exception e) {
            Log.e("error", "->" + e.toString());
        }
    }

    @OnClick({R.id.txt_proceed})
    void onBindClick(View view) {
        if (view.getId() == R.id.txt_proceed) {
            bottonPaymentList();
        }
    }


    public class cartAdapter extends RecyclerView.Adapter<cartAdapter.CartHolder> {
        private Context context;
        private List<OrderProductDataItem> mBanner;
        SessionManager sessionManager;


        public cartAdapter(Context context, List<OrderProductDataItem> mBanner) {
            this.context = context;
            this.mBanner = mBanner;
            sessionManager = new SessionManager(context);

        }

        @NonNull
        @Override
        public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.adapter_item_service, parent, false);
            return new CartHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CartHolder holder, int position) {
            OrderProductDataItem item = mBanner.get(position);
            holder.txtTitle.setText("" + item.getProductName());
            holder.txtQty.setText(getString(R.string.qty)+" " + item.getProductQuantity());
            double res = (item.getProductPrice() / 100.0f) * item.getProductDiscount();
            double pp = item.getProductPrice() - res;
            holder.txtPriced.setText(sessionManager.getStringData(SessionManager.currency) + new DecimalFormat("##.##").format(pp));
            holder.txtPrice.setText(sessionManager.getStringData(SessionManager.currency) + item.getProductPrice());
            holder.txtPrice.setPaintFlags(holder.txtPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


        }

        @Override
        public int getItemCount() {
            return mBanner.size();

        }

        public class CartHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.txt_title)
            TextView txtTitle;
            @BindView(R.id.txt_qty)
            TextView txtQty;

            @BindView(R.id.txt_price)
            TextView txtPrice;
            @BindView(R.id.txt_priced)
            TextView txtPriced;

            public CartHolder(@NonNull View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (paymentsucsses == 1) {
            paymentsucsses = 0;
            orderComplete();

        }
    }
}
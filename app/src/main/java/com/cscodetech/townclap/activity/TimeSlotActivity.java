package com.cscodetech.townclap.activity;

import static com.cscodetech.townclap.utils.Utility.changeAddress;
import static com.cscodetech.townclap.utils.Utility.paymentId;
import static com.cscodetech.townclap.utils.Utility.tragectionID;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cscodetech.townclap.R;
import com.cscodetech.townclap.locationpick.LocationGetActivity;
import com.cscodetech.townclap.locationpick.MapUtility;
import com.cscodetech.townclap.model.AddOnDataItem;
import com.cscodetech.townclap.model.AddressList;
import com.cscodetech.townclap.model.Payment;
import com.cscodetech.townclap.model.PaymentItem;
import com.cscodetech.townclap.model.ResponseMessge;
import com.cscodetech.townclap.model.ServiceListItem;
import com.cscodetech.townclap.model.Times;
import com.cscodetech.townclap.model.User;
import com.cscodetech.townclap.retrofit.APIClient;
import com.cscodetech.townclap.retrofit.GetResult;
import com.cscodetech.townclap.utils.CustPrograssbar;
import com.cscodetech.townclap.utils.MyDatabase;
import com.cscodetech.townclap.utils.SessionManager;
import com.cscodetech.townclap.utils.Utility;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

public class TimeSlotActivity extends BasicActivity implements GetResult.MyListener {

    @BindView(R.id.txt_type)
    TextView txtType;
    @BindView(R.id.txt_address)
    TextView txtAddress;
    @BindView(R.id.txt_change)
    TextView txtChange;
    @BindView(R.id.recycleview_date)
    RecyclerView recycleviewDate;
    @BindView(R.id.lvl_time)
    androidx.recyclerview.widget.RecyclerView lvlTime;
    @BindView(R.id.lvl_continue)
    LinearLayout lvlContinue;
    @BindView(R.id.txt_totle)
    TextView txtTotal;
    @BindView(R.id.txt_proceed)
    TextView txtProceed;

    @BindView(R.id.lvl_ordersucess)
    LinearLayout lvlOrdersucess;

    @BindView(R.id.lvl_data)
    LinearLayout lvlData;

    @BindView(R.id.txt_complet)
    TextView txtComplet;

    @BindView(R.id.lvl_export)
    LinearLayout lvlExport;
    @BindView(R.id.txt_pname)
    TextView txtPname;
    @BindView(R.id.img_pimage)
    CircleImageView imgPimage;


    String name;
    String cid;
    CustPrograssbar custPrograssbar;
    SessionManager sessionManager;
    User user;
    List<String> timelist;
    List<String> datelist;
    List<PaymentItem> paymentList = new ArrayList<>();
    ArrayList<AddOnDataItem> list;
    MyDatabase myDatabase;
    List<ServiceListItem> serviceList = new ArrayList<>();
    String total;
    String wallet;
    List<AddressList> addressLists = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_slot);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        custPrograssbar = new CustPrograssbar();
        sessionManager = new SessionManager(TimeSlotActivity.this);
        user = sessionManager.getUserDetails("");
        name = getIntent().getStringExtra("name");

        myDatabase = new MyDatabase(TimeSlotActivity.this);
        getSupportActionBar().setTitle(name);
        cid = getIntent().getStringExtra("cid");
        total = getIntent().getStringExtra("total");
        wallet = getIntent().getStringExtra("wallet");
        list = getIntent().getParcelableArrayListExtra("slist");

        txtTotal.setText(sessionManager.getStringData(SessionManager.currency) + total);
        recycleviewDate.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recycleviewDate.setItemAnimator(new DefaultItemAnimator());

        lvlTime.setLayoutManager(new GridLayoutManager(this, 2));
        lvlTime.setItemAnimator(new DefaultItemAnimator());

        if (sessionManager.getStringData("pid").equalsIgnoreCase("0")) {
            lvlExport.setVisibility(View.GONE);
        } else {
            lvlExport.setVisibility(View.VISIBLE);
            txtPname.setText("" + sessionManager.getStringData("pname"));
            Glide.with(this).load(APIClient.baseUrl + "/" + sessionManager.getStringData("pimage")).centerCrop().into(imgPimage);

        }
        getTimeSlot();


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

    private void getTimeSlot() {
        custPrograssbar.prograssCreate(this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid", user.getId());
            jsonObject.put("cid", cid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestBody bodyRequest = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        Call<JsonObject> call = APIClient.getInterface().timeslot(bodyRequest);
        GetResult getResult = new GetResult();
        getResult.setMyListener(this);
        getResult.callForLogin(call, "1");
    }


    private class AsyncTaskRunner extends AsyncTask<String, String, JSONArray> {
        String storeID = "0";

        @Override
        protected JSONArray doInBackground(String... params) {
            JSONArray jsonArray = new JSONArray();
            serviceList = myDatabase.getCData(cid);
            for (int i = 0; i < serviceList.size(); i++) {

                JSONObject jsonObject = new JSONObject();
                try {

                    jsonObject.put("title", serviceList.get(i).getServiceTitle());
                    jsonObject.put("cost", serviceList.get(i).getServicePrice());
                    jsonObject.put("qty", serviceList.get(i).getServiceQty());
                    jsonObject.put("discount", serviceList.get(i).getServiceDiscount());
                    jsonArray.put(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            for (int i = 0; i < list.size(); i++) {
                adonTitle.put(list.get(i).getTitle());
                adonPrice.put(list.get(i).getPrice());
            }
            return jsonArray;
        }


        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            // execution of result of Long time consuming operation
            orderPlace(jsonArray);
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onProgressUpdate(String... text) {

        }
    }

    JSONArray adonTitle = new JSONArray();
    JSONArray adonPrice = new JSONArray();

    public void orderPlace(JSONArray jsonArray) {
        custPrograssbar.prograssCreate(TimeSlotActivity.this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid", user.getId());
            jsonObject.put("cid", cid);
            jsonObject.put("p_method_id", paymentId);
            jsonObject.put("full_address", addressLists.get(sessionManager.getIntData("position")).getAddress());
            jsonObject.put("lat", addressLists.get(sessionManager.getIntData("position")).getLatMap());
            jsonObject.put("longs", addressLists.get(sessionManager.getIntData("position")).getLongMap());
            jsonObject.put("transaction_id", tragectionID);
            jsonObject.put("time", timelist.get(selectedTime));
            jsonObject.put("date", datelist.get(selectedDate));
            jsonObject.put("product_total", total);
            jsonObject.put("wal_amt", wallet);
            jsonObject.put("add_on", adonTitle);
            jsonObject.put("add_per_price", adonPrice);
            jsonObject.put("pid", sessionManager.getStringData("pid"));
            jsonObject.put("ProductData", jsonArray);
            RequestBody bodyRequest = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
            Call<JsonObject> call = APIClient.getInterface().getOrderNow(bodyRequest);
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.callForLogin(call, "3");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    Times times;

    @Override
    public void callback(JsonObject result, String callNo) {
        try {
            custPrograssbar.closePrograssBar();
            if (callNo.equalsIgnoreCase("1")) {
                Gson gson = new Gson();
                times = gson.fromJson(result.toString(), Times.class);
                if (times.getResult().equalsIgnoreCase("true")) {

                    if (times.getAddressData().isEmpty()) {
                        startActivity(new Intent(TimeSlotActivity.this, LocationGetActivity.class)
                                .putExtra(MapUtility.latitude, 0.0)
                                .putExtra(MapUtility.longitude, 0.0)
                                .putExtra("atype", "Home")
                                .putExtra("newuser", "curruntlat")
                                .putExtra("userid", user.getId())
                                .putExtra("aid", "0"));

                    } else {
                        addressLists = times.getAddressData();
                        int p = sessionManager.getIntData("position");
                        sessionManager.setStringData(SessionManager.address, times.getAddressData().get(p).getAddress());
                        txtType.setText("" + times.getAddressData().get(p).getType());
                        txtAddress.setText("" + times.getAddressData().get(p).getHouseno() + "," + times.getAddressData().get(p).getLandmark() + "," + times.getAddressData().get(p).getAddress());
                    }
                    timelist = times.getTimeslotData().get(0).getTslot();
                    TimeAdapter timeAdapter;
                    datelist = times.getTimeslotData().get(0).getDays();

                    if (times.getTimeslotData().get(0).getStatus().equalsIgnoreCase("current")) {
                        try {
                            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm aa");
                            Calendar calendar1 = Calendar.getInstance();
                            String currentDate = sdf.format(calendar1.getTime());
                            List<String> list = new ArrayList<>();
                            for (int i = 0; i < timelist.size(); i++) {
                                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                                String formattedDate = df.format(calendar1.getTime());
                                String datadb = formattedDate + " " + timelist.get(i);
                                Date date1 = sdf.parse(currentDate);
                                Date date2 = sdf.parse(datadb);
                                if (date1.before(date2)) {
                                    list.add(timelist.get(i));
                                }
                            }
                            timeAdapter = new TimeAdapter(list);
                            lvlTime.setAdapter(timeAdapter);

                        } catch (Exception e) {
                            Log.e("Error", "" + e.toString());

                        }
                    } else {
                        timeAdapter = new TimeAdapter(timelist);
                        lvlTime.setAdapter(timeAdapter);
                    }

                    DateAdapter dateAdapter = new DateAdapter(times.getTimeslotData().get(0).getDays());
                    recycleviewDate.setAdapter(dateAdapter);
                    getPayment();
                }


            } else if (callNo.equalsIgnoreCase("2")) {
                Gson gson = new Gson();
                Payment payment = gson.fromJson(result.toString(), Payment.class);
                paymentList = payment.getData();
            } else if (callNo.equalsIgnoreCase("3")) {
                Gson gson = new Gson();
                ResponseMessge responseMessge = gson.fromJson(result.toString(), ResponseMessge.class);
                if (responseMessge.getResult().equalsIgnoreCase("true")) {
                    myDatabase.deleteCard(cid);
                    lvlOrdersucess.setVisibility(View.VISIBLE);
                    lvlData.setVisibility(View.GONE);
                    sessionManager.setStringData(SessionManager.wallet, responseMessge.getWallet());
                    mBottomSheetDialog.cancel();

                }


            }
        } catch (Exception e) {
            Log.e("Error", "--->" + e.toString());

        }
    }

    int selectedDate = 0;
    int selectedTime = 0;


    public class DateAdapter extends RecyclerView.Adapter<DateAdapter.MyViewHolder> {

        private List<String> mCatlist;


        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView title;

            public MyViewHolder(View view) {
                super(view);
                title = (TextView) view.findViewById(R.id.txt_date);
            }
        }

        public DateAdapter(List<String> mCatlist) {
            selectedDate = 0;
            this.mCatlist = mCatlist;
        }

        @Override
        public DateAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView;
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.date_item, parent, false);
            return new DateAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final DateAdapter.MyViewHolder holder, int position) {
            holder.title.setText(mCatlist.get(position).substring(0, 3) + "\n" + mCatlist.get(position).substring(4, 6));
            if (selectedDate == position) {
                holder.title.setBackground(getResources().getDrawable(R.drawable.border_border));
                holder.title.setTextColor(getResources().getColor(R.color.colorAccent));
            } else {
                holder.title.setBackground(getResources().getDrawable(R.drawable.border));
                holder.title.setTextColor(getResources().getColor(R.color.colorGray));

            }

            holder.title.setOnClickListener(v -> {
                selectedDate = position;
                if (times.getTimeslotData().get(0).getStatus().equalsIgnoreCase("current") && position == 0) {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm aa");
                        Calendar calendar1 = Calendar.getInstance();
                        String currentDate = sdf.format(calendar1.getTime());
                        List<String> list = new ArrayList<>();
                        for (int i = 0; i < timelist.size(); i++) {
                            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                            String formattedDate = df.format(calendar1.getTime());
                            String datadb = formattedDate + " " + timelist.get(i);
                            Date date1 = sdf.parse(currentDate);
                            Date date2 = sdf.parse(datadb);

                            if (date1.before(date2)) {
                                list.add(timelist.get(i));
                            }
                        }
                        TimeAdapter adapter = new TimeAdapter(list);
                        lvlTime.setAdapter(adapter);

                    } catch (Exception e) {
                        Log.e("Error", "" + e.toString());

                    }
                } else {
                    TimeAdapter adapter = new TimeAdapter(timelist);
                    lvlTime.setAdapter(adapter);
                }


                notifyDataSetChanged();
            });

        }

        @Override
        public int getItemCount() {
            return mCatlist.size();
        }
    }


    public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.MyViewHolder> {

        private List<String> mCatlist;


        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView title;

            public MyViewHolder(View view) {
                super(view);
                title = (TextView) view.findViewById(R.id.txt_date);
            }
        }

        public TimeAdapter(List<String> mCatlist) {
            selectedTime = 0;
            this.mCatlist = mCatlist;
        }

        @Override
        public TimeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView;
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.time_item, parent, false);
            return new TimeAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final TimeAdapter.MyViewHolder holder, int position) {
            holder.title.setText(mCatlist.get(position) + "");
            if (selectedTime == position) {
                holder.title.setBackground(getResources().getDrawable(R.drawable.border_border));
                holder.title.setTextColor(getResources().getColor(R.color.colorAccent));
            } else {
                holder.title.setBackground(getResources().getDrawable(R.drawable.border));
                holder.title.setTextColor(getResources().getColor(R.color.colorGray));

            }


            holder.title.setOnClickListener(v -> {
                selectedTime = position;
                notifyDataSetChanged();
            });

        }

        @Override
        public int getItemCount() {
            return mCatlist.size();
        }
    }

    @OnClick({R.id.txt_change, R.id.txt_proceed, R.id.txt_complet})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.txt_change:
                startActivity(new Intent(TimeSlotActivity.this, AddressActivity.class));
                break;
            case R.id.txt_proceed:
                if (0 == Double.parseDouble(total)) {
                    paymentId = "5";
                    new AsyncTaskRunner().execute("");
                } else {
                    bottonPaymentList();
                }

                break;
            case R.id.txt_complet:
                startActivity(new Intent(this, BookingActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sessionManager == null) {
            return;
        }

        if (AddressActivity.getInstance() != null && changeAddress) {
            changeAddress = false;
            getTimeSlot();

        }
        if (Utility.paymentsucsses == 1) {
            Utility.paymentsucsses = 0;
            new AsyncTaskRunner().execute("");
        }
    }

    BottomSheetDialog mBottomSheetDialog;

    public void bottonPaymentList() {
        mBottomSheetDialog = new BottomSheetDialog(this);
        View sheetView = getLayoutInflater().inflate(R.layout.custome_payment, null);
        LinearLayout listView = sheetView.findViewById(R.id.lvl_list);
        TextView txtTotal = sheetView.findViewById(R.id.txt_total);
        txtTotal.setText("item total " + sessionManager.getStringData(SessionManager.currency) + total);
        for (int i = 0; i < paymentList.size(); i++) {
            LayoutInflater inflater = LayoutInflater.from(TimeSlotActivity.this);
            PaymentItem paymentItem = paymentList.get(i);
            View view = inflater.inflate(R.layout.custome_paymentitem, null);
            ImageView imageView = view.findViewById(R.id.img_icon);
            TextView txtTitle = view.findViewById(R.id.txt_title);
            TextView txtSubtitel = view.findViewById(R.id.txt_subtitel);
            txtTitle.setText("" + paymentList.get(i).getmTitle());
            txtSubtitel.setText("" + paymentList.get(i).getSubtitle());
            Glide.with(TimeSlotActivity.this).load(APIClient.baseUrl + "/" + paymentList.get(i).getmImg()).thumbnail(Glide.with(TimeSlotActivity.this).load(R.drawable.ezgifresize)).into(imageView);
            int finalI = i;
            view.setOnClickListener(v -> {
                paymentId = paymentList.get(finalI).getmId();
                try {
                    switch (paymentList.get(finalI).getmTitle()) {
                        case "Razorpay":
                            int temtoal = (int) Math.round(Double.parseDouble(total));
                            startActivity(new Intent(TimeSlotActivity.this, RazerpayActivity.class).putExtra("amount", temtoal).putExtra("detail", paymentItem));
                            break;
                        case "Cash On Delivery":
                            new AsyncTaskRunner().execute("");
                            break;
                        case "Paypal":
                            startActivity(new Intent(TimeSlotActivity.this, PaypalActivity.class).putExtra("amount", Double.parseDouble(total)).putExtra("detail", paymentItem));
                            break;
                        case "Stripe":
                            startActivity(new Intent(TimeSlotActivity.this, StripPaymentActivity.class).putExtra("amount", Double.parseDouble(total)).putExtra("detail", paymentItem));
                            break;
                        case "FlutterWave":
                            mBottomSheetDialog.cancel();
                            startActivity(new Intent(this, FlutterwaveActivity.class).putExtra("amount", total));
                            break;
                        case "Paytm":
                            mBottomSheetDialog.cancel();
                            startActivity(new Intent(this, PaytmActivity.class).putExtra("amount", total));
                            break;
                        case "SenangPay":
                            mBottomSheetDialog.cancel();
                            startActivity(new Intent(this, SenangpayActivity.class).putExtra("amount", total).putExtra("detail", paymentItem));
                            break;
                        case "PayStack":
                            mBottomSheetDialog.cancel();
                            startActivity(new Intent(this, PaystackActivity.class).putExtra("amount", total).putExtra("detail", paymentItem));
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
}
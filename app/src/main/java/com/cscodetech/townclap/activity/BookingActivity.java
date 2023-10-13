package com.cscodetech.townclap.activity;

import static com.cscodetech.townclap.utils.Utility.resedual;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.cscodetech.townclap.R;
import com.cscodetech.townclap.model.CustomerorderlistItem;
import com.cscodetech.townclap.model.Order;
import com.cscodetech.townclap.model.ResponseMessge;
import com.cscodetech.townclap.model.User;
import com.cscodetech.townclap.retrofit.APIClient;
import com.cscodetech.townclap.retrofit.GetResult;
import com.cscodetech.townclap.utils.CustPrograssbar;
import com.cscodetech.townclap.utils.SessionManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;


public class BookingActivity extends BasicActivity implements GetResult.MyListener {


    @BindView(R.id.my_recycler_view)
    RecyclerView myRecyclerView;
    StaggeredGridLayoutManager gridLayoutManager;
    SessionManager sessionManager;
    User user;
    CustPrograssbar custPrograssbar;
    @BindView(R.id.txt_notfount)
    TextView txtNotFount;
    @BindView(R.id.lvl_notfound)
    LinearLayout lvlNotfound;

    ItemAdp itemAdp;
    List<CustomerorderlistItem> orderHistories = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        ButterKnife.bind(this);
        getSupportActionBar().setTitle(getResources().getString(R.string.menu2));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        custPrograssbar = new CustPrograssbar();
        sessionManager = new SessionManager(BookingActivity.this);
        user = sessionManager.getUserDetails("");
        gridLayoutManager = new StaggeredGridLayoutManager(1, 1);
        myRecyclerView.setLayoutManager(gridLayoutManager);
        itemAdp = new ItemAdp(BookingActivity.this, new ArrayList<>());
        myRecyclerView.setAdapter(itemAdp);

        getOrder();
    }


    private void getOrder() {
        custPrograssbar.prograssCreate(BookingActivity.this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid", user.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestBody bodyRequest = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        Call<JsonObject> call = APIClient.getInterface().getOrder(bodyRequest);
        GetResult getResult = new GetResult();
        getResult.setMyListener(this);
        getResult.callForLogin(call, "1");

    }

    public void getCancel(String oid) {
        custPrograssbar.prograssCreate(BookingActivity.this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid", user.getId());
            jsonObject.put("oid", oid);
            RequestBody bodyRequest = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
            Call<JsonObject> call = APIClient.getInterface().orderCancle(bodyRequest);
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.callForLogin(call, "2");
        } catch (Exception e) {
            e.printStackTrace();

        }


    }

    @Override
    public void callback(JsonObject result, String callNo) {
        try {
            custPrograssbar.closePrograssBar();
            if (callNo.equalsIgnoreCase("1")) {
                Gson gson = new Gson();
                Order orderH = gson.fromJson(result.toString(), Order.class);
                if (orderH.getResult().equalsIgnoreCase("true")) {
                    orderHistories = orderH.getCustomerorderlist();

                    if (!orderHistories.isEmpty()) {
                        lvlNotfound.setVisibility(View.GONE);
                        myRecyclerView.setVisibility(View.VISIBLE);
                        gridLayoutManager = new StaggeredGridLayoutManager(1, 1);
                        myRecyclerView.setLayoutManager(gridLayoutManager);
                        itemAdp = new ItemAdp(BookingActivity.this, orderHistories);
                        myRecyclerView.setAdapter(itemAdp);
                    } else {
                        lvlNotfound.setVisibility(View.VISIBLE);
                        myRecyclerView.setVisibility(View.GONE);
                    }
                }else {
                    lvlNotfound.setVisibility(View.VISIBLE);
                    myRecyclerView.setVisibility(View.GONE);
                }
            } else if (callNo.equalsIgnoreCase("2")) {
                Gson gson1 = new Gson();
                ResponseMessge messge = gson1.fromJson(result.toString(), ResponseMessge.class);
                Toast.makeText(BookingActivity.this, messge.getResponseMsg(), Toast.LENGTH_LONG).show();
                if (messge.getResult().equalsIgnoreCase("true")) {
                    finish();
                    itemAdp.notifyDataSetChanged();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }


    public class ItemAdp extends RecyclerView.Adapter<ItemAdp.ViewHolder> {


        private LayoutInflater mInflater;
        Context mContext;
        List<CustomerorderlistItem> lists;

        public ItemAdp(Context context, List<CustomerorderlistItem> s) {
            this.mInflater = LayoutInflater.from(context);
            this.lists = s;
            this.mContext = context;
            WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.custome_booking_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int i) {
            CustomerorderlistItem item = lists.get(i);
            holder.txtBooking.setText("" + item.getOrderId());
            holder.txtTotal.setText(sessionManager.getStringData(SessionManager.currency) + item.getOrderTotal());
            holder.txtBookingstatus.setText("" + item.getOrderStatus());
            holder.txtBookingdate.setText("" + item.getOrderDateslot() + " " + item.getOrderTime());
            holder.txtAddres.setText("" + item.getCustomerAddress());

            if (item.getOrderStatus().equalsIgnoreCase("Pending")) {
                holder.imgResedul.setVisibility(View.VISIBLE);
                holder.imgCencel.setVisibility(View.VISIBLE);
            } else {
                holder.imgResedul.setVisibility(View.GONE);
                holder.imgCencel.setVisibility(View.GONE);
            }
            holder.imgResedul.setOnClickListener(v -> startActivity(new Intent(BookingActivity.this, ResedulActivity.class)
                    .putExtra("cid", item.getCategoryId())
                    .putExtra("oid", item.getOrderId())));
            holder.imgView.setOnClickListener(v -> startActivity(new Intent(BookingActivity.this, BookingDetailActivity.class)
                    .putExtra("myclass", item)
                    .putParcelableArrayListExtra("addon", item.getAddOnData())
                    .putExtra("itemlist", item.getOrderProductData())));
            holder.imgCencel.setOnClickListener(v -> {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(BookingActivity.this);
                alertDialogBuilder.setMessage(getResources().getString(R.string.deletemesseg));
                alertDialogBuilder.setPositiveButton(R.string.yes,
                        (arg0, arg1) -> {
                            item.setOrderStatus("Cancel");
                            getCancel(item.getOrderId());
                        });

                alertDialogBuilder.setNegativeButton(R.string.noa, (dialog, which) -> finish());
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            });
        }

        @Override
        public int getItemCount() {
            return lists.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.txt_booking)
            TextView txtBooking;
            @BindView(R.id.txt_total)
            TextView txtTotal;
            @BindView(R.id.txt_bookingstatus)
            TextView txtBookingstatus;
            @BindView(R.id.txt_bookingdate)
            TextView txtBookingdate;
            @BindView(R.id.txt_addres)
            TextView txtAddres;
            @BindView(R.id.img_view)
            LinearLayout imgView;
            @BindView(R.id.img_resedul)
            LinearLayout imgResedul;
            @BindView(R.id.img_cencel)
            LinearLayout imgCencel;

            ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

        }


    }

    protected Boolean isActivityRunning(Class activityClass) {
        ActivityManager activityManager = (ActivityManager) getBaseContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = activityManager.getRunningTasks(Integer.MAX_VALUE);

        for (ActivityManager.RunningTaskInfo task : tasks) {
            if (activityClass.getCanonicalName().equalsIgnoreCase(task.baseActivity.getClassName()))
                return true;
        }

        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (!isActivityRunning(HomeActivity.class)) {
                startActivity(new Intent(BookingActivity.this, HomeActivity.class));
                finish();
            } else {
                finish();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(resedual==1){
            resedual=0;
            getOrder();
        }
    }
}
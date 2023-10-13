package com.cscodetech.townclap.activity;

import static com.cscodetech.townclap.utils.SessionManager.pincode;
import static com.cscodetech.townclap.utils.SessionManager.pincoded;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cscodetech.townclap.R;
import com.cscodetech.townclap.locationpick.LocationGetActivity;
import com.cscodetech.townclap.locationpick.MapUtility;
import com.cscodetech.townclap.model.Address;
import com.cscodetech.townclap.model.AddressList;
import com.cscodetech.townclap.model.User;
import com.cscodetech.townclap.retrofit.APIClient;
import com.cscodetech.townclap.retrofit.GetResult;
import com.cscodetech.townclap.utils.CustPrograssbar;
import com.cscodetech.townclap.utils.SessionManager;
import com.cscodetech.townclap.utils.Utility;
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


public class AddressActivity extends BasicActivity implements GetResult.MyListener {


    @BindView(R.id.lvl_address)
    LinearLayout lvlAddress;


    @BindView(R.id.lvl_myaddress)
    LinearLayout lvlMyAddress;


    SessionManager sessionManager;
    User user;

    @BindView(R.id.my_recycler_view)
    RecyclerView myRecyclerView;

    LinearLayoutManager layoutManager;

    CustPrograssbar custPrograssbar;
    public static AddressActivity activity = null;

    public static AddressActivity getInstance() {
        return activity;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);
        activity = this;
        custPrograssbar = new CustPrograssbar();
        sessionManager = new SessionManager(AddressActivity.this);
        user = sessionManager.getUserDetails("");
        layoutManager = new LinearLayoutManager(AddressActivity.this, LinearLayoutManager.VERTICAL, false);
        myRecyclerView.setLayoutManager(layoutManager);
        requestPermissions(new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        final LocationManager manager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER) && Utility.hasGPSDevice(AddressActivity.this)) {
            Toast.makeText(AddressActivity.this, "Gps not enabled", Toast.LENGTH_SHORT).show();
            Utility.enableLoc(AddressActivity.this);
        }


        getAddress();
    }

    private void getAddress() {
        custPrograssbar.prograssCreate(AddressActivity.this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid", user.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestBody bodyRequest = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        Call<JsonObject> call = APIClient.getInterface().getAddress(bodyRequest);
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
                Address address = gson.fromJson(result.toString(), Address.class);
                if (address.getResult().equalsIgnoreCase("true")) {
                    lvlMyAddress.setVisibility(View.VISIBLE);
                    if(address.getAddressList().size()<=2){
                        lvlAddress.setVisibility(View.VISIBLE);
                    }else {
                        lvlAddress.setVisibility(View.GONE);
                    }
                    AdepterAddress adepterAddress = new AdepterAddress(AddressActivity.this, address.getAddressList());
                    myRecyclerView.setAdapter(adepterAddress);
                } else {
                    lvlMyAddress.setVisibility(View.GONE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.lvl_address, R.id.lvl_myaddress})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lvl_address:

                startActivity(new Intent(AddressActivity.this, LocationGetActivity.class)
                        .putExtra(MapUtility.latitude, 0.0)
                        .putExtra(MapUtility.longitude, 0.0)
//                        .putExtra("landmark","")
//                        .putExtra("hno", "")
                        .putExtra("atype", "Home")
                        .putExtra("newuser", "curruntlat")
                        .putExtra("userid", user.getId())
                        .putExtra("aid", "0"));
                break;
            case R.id.lvl_myaddress:
                break;


            default:
                break;
        }
    }


    public class AdepterAddress extends RecyclerView.Adapter<AdepterAddress.BannerHolder> {
        private Context context;
        private List<AddressList> mBanner;

        public AdepterAddress(Context context, List<AddressList> mBanner) {
            this.context = context;
            this.mBanner = mBanner;
        }

        @NonNull
        @Override
        public BannerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.adapter_addresss_item, parent, false);
            return new BannerHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull BannerHolder holder, int position) {

            holder.txtType.setText("" + mBanner.get(position).getType());
            holder.txtHomeaddress.setText(mBanner.get(position).getHouseno() + mBanner.get(position).getLandmark() + "," + mBanner.get(position).getAddress());
            holder.lvlHome.setOnClickListener(v -> {

            });
            holder.imgMenu.setOnClickListener(v -> {
                PopupMenu popup = new PopupMenu(context, holder.imgMenu);
                popup.inflate(R.menu.address_menu);
                popup.setOnMenuItemClickListener(item -> {
                    switch (item.getItemId()) {
                        case R.id.menu_select:
                            Utility.changeAddress = true;
                            sessionManager.setIntData("position", position);
                            sessionManager.setStringData(pincoded, mBanner.get(position).getAddress());
                            finish();
                            break;
                        case R.id.menu_edit:
                            startActivity(new Intent(AddressActivity.this, LocationGetActivity.class)
                                    .putExtra(MapUtility.latitude,Double.parseDouble( mBanner.get(position).getLatMap()))
                                    .putExtra(MapUtility.longitude, Double.parseDouble(mBanner.get(position).getLongMap()))
                                    .putExtra("atype", mBanner.get(position).getType())
                                    .putExtra("landmark", mBanner.get(position).getLandmark())
                                    .putExtra("hno", mBanner.get(position).getHouseno())
                                    .putExtra("newuser", "update")
                                    .putExtra("userid", user.getId())
                                    .putExtra("aid", "0"));

                            break;
                        default:
                            break;
                    }
                    return false;
                });
                popup.show();
            });

        }

        @Override
        public int getItemCount() {
            return mBanner.size();
        }

        public class BannerHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.img_menu)
            ImageView imgMenu;
            @BindView(R.id.txt_homeaddress)
            TextView txtHomeaddress;
            @BindView(R.id.txt_tital)
            TextView txtType;
            @BindView(R.id.lvl_home)
            LinearLayout lvlHome;

            public BannerHolder(@NonNull View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (sessionManager != null) {
            getAddress();
        }
    }

    @Override
    public void onBackPressed() {
        if (!sessionManager.getStringData(pincode).equalsIgnoreCase("")) {
            super.onBackPressed();
        }
    }
}
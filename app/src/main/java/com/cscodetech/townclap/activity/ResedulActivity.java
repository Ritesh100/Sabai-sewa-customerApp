package com.cscodetech.townclap.activity;

import static com.cscodetech.townclap.utils.Utility.resedual;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cscodetech.townclap.R;
import com.cscodetech.townclap.model.AddressList;
import com.cscodetech.townclap.model.ResponseMessge;
import com.cscodetech.townclap.model.Times;
import com.cscodetech.townclap.model.User;
import com.cscodetech.townclap.retrofit.APIClient;
import com.cscodetech.townclap.retrofit.GetResult;
import com.cscodetech.townclap.utils.CustPrograssbar;
import com.cscodetech.townclap.utils.SessionManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

public class ResedulActivity extends BasicActivity implements GetResult.MyListener {
    @BindView(R.id.recycleview_date)
    RecyclerView recycleviewDate;
    @BindView(R.id.lvl_time)
    RecyclerView lvlTime;
    @BindView(R.id.txt_update)
    TextView txtUpdate;
    CustPrograssbar custPrograssbar;
    SessionManager sessionManager;
    User user;
    int selectedDate = 0;
    int selectedTime = 0;

    List<String> timelist;
    List<String> datelist;
    List<AddressList> addressLists = new ArrayList<>();
    String oid;
    String cid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resedul);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        oid = getIntent().getStringExtra("oid");
        cid = getIntent().getStringExtra("cid");
        sessionManager = new SessionManager(ResedulActivity.this);
        user = sessionManager.getUserDetails("");
        custPrograssbar = new CustPrograssbar();
        recycleviewDate.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recycleviewDate.setItemAnimator(new DefaultItemAnimator());

        lvlTime.setLayoutManager(new GridLayoutManager(this, 2));
        lvlTime.setItemAnimator(new DefaultItemAnimator());
        getTimeSlot();

    }

    private void getTimeSlot() {
        custPrograssbar.prograssCreate(ResedulActivity.this);
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

    private void getUpdate() {
        custPrograssbar.prograssCreate(ResedulActivity.this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid", user.getId());
            jsonObject.put("oid", oid);
            jsonObject.put("time", timelist.get(selectedTime));
            jsonObject.put("date", datelist.get(selectedDate));
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestBody bodyRequest = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        Call<JsonObject> call = APIClient.getInterface().reschedule(bodyRequest);
        GetResult getResult = new GetResult();
        getResult.setMyListener(this);
        getResult.callForLogin(call, "2");


    }

    @Override
    public void callback(JsonObject result, String callNo) {
        try {
            custPrograssbar.closePrograssBar();
            if (callNo.equalsIgnoreCase("1")) {
                Gson gson = new Gson();
                Times times = gson.fromJson(result.toString(), Times.class);
                if (times.getResult().equalsIgnoreCase("true")) {

                    timelist = times.getTimeslotData().get(0).getTslot();
                    ResedulActivity.TimeAdapter timeAdapter;
                    datelist = times.getTimeslotData().get(0).getDays();

                    if (selectedDate == 0) {
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
                            timeAdapter = new ResedulActivity.TimeAdapter(list);
                            lvlTime.setAdapter(timeAdapter);

                        } catch (Exception e) {
                            Log.e("Error-->", "" + e.toString());
                        }
                    } else {
                        timeAdapter = new ResedulActivity.TimeAdapter(timelist);
                        lvlTime.setAdapter(timeAdapter);
                    }

                    ResedulActivity.DateAdapter dateAdapter = new ResedulActivity.DateAdapter(times.getTimeslotData().get(0).getDays());
                    recycleviewDate.setAdapter(dateAdapter);

                }


            } else if (callNo.equalsIgnoreCase("2")) {
                Gson gson1 = new Gson();
                ResponseMessge messge = gson1.fromJson(result.toString(), ResponseMessge.class);
                Toast.makeText(ResedulActivity.this, messge.getResponseMsg(), Toast.LENGTH_LONG).show();
                if (messge.getResult().equalsIgnoreCase("true")) {
                    resedual=1;
                    finish();
                }
            }
        } catch (Exception e) {
            Log.e("Error", "-->" + e.toString());
        }
    }

    public class DateAdapter extends RecyclerView.Adapter<ResedulActivity.DateAdapter.MyViewHolder> {

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
        public ResedulActivity.DateAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView;
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.date_item, parent, false);
            return new ResedulActivity.DateAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final ResedulActivity.DateAdapter.MyViewHolder holder, int position) {
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
                if (selectedDate == 0) {
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
                        Log.e("error", "-->" + e.toString());
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


    public class TimeAdapter extends RecyclerView.Adapter<ResedulActivity.TimeAdapter.MyViewHolder> {

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
        public ResedulActivity.TimeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView;
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.time_item, parent, false);
            return new ResedulActivity.TimeAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final ResedulActivity.TimeAdapter.MyViewHolder holder, int position) {
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

    @OnClick({R.id.txt_update})
    public void onClick(View view) {
        if (view.getId() == R.id.txt_update) {
            getUpdate();
        }
    }
}
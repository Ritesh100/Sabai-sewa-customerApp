package com.cscodetech.townclap.activity;

import static com.cscodetech.townclap.utils.SessionManager.login;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cscodetech.townclap.R;
import com.cscodetech.townclap.model.LoginUser;
import com.cscodetech.townclap.model.User;
import com.cscodetech.townclap.retrofit.APIClient;
import com.cscodetech.townclap.retrofit.GetResult;
import com.cscodetech.townclap.utils.CustPrograssbar;
import com.cscodetech.townclap.utils.SessionManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

public class EditProfileActivity extends BasicActivity implements GetResult.MyListener {

    @BindView(R.id.ed_firstname)
    EditText edFirstName;

    @BindView(R.id.ed_email)
    TextView edEmail;
    @BindView(R.id.ed_password)
    EditText edPassword;
    @BindView(R.id.btn_countinue)
    TextView btuContinues;

    SessionManager sessionManager;
    User user;
    @BindView(R.id.ed_mobile)
    TextView edMobile;

    CustPrograssbar custPrograssbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        custPrograssbar = new CustPrograssbar();
        sessionManager = new SessionManager(EditProfileActivity.this);
        user = sessionManager.getUserDetails("");
        edFirstName.setText(user.getName());

        edMobile.setText(user.getMobile());
        edEmail.setText(user.getEmail());
        edPassword.setText(user.getPassword());
    }


    public boolean validation() {
        if (edFirstName.getText().toString().isEmpty()) {
            edFirstName.setError("Enter First Name");
            return false;
        }

        if (edPassword.getText().toString().isEmpty()) {
            edPassword.setError("Enter Password");
            return false;
        }
        return true;
    }

    private void updateUser() {
        custPrograssbar.prograssCreate(EditProfileActivity.this);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid", user.getId());
            jsonObject.put("name", edFirstName.getText().toString());
            jsonObject.put("password", edPassword.getText().toString());
            RequestBody bodyRequest = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
            Call<JsonObject> call = APIClient.getInterface().getProfile(bodyRequest);
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.callForLogin(call, "1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void callback(JsonObject result, String callNo) {
        try {
            custPrograssbar.closePrograssBar();
            if (callNo.equalsIgnoreCase("1")) {
                Gson gson = new Gson();
                LoginUser loginUser = gson.fromJson(result.toString(), LoginUser.class);
                Toast.makeText(EditProfileActivity.this, loginUser.getResponseMsg(), Toast.LENGTH_LONG).show();
                if (loginUser.getResult().equalsIgnoreCase("true")) {
                    sessionManager.setUserDetails("", loginUser.getUser());
                    sessionManager.setBooleanData(login, true);
                    finish();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({ R.id.btn_countinue})
    public void onClick(View view) {
        if (view.getId() == R.id.btn_countinue) if (validation()) {
            updateUser();
        }
    }
}
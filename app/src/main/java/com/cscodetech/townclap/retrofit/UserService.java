package com.cscodetech.townclap.retrofit;


import com.google.gson.JsonObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {

    @POST(APIClient.APPEND_URL + "s_reg_user.php")
    Call<JsonObject> createUser(@Body RequestBody requestBody);

    @POST(APIClient.APPEND_URL + "s_user_login.php")
    Call<JsonObject> loginUser(@Body RequestBody requestBody);

    @POST(APIClient.APPEND_URL + "s_home_data.php")
    Call<JsonObject> getHome(@Body RequestBody requestBody);

    @POST(APIClient.APPEND_URL + "s_sub_list.php")
    Call<JsonObject> sSubList(@Body RequestBody requestBody);

    @POST(APIClient.APPEND_URL + "s_child_list.php")
    Call<JsonObject> sChildList(@Body RequestBody requestBody);

    @POST(APIClient.APPEND_URL + "s_service_list.php")
    Call<JsonObject> serviceList(@Body RequestBody requestBody);

        @POST(APIClient.APPEND_URL + "list_addon.php")
    Call<JsonObject> listaddon(@Body RequestBody requestBody);

    @POST(APIClient.APPEND_URL + "s_timeslot.php")
    Call<JsonObject> timeslot(@Body RequestBody requestBody);

    @POST(APIClient.APPEND_URL + "s_reschedule.php")
    Call<JsonObject> reschedule(@Body RequestBody requestBody);

    @POST(APIClient.APPEND_URL + "s_order_cancle.php")
    Call<JsonObject> orderCancle(@Body RequestBody requestBody);

    @POST(APIClient.APPEND_URL + "s_country_code.php")
    Call<JsonObject> getCodelist(@Body RequestBody requestBody);

    @POST(APIClient.APPEND_URL + "s_address_user.php")
    Call<JsonObject> setAddress(@Body RequestBody requestBody);

    @POST(APIClient.APPEND_URL + "s_address_list.php")
    Call<JsonObject> getAddress(@Body RequestBody requestBody);

    @POST(APIClient.APPEND_URL + "s_paymentgateway.php")
    Call<JsonObject> getPaymentList(@Body RequestBody requestBody);

    @POST(APIClient.APPEND_URL + "s_order_now.php")
    Call<JsonObject> getOrderNow(@Body RequestBody requestBody);

    @POST(APIClient.APPEND_URL + "s_notification_list.php")
    Call<JsonObject> getNote(@Body RequestBody requestBody);

    @POST(APIClient.APPEND_URL + "s_wallet_report.php")
    Call<JsonObject> getWallet(@Body RequestBody requestBody);

    @POST(APIClient.APPEND_URL + "s_wallet_report.php")
    Call<JsonObject> getDta(@Body RequestBody requestBody);

    @POST(APIClient.APPEND_URL + "s_order_list.php")
    Call<JsonObject> getOrder(@Body RequestBody requestBody);

    @POST(APIClient.APPEND_URL + "order_complete.php")
    Call<JsonObject> orderComplete(@Body RequestBody requestBody)
            ;

    @POST(APIClient.APPEND_URL + "s_child_search.php")
    Call<JsonObject> getSearch(@Body RequestBody requestBody);

    @POST(APIClient.APPEND_URL + "getdata.php")
    Call<JsonObject> getRefercode(@Body RequestBody requestBody);

    @POST(APIClient.APPEND_URL + "s_profile.php")
    Call<JsonObject> getProfile(@Body RequestBody requestBody);

    @POST(APIClient.APPEND_URL + "s_forget_password.php")
    Call<JsonObject> getForgot(@Body RequestBody object);

    @POST(APIClient.APPEND_URL + "s_mobile_check.php")
    Call<JsonObject> getMobileCheck(@Body RequestBody object);

    @POST(APIClient.APPEND_URL + "s_partner_list.php")
    Call<JsonObject> getPartner(@Body RequestBody object);


}

package com.mdakram28.smarthome.retrofit;

import com.google.gson.JsonElement;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by mdakram28 on 6/6/17.
 */

public interface AuthAPI {

    @POST("/login")
    @FormUrlEncoded
    Call<JsonElement> login(@Field("username") String username, @Field("password") String password);
}

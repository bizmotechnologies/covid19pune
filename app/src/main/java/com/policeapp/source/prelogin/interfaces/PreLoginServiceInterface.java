package com.policeapp.source.prelogin.interfaces;

import com.policeapp.framework.network.Bean.CommonResponse;
import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

public interface PreLoginServiceInterface
{
    @FormUrlEncoded
    @POST("/Login_api/checkLogin")
    void signing(@Field("login_username") String userName, @Field("login_password") String password, Callback<CommonResponse> response);
}


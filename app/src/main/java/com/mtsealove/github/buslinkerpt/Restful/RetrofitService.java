package com.mtsealove.github.buslinkerpt.Restful;

import com.mtsealove.github.buslinkerpt.Restful.Request.Ask;
import com.mtsealove.github.buslinkerpt.Restful.Response.Login;
import com.mtsealove.github.buslinkerpt.Restful.Response.Result;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitService {
    @POST("/Login")
    Call<Login> Login(@Body com.mtsealove.github.buslinkerpt.Restful.Request.Login login);

    @POST("/Ask")
    Call<Result> CreateAsk(@Body Ask ask);
}

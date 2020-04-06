package com.mtsealove.github.buslinkerpt.Restful;

import com.mtsealove.github.buslinkerpt.Restful.Request.Ask;
import com.mtsealove.github.buslinkerpt.Restful.Request.Commute;
import com.mtsealove.github.buslinkerpt.Restful.Response.Calendar;
import com.mtsealove.github.buslinkerpt.Restful.Response.Cnt;
import com.mtsealove.github.buslinkerpt.Restful.Response.Login;
import com.mtsealove.github.buslinkerpt.Restful.Response.My;
import com.mtsealove.github.buslinkerpt.Restful.Response.Result;
import com.mtsealove.github.buslinkerpt.Restful.Response.Route_Timeline;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitService {
    @POST("/Login")
    Call<Login> Login(@Body com.mtsealove.github.buslinkerpt.Restful.Request.Login login);

    @POST("/Ask")
    Call<Result> CreateAsk(@Body Ask ask);

    @POST("/Pt/Commute")
    Call<Result> CheckQrCode(@Body Commute commute);

    @GET("/Commute")
    Call<Result> getCommute(@Query("ID") String id);

    @GET("/Pt/My")
    Call<My> getMy(@Query("ID") String id);

    @GET("/Pt/Calendar")
    Call<List<Calendar>> getCalendar(@Query("ID") String id);

    @GET("/Pt/Timeline")
    Call<Route_Timeline> getRouteTimeline(@Query("ID") String id, @Query("Date") String date);

    @GET("/Pt/Cnt")
    Call<Cnt> getCnt(@Query("ID") String id);
}

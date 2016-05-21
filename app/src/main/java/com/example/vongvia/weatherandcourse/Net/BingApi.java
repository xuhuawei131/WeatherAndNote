package com.example.vongvia.weatherandcourse.Net;


import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by castl on 2016/5/18.
 */
public interface BingApi {
    @GET("bing/day/{what_day}/mkt/{country}")
    Observable<ResponseBody> getBingPicPath(@Path("what_day") String what_day,
                                            @Path("country") String country);
}

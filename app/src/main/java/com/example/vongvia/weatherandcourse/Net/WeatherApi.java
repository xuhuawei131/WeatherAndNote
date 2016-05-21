package com.example.vongvia.weatherandcourse.Net;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by castl on 2016/5/19.
 */
public interface WeatherApi {

    @GET("/onebox/weather/query?")
    Observable<Weather> getWeatherInfo(@Query("cityname") String phone,
                                       @Query("key") String key);
}

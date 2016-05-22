package com.example.vongvia.weatherandcourse.Net;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by castl on 2016/5/18.
 */
public class NetWork {
    public static BingApi bingApi;
    public static WeatherApi weatherApi;
    static OkHttpClient client = new OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .readTimeout(3, TimeUnit.SECONDS)
            .connectTimeout(3, TimeUnit.SECONDS)
            .build();

    //获取bing壁纸地址
    public static BingApi getBingApi() {
        if (bingApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl("http://test.dou.ms/")
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            bingApi = retrofit.create(BingApi.class);
        }
        return bingApi;
    }

    //获取聚合数据的天气信息
    public static WeatherApi getWeatherApi() {
        if (weatherApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl("http://op.juhe.cn")
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            weatherApi = retrofit.create(WeatherApi.class);
        }
        return weatherApi;
    }

}

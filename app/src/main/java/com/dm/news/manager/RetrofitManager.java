package com.dm.news.manager;

import com.dm.news.api.ApiService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dm on 2016/10/28.
 * http管理类
 */

public class RetrofitManager {

    private  OkHttpClient client = null;
    private  Retrofit retrofit = null;
    private  ApiService apiService = null;

    private static RetrofitManager manager;

    private static final String BASE_URL = "http://api.tianapi.com/";

    private RetrofitManager(){}

    public static RetrofitManager getInstence() {

        if (manager == null) {
            synchronized (RetrofitManager.class) {
                if (manager == null) {
                    manager = new RetrofitManager();
                }
            }
        }
        return manager;
    }

    private OkHttpClient getOkHttpClient(){
        if (client == null) {
            client = new OkHttpClient()
                    .newBuilder()
                    .connectTimeout(3,TimeUnit.SECONDS)
                    .readTimeout(3,TimeUnit.SECONDS)
                    .writeTimeout(3,TimeUnit.SECONDS)
                    .build();
        }
        return client;
    }

    private Retrofit getRetrofit(){
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getOkHttpClient())
                    .baseUrl(BASE_URL)
                    .build();
        }
        return retrofit;
    }

    public ApiService getApiService(){

        if (apiService == null) {
            apiService = getRetrofit().create(ApiService.class);
        }
        return apiService;
    }

}

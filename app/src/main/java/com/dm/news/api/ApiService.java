package com.dm.news.api;

import com.dm.news.bean.MeiNvGson;
import com.dm.news.bean.NewsGson;
import com.dm.news.bean.User;

import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by Administrator on 2016/10/28.
 */

public interface ApiService {

    @GET("social/")
    Observable<String> getString(@Query("key")String key, @Query("num") String num, @Query("page") int page);

    @GET("social/")
    Observable <NewsGson> getNewsData(@Query("key")String key, @Query("num") String num, @Query("page") int page);

    @GET("meinv/")
    Observable <MeiNvGson> getPictureData(@Query("key")String key, @Query("num") String num, @Query("page") int page);

    @GET
    Observable <User> getUserInfo(@Url String url);
}

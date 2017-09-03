package com.azhar.upcomingmovies.interfaces;

import com.azhar.upcomingmovies.pojo.ImagesItem;
import com.azhar.upcomingmovies.pojo.MoviesItem;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by WIN 10 on 8/28/2017.
 */
public interface ApiInterface {


    @GET("/3/movie/upcoming")
    Call<MoviesItem> getMethod(@Query("api_key") String api_key);


    @GET("/3/movie/{id}/images")
    Call<ImagesItem> getImgMethod(@Path("id") int id,@Query("api_key") String api_key);
}

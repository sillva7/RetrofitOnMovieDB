package com.example.retrofitonmoviedb.API;

import com.example.retrofitonmoviedb.clASSes.Example;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface ApiService {


    @GET("movie/popular?api_key=a687e2e3f4e0d2806d76dcc7802dd7a3&language=en-US&page=1")
    Observable<Example> getExamplesPopular();

    @GET("movie/upcoming?api_key=a687e2e3f4e0d2806d76dcc7802dd7a3&language=en-US&page=1")
    Observable<Example> getExamplesLatest();
}

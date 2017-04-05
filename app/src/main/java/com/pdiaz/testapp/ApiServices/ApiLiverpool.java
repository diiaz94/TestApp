package com.pdiaz.testapp.ApiServices;

import com.pdiaz.testapp.DTO.Content;
import com.pdiaz.testapp.DTO.ResultSearch;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.QueryMap;

/**
 * Created by diiaz94 on 04-04-2017.
 */
public interface ApiLiverpool {


    public static final String BASE_URL = "https://www.liverpool.com.mx";
    @Headers({"Content-Type: application/json"})
    @GET("/tienda")
    Call<ResultSearch> search(@QueryMap Map<String, String> options);
}

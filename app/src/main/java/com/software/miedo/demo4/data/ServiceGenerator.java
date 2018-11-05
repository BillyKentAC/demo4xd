package com.software.miedo.demo4.data;

import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.util.Iterator;

import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Service Factory for Retrofit
 */
public class ServiceGenerator {

    public static final String API_BASE_URL = "http://169.62.31.213:8082/";


    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create());

    public static OkHttpClient httpClient = new OkHttpClient.Builder()
            .addInterceptor(new BasicAuthInterceptor("admin", "admin"))
            .build();

    private static Retrofit retrofit = builder
            .baseUrl(API_BASE_URL)
            .client(httpClient)
            .build();


    public static <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }


}
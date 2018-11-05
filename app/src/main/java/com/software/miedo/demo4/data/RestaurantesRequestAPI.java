package com.software.miedo.demo4.data;

import com.software.miedo.demo4.model.PostComentario;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RestaurantesRequestAPI {

    // bfbce7bb-840e-4849-8426-99a06eb7f940
    @GET("/alfresco/api/-default-/public/alfresco/versions/1/nodes/bfbce7bb-840e-4849-8426-99a06eb7f940/children")
    Call<String> getRestaurantesJSON();

    @GET("/alfresco/api/-default-/public/alfresco/versions/1/nodes/{id}")
    Call<String> getRestauranteById(@Path("id") String id);

    @GET("/alfresco/api/-default-/public/alfresco/versions/1/nodes/{id}/comments")
    Call<String> getCommentsById(@Path("id") String id);

}

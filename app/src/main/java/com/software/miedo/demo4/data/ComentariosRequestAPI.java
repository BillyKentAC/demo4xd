package com.software.miedo.demo4.data;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ComentariosRequestAPI {

    @Headers("Content-Type: application/json")
    @FormUrlEncoded
    @POST("/alfresco/api/-default-/public/alfresco/versions/1/nodes/{id}/comments")
    Call<String> postComment(@Field("content") String comment, @Path("id") String id);

    @GET("/alfresco/api/-default-/public/alfresco/versions/1/nodes/{id}/comments")
    Call<String> getCommentsById(@Path("id") String id);

}

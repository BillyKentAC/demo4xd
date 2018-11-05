package com.software.miedo.demo4.detail;

import android.arch.lifecycle.ViewModel;
import android.content.Context;


import com.software.miedo.demo4.data.RestaurantesRequestAPI;
import com.software.miedo.demo4.data.ServiceGenerator;
import com.software.miedo.demo4.model.Comentario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailViewModel extends ViewModel implements DetailContract.Presenter {


    private ArrayList<Comentario> mData = new ArrayList<>();

    private String idRestaurante;

    private DetailContract.View mView;


    public DetailViewModel() {


    }

    public ArrayList<Comentario> getData() {
        return mData;
    }

    public boolean isDataEmpty() {
        return mData.isEmpty();
    }


    @Override
    public void setView(DetailContract.View view) {
        mView = view;
    }

    public String getIdRestaurante() {
        return idRestaurante;
    }

    public void setIdRestaurante(String idRestaurante) {
        this.idRestaurante = idRestaurante;
    }

    @Override
    public void loadData() {

        mData.clear();

        RestaurantesRequestAPI api = ServiceGenerator.createService(RestaurantesRequestAPI.class);
        Call<String> call = api.getCommentsById(idRestaurante);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() == 200) {
                    String respuesta = response.body();
                    fetchData(respuesta);
                }
                mView.notificarCambios();

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                mView.mostrarError();
            }
        });
    }

    private void fetchData(String respuesta) {
        try {
            JSONArray comentarios = new JSONObject(respuesta).getJSONObject("list").getJSONArray("entries");

            for (int i = 0; i < comentarios.length(); i++) {
                JSONObject entry = comentarios.getJSONObject(i).getJSONObject("entry");

                String contenido = entry.getString("content");
                String[] partes = contenido.split("@");

                Comentario nuevoComentario = new Comentario();
                if (partes.length == 2) {
                    nuevoComentario.setEmisor(partes[0]);
                    nuevoComentario.setContenido(partes[1]);
                } else {
                    nuevoComentario.setEmisor("Emisor anónimo");
                    nuevoComentario.setContenido(contenido);
                }
                nuevoComentario.setUrlFoto("http://www.payasochupetin.com/chupetinface.jpg");

                mData.add(nuevoComentario);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}

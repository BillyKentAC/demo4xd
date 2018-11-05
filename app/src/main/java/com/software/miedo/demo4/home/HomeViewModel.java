package com.software.miedo.demo4.home;

import android.arch.lifecycle.ViewModel;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.software.miedo.demo4.data.RestaurantesRequestAPI;
import com.software.miedo.demo4.data.ServiceGenerator;
import com.software.miedo.demo4.model.Restaurante;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends ViewModel implements HomeContract.Presenter {

    public static final String TAG = "HomeViewModel";

    private static final String BASE_IMAGE_URL="http://169.62.31.213:8082/alfresco/service/api/node/content/workspace/SpacesStore/";

    HomeContract.View mView;


    private ArrayList<Restaurante> mData = new ArrayList<>();


    public HomeViewModel() {
        //probarMierda();
        //loadData();
    }

    public void probarMierda() {

        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        RestaurantesRequestAPI api = ServiceGenerator.createService(RestaurantesRequestAPI.class);

                        Call<String> callback = api.getRestauranteById("b5404121-f800-468f-9d7d-512ee26bef51");
                        try {
                            String response = callback.execute().toString();
                            Log.i("JOJO", "Codigo :" + response);

                        } catch (IOException e) {
                            Log.e("JOJO", "Se cago tmre");
                        }

                    }
                }

        ).start();
    }

    public boolean isDataEmpty() {
        return mData.isEmpty();
    }

    public ArrayList<Restaurante> getData() {
        return mData;
    }


    private ArrayList<String> fetchIdsData(String rpta) {
        ArrayList<String> ids = new ArrayList<>();
        try {
            Log.i("JOJO", "Obteniendo los id de los restaurantes.");
            JSONObject reader = new JSONObject(rpta);
            JSONArray entries = reader
                    .getJSONObject("list")
                    .getJSONArray("entries");

            for (int i = 0; i < entries.length(); i++) {
                JSONObject r = entries.getJSONObject(i).getJSONObject("entry");
                ids.add(r.getString("id"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("JOJO", "Error al leer el json");
        }
        return ids;
    }

    private void fetchRestauranteData(RestaurantesRequestAPI api, String id) {
        Log.i("JOJO", "Extrayendo la data de :" + id);
        Call<String> restauranteCall = api.getRestauranteById(id);

        restauranteCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("JOJO", "Codigo de busqueda: " + response.code());

                if (response.code() == 200) {
                    Restaurante restaurante = fetchRestaurante(response.body());
                    mData.add(restaurante);
                    mView.notificarCambios();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("JOJO", "Error en la consulta de un restaurante");
            }
        });

    }

    private Restaurante fetchRestaurante(String body) {
        Restaurante retorno = new Restaurante();

        try {
            JSONObject entry = new JSONObject(body).getJSONObject("entry");
            JSONObject properties =entry.getJSONObject("properties");

            retorno.setNombre((properties.has("cm:title")) ? properties.getString("cm:title")
                    : "Título");
            retorno.setDescripcion((properties.has("cm:description")) ? properties.getString("cm:description")
                    : "Descripción");
            retorno.setDireccion((properties.has("cm:location")) ? properties.getString("cm:location")
                    : "Dirección");

            // por cambiar
            retorno.setPuntuacion(1);
            retorno.setId(entry.getString("id"));
            retorno.setUrlImage(BASE_IMAGE_URL+entry.getString("id"));


        } catch (JSONException e) {
            Log.e("JOJO", "Error al analizar el json de un restaurante");
        }

        return retorno;


    }

    @Override
    public void setView(HomeContract.View view) {
        mView = view;
    }

    @Override
    public void loadData() {

        mData.clear();
        final RestaurantesRequestAPI api = ServiceGenerator.createService(RestaurantesRequestAPI.class);
        Call<String> call = api.getRestaurantesJSON();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("JOJO", "Response code : " + response.code());
                Log.i("JOJO", "Cuerpo: " + response.body());

                if (response.code() == 200) {
                    String rpta = response.body();

                    // Obtenemos los id's
                    ArrayList<String> idsRestaurantes = fetchIdsData(rpta);
                    Log.i("JOJO", "Obteniendo la data de " + idsRestaurantes.size() + " restaurantes.");

                    // Para cada id obtenemos la data de los restaurantes
                    for (String id : idsRestaurantes) {
                        fetchRestauranteData(api, id);
                    }
                }

                /*Call<String> callback = api.getRestauranteById("b5404121-f800-468f-9d7d-512ee26bef51");
                try {
                    String responses = callback.execute().toString();
                    Log.i("JOJO", "Codigo :" + responses);

                } catch (IOException e) {
                    Log.e("JOJO", "Se cago tmre");
                }*/
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("JOJO", "la puta madre");
            }
        });

    }
}

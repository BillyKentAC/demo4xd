package com.software.miedo.demo4.home;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.software.miedo.demo4.R;
import com.software.miedo.demo4.DetailActivity;
import com.software.miedo.demo4.data.ServiceGenerator;
import com.software.miedo.demo4.model.Restaurante;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.CartaRestaurante> {

    List<Restaurante> mData;

    Context mContext = null;

    public HomeAdapter(List<Restaurante> data) {
        mData = data;
    }


    @Override
    public CartaRestaurante onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_item_restaurante, parent, false);

        return new CartaRestaurante(itemView);
    }

    @Override
    public void onBindViewHolder(final CartaRestaurante holder, int position) {
        final Restaurante restaurante = mData.get(position);

        holder.tv_nombre.setText(restaurante.getNombre());
        holder.rb_puntuacion.setRating(restaurante.getPuntuacion());

        Picasso picasso = new Picasso.Builder(mContext)
                .downloader(new OkHttp3Downloader(ServiceGenerator.httpClient))
                .build();

        picasso.load(restaurante.getUrlImage()).into(holder.iv_foto);


        if (mContext != null) {
            holder.cv_restaurante.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, DetailActivity.class);

                    intent.putExtra("nombre", restaurante.getNombre());
                    intent.putExtra("direccion", restaurante.getDireccion());
                    intent.putExtra("imagenURL", restaurante.getUrlImage());
                    intent.putExtra("descripcion", restaurante.getDescripcion());
                    intent.putExtra("id", restaurante.getId());
                    mContext.startActivity(intent);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public static class CartaRestaurante extends RecyclerView.ViewHolder {

        public TextView tv_nombre;
        public ImageView iv_foto;
        public RatingBar rb_puntuacion;
        public CardView cv_restaurante;

        public CartaRestaurante(View view) {
            super(view);
            tv_nombre = (TextView) view.findViewById(R.id.nombre);
            iv_foto = (ImageView) view.findViewById(R.id.foto);
            rb_puntuacion = (RatingBar) view.findViewById(R.id.rb_puntuacion);
            cv_restaurante = (CardView) view.findViewById(R.id.cardview_restaurante);
        }
    }

}

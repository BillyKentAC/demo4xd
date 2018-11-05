package com.software.miedo.demo4.detail;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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
import com.software.miedo.demo4.model.Comentario;
import com.software.miedo.demo4.model.Restaurante;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.CartaComentario> {

    List<Comentario> mData;

    Context mContext = null;

    public DetailAdapter(List<Comentario> data) {
        mData = data;
    }

    @Override
    public CartaComentario onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_item_comentario, parent, false);

        return new CartaComentario(itemView);
    }

    @Override
    public void onBindViewHolder(final CartaComentario holder, int position) {
        final Comentario comentario = mData.get(position);

        holder.tv_comentario_emisor.setText(comentario.getEmisor());
        holder.tv_comentario_contenido.setText(comentario.getContenido());
        Picasso.get().load(comentario.getUrlFoto()).into(holder.iv_comentario_foto);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public static class CartaComentario extends RecyclerView.ViewHolder {

        public TextView tv_comentario_emisor, tv_comentario_contenido;

        public ImageView iv_comentario_foto;

        public CartaComentario(View view) {
            super(view);
            tv_comentario_emisor = (TextView) view.findViewById(R.id.comentario_emisor);
            tv_comentario_contenido = (TextView) view.findViewById(R.id.comentario_contenido);
            iv_comentario_foto = (ImageView) view.findViewById(R.id.comentario_foto);
        }
    }

}

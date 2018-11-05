package com.software.miedo.demo4;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.software.miedo.demo4.R;
import com.software.miedo.demo4.data.ServiceGenerator;
import com.software.miedo.demo4.detail.DetailFragment;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    TextView tv_nombre_restaurante, tv_direccion_restaurante, tv_descripcion_restaurante;
    ImageView iv_foto_restaurante;

    DetailFragment fragment;

    private final int COMENTARIO_AGREGADO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_nofake);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_nofake);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        fragment = DetailFragment.newInstance();
        //getSupportFragmentManager().findFragmentById(R.id.detail_container);
        setFragment(fragment);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.bt_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, CommentActivity.class);
                intent.putExtra("id", id);
                startActivityForResult(intent, COMENTARIO_AGREGADO);
            }
        });

        tv_descripcion_restaurante = (TextView) findViewById(R.id.tv_descripcion_restaurante);
        tv_direccion_restaurante = (TextView) findViewById(R.id.tv_direccion_restaurante);
        tv_nombre_restaurante = (TextView) findViewById(R.id.tv_nombre_restaurante);
        iv_foto_restaurante = (ImageView) findViewById(R.id.iv_foto_restaurante);
        configurarDatosEntrada();
    }

    String id;

    private void configurarDatosEntrada() {

        //intent.putExtra("nombre", restaurante.getNombre());
        //                    intent.putExtra("direccion", restaurante.getDireccion());
        //                    intent.putExtra("imagenURL", restaurante.getUrlImage());

        String nombre = getIntent().getStringExtra("nombre");
        String direccion = getIntent().getStringExtra("direccion");
        String imagenURL = getIntent().getStringExtra("imagenURL");
        String descripcion = getIntent().getStringExtra("descripcion");
        id = getIntent().getStringExtra("id");

        fragment.setIdRestaurante(id);

        tv_nombre_restaurante.setText(nombre);
        tv_direccion_restaurante.setText(direccion);
        tv_descripcion_restaurante.setText(descripcion);

        Picasso picasso = new Picasso.Builder(this)
                .downloader(new OkHttp3Downloader(ServiceGenerator.httpClient))
                .build();
        picasso.load(imagenURL).into(iv_foto_restaurante);
    }

    // This could be moved into an abstract BaseActivity
    // class for being re-used by several instances
    protected void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.detail_container, fragment);
        fragmentTransaction.commit();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == COMENTARIO_AGREGADO && resultCode == Activity.RESULT_OK) {
            fragment.actualizarData();
        }

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}

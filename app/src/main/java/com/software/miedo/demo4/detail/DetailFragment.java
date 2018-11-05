package com.software.miedo.demo4.detail;

import android.arch.lifecycle.ViewModelProviders;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.software.miedo.demo4.R;
import com.software.miedo.demo4.home.HomeAdapter;
import com.software.miedo.demo4.home.HomeFragment;


public class DetailFragment extends Fragment implements DetailContract.View {

    private DetailViewModel mViewModel;
    private DetailAdapter adapter;
    private ProgressBar barra;
    private RecyclerView recyclerView;
    private TextView error;

    public static DetailFragment newInstance() {
        return new DetailFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);


        mViewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
        mViewModel.setView(this);
        mViewModel.setIdRestaurante(id);

        // Asignamos el adapter del viewmodel al recyclerview
        recyclerView = (RecyclerView) view.findViewById(R.id.reciclerviewXD);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        barra = (ProgressBar) view.findViewById(R.id.progreso_home);
        error = (TextView) view.findViewById(R.id.error_home);


        adapter = new DetailAdapter(mViewModel.getData());
        recyclerView.setAdapter(adapter);
        adapter.setmContext(this.getContext());

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        actualizarData();
    }


    @Override
    public void setPresenter(DetailContract.Presenter presenter) {

    }

    @Override
    public void mostrarCarga() {
        recyclerView.setVisibility(View.GONE);
        error.setVisibility(View.GONE);
        barra.setVisibility(View.VISIBLE);
    }

    @Override
    public void mostrarError() {
        recyclerView.setVisibility(View.GONE);
        error.setVisibility(View.VISIBLE);
        barra.setVisibility(View.GONE);
    }

    @Override
    public void mostrarDatos() {

        recyclerView.setVisibility(View.VISIBLE);
        error.setVisibility(View.GONE);
        barra.setVisibility(View.GONE);
    }

    public void actualizarData() {
        mostrarCarga();
        mViewModel.loadData();
    }

    @Override
    public void notificarCambios() {

        if (recyclerView.getVisibility() == View.GONE) {
            if (!mViewModel.isDataEmpty()) {
                mostrarDatos();
            }
        }
        adapter.notifyDataSetChanged();

    }

    public void setIdRestaurante(String id) {
        this.id = id;
    }

    String id;


}

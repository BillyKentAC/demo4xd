package com.software.miedo.demo4.home;

import android.arch.lifecycle.ViewModelProviders;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.software.miedo.demo4.R;

public class HomeFragment extends Fragment implements HomeContract.View {

    private HomeViewModel mViewModel;

    private HomeAdapter adapter;

    private ProgressBar barra;
    private RecyclerView recyclerView;
    private TextView error;

    public HomeFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home_fragment, container, false);

        mViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        mViewModel.setView(this);

        barra = (ProgressBar) view.findViewById(R.id.progreso_home);
        error = (TextView) view.findViewById(R.id.error_home);

        // Asignamos el adapter del viewmodel al recyclerview
        recyclerView = (RecyclerView) view.findViewById(R.id.reciclerviewXD);

        //recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        adapter = new HomeAdapter(mViewModel.getData());
        recyclerView.setAdapter(adapter);
        adapter.setmContext(HomeFragment.this.getContext());

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //comprobarData();

        mostrarCarga();
        mViewModel.loadData();
    }

    /*private void comprobarData() {
        if (mViewModel.isDataEmpty()) {
            HomeTask task = new HomeTask();
            task.execute((Void) null);
        } else {

            recyclerView.setVisibility(View.VISIBLE);
            error.setVisibility(View.GONE);
            barra.setVisibility(View.GONE);
        }


    }*/

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {

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

    @Override
    public void notificarCambios() {

        if (recyclerView.getVisibility() == View.GONE) {
            if (!mViewModel.isDataEmpty()) {
                mostrarDatos();
            }
        }
        adapter.notifyDataSetChanged();

    }


   /* public class HomeTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            recyclerView.setVisibility(View.GONE);
            error.setVisibility(View.GONE);
            barra.setVisibility(View.VISIBLE);
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                return false;
            }

            mViewModel.addDatosFake();

            return true;
        }

        @Override
        protected void onPostExecute(Boolean success) {

            if (success) {

                recyclerView.setVisibility(View.VISIBLE);
                error.setVisibility(View.GONE);
                barra.setVisibility(View.GONE);

                adapter.notifyDataSetChanged();

            } else {

                recyclerView.setVisibility(View.GONE);
                error.setVisibility(View.VISIBLE);
                barra.setVisibility(View.GONE);
            }


        }
    }*/


}

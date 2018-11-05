package com.software.miedo.demo4.home;

public class HomeContract {

    interface View {
        void setPresenter(Presenter presenter);

        void mostrarCarga();

        void mostrarError();

        void mostrarDatos();

        void notificarCambios();

    }

    interface Presenter {
        void setView(View view);

        void loadData();

    }

}

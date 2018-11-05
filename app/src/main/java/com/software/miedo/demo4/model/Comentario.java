package com.software.miedo.demo4.model;

public class Comentario {

    private String emisor, contenido, urlFoto;

    public Comentario(String emisor, String contenido, String urlFoto) {
        this.emisor = emisor;
        this.contenido = contenido;
        this.urlFoto = urlFoto;
    }

    public Comentario() {
    }

    public String getEmisor() {
        return emisor;
    }

    public void setEmisor(String emisor) {
        this.emisor = emisor;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }
}

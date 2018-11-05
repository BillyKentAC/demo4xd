package com.software.miedo.demo4.model;

public class Restaurante {

    private String nombre;
    private String direccion;
    private float puntuacion;
    private String urlImage;
    private String descripcion;
    private String id;

    public Restaurante() {
    }

    public Restaurante(String nombre, String direccion, float puntuacion, String urlImage, String descripcion, String id) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.puntuacion = puntuacion;
        this.urlImage = urlImage;
        this.descripcion = descripcion;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public float getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(float puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}

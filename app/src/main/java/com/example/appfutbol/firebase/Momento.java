package com.example.appfutbol.firebase;

import android.graphics.Bitmap;

public class Momento {
    private String titulo;
    private String descripcion;
    private String fecha;
    private Bitmap imagen;

    public Momento(String titulo, String descripcion, String fecha, Bitmap imagen) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.imagen = imagen;
    }

    public Momento() {
        this.titulo = "";
        this.descripcion = "";
        this.fecha = "";
        this.imagen = null;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }
}

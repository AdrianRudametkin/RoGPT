package com.example.appfutbol.ui.momentos;

import android.graphics.Bitmap;
/**
 * Rogelio Rodriguez
 */
// Modelo objeto de un momento
public class Momento {
    private String id;
    private String titulo;
    private String descripcion;
    private String fecha;
    private String imagenUrl;

    public Momento(String id, String titulo, String descripcion, String fecha, String imagenUrl) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.imagenUrl = imagenUrl;
    }

    public Momento() {
        this.id = "";
        this.titulo = "";
        this.descripcion = "";
        this.fecha = "";
        this.imagenUrl = null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }
}

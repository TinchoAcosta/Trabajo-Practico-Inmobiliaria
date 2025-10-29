package com.example.trabajopracticoinmobiliaria.models;

import java.io.Serializable;

public class Contrato implements Serializable {
    private String fechaInicio, fechaFinalizacion;
    private int idContrato, idInquilino, idInmueble;
    private double montoAlquiler;
    private boolean estado;
    private Inquilino inquilino;
    private Inmueble inmueble;

    public Contrato() {}

    public Contrato(boolean estado, double montoAlquiler, String fechaFinalizacion, String fechaInicio, int idContrato, int idInmueble, int idInquilino, Inmueble inmueble, Inquilino inquilino) {
        this.estado = estado;
        this.montoAlquiler = montoAlquiler;
        this.fechaFinalizacion = fechaFinalizacion;
        this.fechaInicio = fechaInicio;
        this.idContrato = idContrato;
        this.idInmueble = idInmueble;
        this.idInquilino = idInquilino;
        this.inmueble = inmueble;
        this.inquilino = inquilino;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public double getMontoAlquiler() {
        return montoAlquiler;
    }

    public void setMontoAlquiler(double montoAlquiler) {
        this.montoAlquiler = montoAlquiler;
    }

    public Inquilino getInquilino() {
        return inquilino;
    }

    public void setInquilino(Inquilino inquilino) {
        this.inquilino = inquilino;
    }

    public Inmueble getInmueble() {
        return inmueble;
    }

    public void setInmueble(Inmueble inmueble) {
        this.inmueble = inmueble;
    }

    public int getIdInquilino() {
        return idInquilino;
    }

    public void setIdInquilino(int idInquilino) {
        this.idInquilino = idInquilino;
    }

    public int getIdInmueble() {
        return idInmueble;
    }

    public void setIdInmueble(int idInmueble) {
        this.idInmueble = idInmueble;
    }

    public int getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(int idContrato) {
        this.idContrato = idContrato;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFinalizacion() {
        return fechaFinalizacion;
    }

    public void setFechaFinalizacion(String fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
    }
}

package com.example.trabajopracticoinmobiliaria.models;

public class Inmueble {
    private int idInmueble, ambientes, idPropietario;
    private String direccion, uso, tipo, imagen;
    private double superficie, latitud, longitud, valor;
    private boolean disponible, tieneContratoVigente;
    private Propietario duenio;

    public Inmueble() {}

    public Inmueble(int ambientes, double valor, String uso, String tipo, boolean tieneContratoVigente, double superficie, double longitud, double latitud, String imagen, int idPropietario, int idInmueble, Propietario duenio, boolean disponible, String direccion) {
        this.ambientes = ambientes;
        this.valor = valor;
        this.uso = uso;
        this.tipo = tipo;
        this.tieneContratoVigente = tieneContratoVigente;
        this.superficie = superficie;
        this.longitud = longitud;
        this.latitud = latitud;
        this.imagen = imagen;
        this.idPropietario = idPropietario;
        this.idInmueble = idInmueble;
        this.duenio = duenio;
        this.disponible = disponible;
        this.direccion = direccion;
    }

    public int getAmbientes() {
        return ambientes;
    }

    public void setAmbientes(int ambientes) {
        this.ambientes = ambientes;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public Propietario getDuenio() {
        return duenio;
    }

    public void setDuenio(Propietario duenio) {
        this.duenio = duenio;
    }

    public int getIdInmueble() {
        return idInmueble;
    }

    public void setIdInmueble(int idInmueble) {
        this.idInmueble = idInmueble;
    }

    public int getIdPropietario() {
        return idPropietario;
    }

    public void setIdPropietario(int idPropietario) {
        this.idPropietario = idPropietario;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public double getSuperficie() {
        return superficie;
    }

    public void setSuperficie(double superficie) {
        this.superficie = superficie;
    }

    public boolean isTieneContratoVigente() {
        return tieneContratoVigente;
    }

    public void setTieneContratoVigente(boolean tieneContratoVigente) {
        this.tieneContratoVigente = tieneContratoVigente;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getUso() {
        return uso;
    }

    public void setUso(String uso) {
        this.uso = uso;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}

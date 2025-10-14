package com.example.trabajopracticoinmobiliaria.models;

import java.io.Serializable;

public class Propietario implements Serializable {
    private int idPropietario;
    private String nombre, apellido, dni, telefono, email, clave;

    public Propietario(String apellido, String telefono, String nombre, int idPropietario, String email, String dni, String clave) {
        this.apellido = apellido;
        this.telefono = telefono;
        this.nombre = nombre;
        this.idPropietario = idPropietario;
        this.email = email;
        this.dni = dni;
        this.clave = clave;
    }

    public Propietario(){}

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getIdPropietario() {
        return idPropietario;
    }
}

package com.example.trabajopracticoinmobiliaria.models;

public class Inquilino {
    private String nombre, apellido, email;
    private int telefono, dni, idInquilino;

    public Inquilino(){}

    public Inquilino(String apellido, int telefono, String nombre, String email, int dni, int id) {
        this.apellido = apellido;
        this.telefono = telefono;
        this.nombre = nombre;
        this.email = email;
        this.dni = dni;
        this.idInquilino = id;
    }

    public int getIdInquilino() {
        return idInquilino;
    }

    public void setIdInquilino(int idInquilino) {
        this.idInquilino = idInquilino;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
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

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }
}

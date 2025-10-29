package com.example.trabajopracticoinmobiliaria.models;

public class Pago {
    private int idPago, idContrato;
    private String fechaPago, detalle;
    private double monto;
    private boolean estado;
    private Contrato contrato;

    public Pago() {}

    public Pago(Contrato contrato, double monto, int idPago, int idContrato, String fechaPago, boolean estado, String detalle) {
        this.contrato = contrato;
        this.monto = monto;
        this.idPago = idPago;
        this.idContrato = idContrato;
        this.fechaPago = fechaPago;
        this.estado = estado;
        this.detalle = detalle;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public int getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(int idContrato) {
        this.idContrato = idContrato;
    }

    public String getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(String fechaPago) {
        this.fechaPago = fechaPago;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }
}

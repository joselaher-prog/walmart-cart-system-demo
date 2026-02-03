package com.walmart.miservidor.model;

public class PromoResponse {
    private boolean aplica;
    private String mensaje;
    private double descuentoMonto;
    
    public boolean isAplica() { return aplica; } public void setAplica(boolean a) { aplica = a; }
    public String getMensaje() { return mensaje; } public void setMensaje(String m) { mensaje = m; }
    public double getDescuentoMonto() { return descuentoMonto; } public void setDescuentoMonto(double d) { descuentoMonto = d; }
}
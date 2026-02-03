package com.walmart.miservidor.model;

import java.util.List;

public class BoletaResponse {
    private String cartId;
    private List<Item> items;
    private String paymentMethod;
    private ShippingAddress shippingAddress;
    private double totalEstimado;
    private double costoEnvio;
    private double descuentoAplicado;
    private double totalConDescuento;

    // Getters y Setters
    public String getCartId() { return cartId; }
    public void setCartId(String cartId) { this.cartId = cartId; }

    public List<Item> getItems() { return items; }
    public void setItems(List<Item> items) { this.items = items; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public ShippingAddress getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(ShippingAddress shippingAddress) { this.shippingAddress = shippingAddress; }

    public double getTotalEstimado() { return totalEstimado; }
    public void setTotalEstimado(double totalEstimado) { this.totalEstimado = totalEstimado; }

    public double getCostoEnvio() { return costoEnvio; }
    public void setCostoEnvio(double costoEnvio) { this.costoEnvio = costoEnvio; }

    public double getDescuentoAplicado() { return descuentoAplicado; }
    public void setDescuentoAplicado(double descuentoAplicado) { this.descuentoAplicado = descuentoAplicado; }

    public double getTotalConDescuento() { return totalConDescuento; }
    public void setTotalConDescuento(double totalConDescuento) { this.totalConDescuento = totalConDescuento; }
}
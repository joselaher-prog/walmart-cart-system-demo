package com.walmart.miservidor.model;
import java.util.List;

public class Carrito {
    private String cartId;
    private List<Item> items;
    private String paymentMethod;
    private ShippingAddress shippingAddress;
    
    public String getCartId() { return cartId; }
    public List<Item> getItems() { return items; }
    public void setItems(List<Item> items) { this.items = items; }
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public ShippingAddress getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(ShippingAddress shippingAddress) { this.shippingAddress = shippingAddress; }
}
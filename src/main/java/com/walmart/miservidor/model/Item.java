package com.walmart.miservidor.model;

public class Item {
    private String sku, name;
    private double price;
    private int quantity;
    
    public String getSku() { return sku; } public void setSku(String s) { sku = s; }
    public String getName() { return name; } public void setName(String n) { name = n; }
    public double getPrice() { return price; } public void setPrice(double p) { price = p; }
    public int getQuantity() { return quantity; } public void setQuantity(int q) { quantity = q; }
}
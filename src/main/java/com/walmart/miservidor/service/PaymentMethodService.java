package com.walmart.miservidor.service;

import org.springframework.stereotype.Service;

@Service
public class PaymentMethodService {

    /**
     * Define los porcentajes de descuento. 
     * Si no coincide con ninguno, devuelve 0.0 (0%).
     */
    public double getPorcentaje(String m) {
        if (m == null) return 0.0;
        
        switch (m.toUpperCase()) {
            case "LIDER_BCI":
                return 0.30; // 30% de descuento
            case "DEBIT":
                return 0.20; // Se mantiene 20%
            case "CREDIT_CARD":
                return 0.10; // Se mantiene 10%
            case "CASH":
                return 0.05; // 5% de descuento
            default:
                return 0.0;  // Si no viene ninguno de estos valores, 0%
        }
    }

    /**
     * Retorna el nombre legible para la vista HTML.
     */
    public String getMetodoVisual(String m) {
        if (m == null) return "No especificado";
        
        switch (m.toUpperCase()) {
            case "LIDER_BCI":
                return "Tarjeta Lider BCI";
            case "DEBIT":
                return "Débito";
            case "CREDIT_CARD":
                return "Tarjeta de Crédito";
            case "CASH":
                return "Efectivo";
            default:
                return "Otro método";
        }
    }
}
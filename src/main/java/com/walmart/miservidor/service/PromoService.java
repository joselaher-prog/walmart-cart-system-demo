package com.walmart.miservidor.service;

import com.walmart.miservidor.model.PromoResponse; 
import com.walmart.miservidor.model.Item;
import org.springframework.stereotype.Service;

@Service
public class PromoService {

    /**
     * Calcula el ahorro para un item individual.
     * Se usa para el cálculo del total en el Controlador.
     */
    public double obtenerAhorroDesdeItem(Item item) {
        if (item == null || item.getSku() == null) return 0.0;
        
        String sku = item.getSku().toLowerCase();
        int qty = item.getQuantity();
        double price = item.getPrice();

        // Lógica P-010: Lleva 4 paga 3 (El 4to es gratis)
        if (sku.equals("p-010")) {
            int unidadesGratis = qty / 4; // División entera: si lleva 8, son 2 gratis
            return unidadesGratis * price;
        }

        // Lógica P-030: Cada 2 por 400 pesos
        if (sku.equals("p-030")) {
            int parejas = qty / 2;
            double costoNormalPareja = price * 2;
            double ahorroPorPareja = costoNormalPareja - 400.0;
            
            // Si el ahorro es negativo (ej. el precio ya era < 200), no aplicamos
            return (ahorroPorPareja > 0) ? (parejas * ahorroPorPareja) : 0.0;
        }

        return 0.0;
    }

    /**
     * Procesa la promoción para la validación dinámica (Frontend).
     */
    public PromoResponse procesarPromocion(String sku, int qty, double price) {
        PromoResponse res = new PromoResponse();
        res.setAplica(false);
        res.setDescuentoMonto(0.0);

        if (sku == null) return res;
        String skuLower = sku.toLowerCase();

        // Validación P-010
        if (skuLower.equals("p-010") && qty >= 4) {
            double ahorro = (qty / 4) * price;
            res.setAplica(true);
            res.setMensaje("¡Promoción 4x3 aplicada!");
            res.setDescuentoMonto(ahorro);
        } 
        // Validación P-030
        else if (skuLower.equals("p-030") && qty >= 2) {
            int parejas = qty / 2;
            double ahorro = (parejas * (price * 2)) - (parejas * 400.0);
            if (ahorro > 0) {
                res.setAplica(true);
                res.setMensaje("¡Oferta 2 x $400 aplicada!");
                res.setDescuentoMonto(ahorro);
            }
        }

        return res;
    }
}
package com.walmart.miservidor.service;

import com.walmart.miservidor.model.ShippingAddress; 
import org.springframework.stereotype.Service;

@Service
public class ShippingService {

    public double calcularEnvio(double subtotal, ShippingAddress address) {
        // Validación de seguridad: si no hay dirección, no podemos calcular zona
        if (address == null || address.getZoneId() == null) {
            return 0.0;
        }
        
        // Lógica de envío por zona
        switch (address.getZoneId().toLowerCase()) {
            case "zone-1":
                return 1000.0;
            case "zone-2":
                return 2000.0;
            case "zone-3":
                return 3000.0;
            default:
                // Si no corresponde a ninguna de las zonas anteriores
                return 0.0;
        }
    }
}
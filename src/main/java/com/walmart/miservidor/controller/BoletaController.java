package com.walmart.miservidor.controller;

import com.walmart.miservidor.model.BoletaResponse;
import tools.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class BoletaController {

    @PostMapping("/boleta")
public BoletaResponse generarBoleta(@RequestBody BoletaResponse entrada) {
    BoletaResponse salida = new BoletaResponse();
    
    salida.setCartId(entrada.getCartId()); 
    salida.setItems(entrada.getItems());
    salida.setPaymentMethod(entrada.getPaymentMethod());
    salida.setShippingAddress(entrada.getShippingAddress());
    salida.setTotalEstimado(entrada.getTotalEstimado());
    salida.setCostoEnvio(entrada.getCostoEnvio());
    salida.setDescuentoAplicado(entrada.getDescuentoAplicado());
    salida.setTotalConDescuento(entrada.getTotalConDescuento());

    // IMPRESIÓN EN EL LOG DEL SERVIDOR
    System.out.println("========================================");
    System.out.println("BOLETA PROCESADA EXITOSAMENTE");
    System.out.println("Cart ID: " + salida.getCartId());
    System.out.println("Total Pagado: $" + salida.getTotalConDescuento());
    System.out.println("========================================");

    try {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(entrada);
        
        System.out.println("========================================");
        System.out.println("DEBUG JSON RECIBIDO:");
        System.out.println(jsonString);
        System.out.println("========================================");
        
    } catch (Exception e) {
        System.out.println("Error al imprimir el JSON: " + e.getMessage());
    }

    return salida; // Retorna la respuesta en un json con las modificaciones realizadas en pantalla
}
}
package com.walmart.miservidor.controller;

import com.walmart.miservidor.model.*; // Importamos los nuevos archivos de la carpeta model
import com.walmart.miservidor.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class Controlador {

    @Autowired 
    private PromoService promoService;
    
    @Autowired 
    private ShippingService shippingService;
    
    @Autowired 
    private LoyaltyService loyaltyService;

    @Autowired
    private PaymentMethodService paymentMethodService;

    private Carrito ultimoCarrito;

    @PostMapping("/carrito")
    @ResponseBody
    public String recibirCarrito(@RequestBody Carrito datosEntrada) {
        this.ultimoCarrito = datosEntrada;
        return "OK";
    }

    @GetMapping("/ver-carrito")
    public String verCarrito(Model model) {
        if (ultimoCarrito == null) return "error";

        double totalBruto = 0;
        double totalAhorroPromos = 0;

        for (Item item : ultimoCarrito.getItems()) {
            totalBruto += (item.getPrice() * item.getQuantity());
            totalAhorroPromos += promoService.obtenerAhorroDesdeItem(item);
        }

        double subtotalConPromos = totalBruto - totalAhorroPromos;
        double costoEnvio = shippingService.calcularEnvio(subtotalConPromos, ultimoCarrito.getShippingAddress());

        double porcentajeDescMetodo = paymentMethodService.getPorcentaje(ultimoCarrito.getPaymentMethod());
        String nombreMetodoVisual = paymentMethodService.getMetodoVisual(ultimoCarrito.getPaymentMethod());
        
        double totalEstimado = subtotalConPromos + costoEnvio;
        double montoDescuentoMetodo = subtotalConPromos * porcentajeDescMetodo;
        double pagoFinal = totalEstimado - montoDescuentoMetodo;

        int puntosMiClub = loyaltyService.calcularPesosMiClub(pagoFinal);
        
        model.addAttribute("cartId", ultimoCarrito.getCartId());
        model.addAttribute("items", ultimoCarrito.getItems());
        model.addAttribute("metodo", nombreMetodoVisual);
        model.addAttribute("porcentaje_js", porcentajeDescMetodo);
        model.addAttribute("porcentaje_num", (int)(porcentajeDescMetodo * 100));
        model.addAttribute("envio", costoEnvio);
        model.addAttribute("direccion", ultimoCarrito.getShippingAddress());
        model.addAttribute("puntos", puntosMiClub);

        return "carrito";
    }

    @GetMapping("/validar-promo")
    @ResponseBody
    public PromoResponse validarPromocion(@RequestParam String sku, @RequestParam int qty, @RequestParam double price) {
        return promoService.procesarPromocion(sku, qty, price);
    }
}
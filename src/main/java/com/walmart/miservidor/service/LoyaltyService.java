package com.walmart.miservidor.service;

import org.springframework.stereotype.Service;

@Service
public class LoyaltyService {
    public int calcularPesosMiClub(double montoFinal) {
        return (int) Math.floor(montoFinal * 0.05);
    }
}
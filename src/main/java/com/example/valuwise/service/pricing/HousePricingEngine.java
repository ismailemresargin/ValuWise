package com.example.valuwise.service.pricing;

import com.example.valuwise.model.House; // Ev yerine House import edildi

public class HousePricingEngine {
    public double tahminiDegerHesapla(House house) {
        double tabanFiyat = house.getMetrekare() * 30000;

        if (house.isDenizManzarasiVarMi()) tabanFiyat += 600000;
        if (house.isOtoparkVarMi()) tabanFiyat += 150000;
        if (house.isAsansorVarMi()) tabanFiyat += 100000;

        if (house.isDepremeDayanikliMi()) tabanFiyat *= 1.25;
        else tabanFiyat *= 0.65;

        double yipranmaPayi = (house.getBinaYasi() * 0.01) * tabanFiyat;
        tabanFiyat -= yipranmaPayi;
        house.setTahminiDeger(tabanFiyat);
        return tabanFiyat;
    }
}
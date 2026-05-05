package com.example.valuwise.service.pricing;

import com.example.valuwise.model.Car;

public class CarPricingEngine implements IPricingStrategy<Car> {

    private static final int CURRENT_YEAR = 2026;
    private static final double BASE_PRICE = 1_500_000.0;

    @Override
    public double tahminiDegerHesapla(Car car) {
        double fiyat = BASE_PRICE;

        int yas = CURRENT_YEAR - car.getModelYili();
        fiyat *= getYasCarpani(yas);

        fiyat *= getKmCarpani(car.getKilometre());

        fiyat *= getYakitCarpani(car.getYakitTipi());

        fiyat *= getVitesCarpani(car.getVitesTipi());

        fiyat *= getTramerCarpani(car.getTramerKaydi());

        fiyat *= getParcaCarpani(car.getDegisenParcaSayisi());

        car.setTahminiDeger(fiyat);
        return fiyat;
    }

    private double getYasCarpani(int yas) {
        if (yas <= 1)  return 0.95;
        if (yas <= 3)  return 0.85;
        if (yas <= 5)  return 0.75;
        if (yas <= 8)  return 0.65;
        if (yas <= 12) return 0.55;
        if (yas <= 17) return 0.45;
        return 0.40; // 17+ yil taban
    }

    private double getKmCarpani(int km) {
        if (km <= 50_000)  return 1.00;
        if (km <= 100_000) return 0.90;
        if (km <= 150_000) return 0.80;
        if (km <= 200_000) return 0.70;
        if (km <= 300_000) return 0.60;
        return 0.50; // 300k+ taban
    }

    private double getYakitCarpani(String yakitTipi) {
        if (yakitTipi == null) return 1.00;
        return switch (yakitTipi.trim().toLowerCase()) {
            case "elektrik" -> 1.20;
            case "hibrit"   -> 1.10;
            case "dizel"    -> 1.05;
            case "lpg"      -> 0.90;
            default         -> 1.00; // Benzin ve digerleri
        };
    }

    private double getVitesCarpani(String vitesTipi) {
        if (vitesTipi == null) return 1.00;
        return switch (vitesTipi.trim().toLowerCase()) {
            case "otomatik"      -> 1.10;
            case "yarı otomatik" -> 1.05;
            default              -> 1.00; // Manuel
        };
    }

    private double getTramerCarpani(double tramer) {
        if (tramer <= 0)      return 1.00;
        if (tramer <= 10_000) return 0.95;
        if (tramer <= 50_000) return 0.85;
        return 0.70; // 50k+ TL hasar
    }

    private double getParcaCarpani(int parcaSayisi) {
        if (parcaSayisi <= 0) return 1.00;
        if (parcaSayisi <= 2) return 0.95;
        if (parcaSayisi <= 5) return 0.85;
        return 0.75; // 5+ parca
    }
}

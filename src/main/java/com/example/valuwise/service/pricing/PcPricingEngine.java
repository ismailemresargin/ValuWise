package com.example.valuwise.service.pricing;

import com.example.valuwise.model.Pc;

public class PcPricingEngine implements IPricingStrategy<Pc> {
    //Sadece bilgisayar fiyatı hesaplama algoritması
    @Override
    public double tahminiDegerHesapla(Pc pc) {
        // Başlangıç (Taban) fiyatı
        double tabanFiyat = 6000.0;

        // 1. İşlemci Ailesi ve Nesil Hesaplaması
        String islemciAilesi = pc.getIslemciAilesi() != null ? pc.getIslemciAilesi().toLowerCase() : "";
        if (islemciAilesi.contains("i9") || islemciAilesi.contains("ryzen 9") || islemciAilesi.contains("m2 max") || islemciAilesi.contains("m3")) {
            tabanFiyat += 15000;
        } else if (islemciAilesi.contains("i7") || islemciAilesi.contains("ryzen 7") || islemciAilesi.contains("m1 pro") || islemciAilesi.contains("m2 pro")) {
            tabanFiyat += 9000;
        } else if (islemciAilesi.contains("i5") || islemciAilesi.contains("ryzen 5") || islemciAilesi.contains("m1") || islemciAilesi.contains("m2")) {
            tabanFiyat += 5000;
        } else {
            tabanFiyat += 2000;
        }

        int nesil = pc.getIslemciNesli();
        if (nesil >= 12) {
            tabanFiyat += 3000;
        } else if (nesil > 0 && nesil <= 9) {
            tabanFiyat -= 1500;
        }

        // 2. Ekran Kartı Hesaplaması
        String gpu = pc.getEkranKartiModeli() != null ? pc.getEkranKartiModeli().toLowerCase() : "";
        if (gpu.contains("rtx 40") || gpu.contains("rx 7")) {
            tabanFiyat += 18000;
        } else if (gpu.contains("rtx 30") || gpu.contains("rx 6")) {
            tabanFiyat += 10000;
        } else if (gpu.contains("rtx 20") || gpu.contains("gtx 16")) {
            tabanFiyat += 5000;
        } else if (!gpu.contains("dahili") && !gpu.isEmpty()) {
            tabanFiyat += 2000;
        }

        // 3. RAM Kapasitesi ve Tipi
        if (pc.getRamKapasitesiGB() > 8) {
            tabanFiyat += (pc.getRamKapasitesiGB() - 8) * 150;
        }
        String ramTipi = pc.getRamTipi() != null ? pc.getRamTipi().toUpperCase() : "";
        if (ramTipi.contains("DDR5")) {
            tabanFiyat += 1500;
        }

        // 4. Depolama Kapasitesi ve Tipi
        if (pc.getDepolamaKapasitesiGB() > 256) {
            tabanFiyat += (pc.getDepolamaKapasitesiGB() - 256) * 3;
        }
        String depolamaTipi = pc.getDepolamaTipi() != null ? pc.getDepolamaTipi().toUpperCase() : "";
        if (depolamaTipi.contains("NVME")) {
            tabanFiyat += 1000;
        } else if (depolamaTipi.contains("HDD")) {
            tabanFiyat -= 500;
        }

        // 5. Cihaz Tipi ve Batarya Sağlığı
        String cihazTipi = pc.getCihazTipi() != null ? pc.getCihazTipi().toLowerCase() : "";
        if (cihazTipi.equals("dizüstü") || cihazTipi.equals("laptop")) {
            tabanFiyat += 3000;

            if (pc.getBataryaSagligiYuzdesi() > 0 && pc.getBataryaSagligiYuzdesi() < 80) {
                tabanFiyat -= 2500;
            }
        }

        // 6. Cihaz Yaşı ve Amortisman
        double yasDususuOrani = pc.getCihazYasiYil() * 0.10;
        if (yasDususuOrani > 0.70) {
            yasDususuOrani = 0.70;
        }
        tabanFiyat -= (tabanFiyat * yasDususuOrani);

        // 7. Kozmetik Hasar ve Garanti
        if (pc.isKozmetikHasarVarMi()) {
            tabanFiyat *= 0.85;
        }
        if (pc.isGarantisiVarMi()) {
            tabanFiyat += 2500;
        }

        // Sonucu nesneye kaydet ve döndür
        pc.setTahminiDeger(tabanFiyat);
        return tabanFiyat;
    }
}

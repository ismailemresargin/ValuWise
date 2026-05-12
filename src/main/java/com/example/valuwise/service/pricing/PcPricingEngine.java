package com.example.valuwise.service.pricing;

import com.example.valuwise.model.Pc;

public class PcPricingEngine implements IPricingStrategy<Pc> {

    @Override
    public double tahminiDegerHesapla(Pc pc) {
        // 2026 Yılı Revize Edilmiş Taban Fiyat
        double tabanFiyat = 12000.0;

        // --- 0. MARKA VE MODEL ETKİSİ ---
        String marka = pc.getMarka() != null ? pc.getMarka().toLowerCase() : "";
        String model = pc.getModelAilesi() != null ? pc.getModelAilesi().toLowerCase() : "";

        if (marka.contains("apple")) {
            tabanFiyat += 12000;
        } else if (model.contains("rog") || model.contains("legion") || model.contains("alienware") || model.contains("predator") || model.contains("titan") || model.contains("blade")) {
            tabanFiyat += 10000;
        } else if (model.contains("workstation") || model.contains("zbook") || model.contains("precision") || model.contains("thinkpad") || model.contains("xps") || model.contains("zenbook")) {
            tabanFiyat += 8000;
        } else if (model.contains("tuf") || model.contains("victus") || model.contains("nitro") || model.contains("loq") || model.contains("abra")) {
            tabanFiyat += 3000;
        }

        // --- 1. İŞLEMCİ (CPU) HESAPLAMASI ---
        String islemciAilesi = pc.getIslemciAilesi() != null ? pc.getIslemciAilesi().toLowerCase() : "";

        if (islemciAilesi.contains("i9") || islemciAilesi.contains("ryzen 9") || islemciAilesi.contains("ultra 9") || islemciAilesi.contains("threadripper") || islemciAilesi.contains("max") || islemciAilesi.contains("ultra")) {
            tabanFiyat += 30000;
        } else if (islemciAilesi.contains("i7") || islemciAilesi.contains("ryzen 7") || islemciAilesi.contains("ultra 7") || islemciAilesi.contains("pro")) {
            tabanFiyat += 15000;
        } else if (islemciAilesi.contains("i5") || islemciAilesi.contains("ryzen 5") || islemciAilesi.contains("ultra 5") || islemciAilesi.contains("m1") || islemciAilesi.contains("m2") || islemciAilesi.contains("m3")) {
            tabanFiyat += 7000;
        } else if (islemciAilesi.contains("i3") || islemciAilesi.contains("ryzen 3")) {
            tabanFiyat += 3000;
        }

        // --- 2. NESİL / SERİ ETKİSİ ---
        int nesil = pc.getIslemciNesli();
        if (nesil == 0) {
            if (islemciAilesi.contains("m1") || islemciAilesi.contains("m2") || islemciAilesi.contains("m3")) {
                tabanFiyat += 3000;
            } else {
                tabanFiyat -= 3000;
            }
        } else if (nesil < 100) { // INTEL
            if (nesil >= 14) tabanFiyat += 8000;
            else if (nesil == 13) tabanFiyat += 4000;
            else if (nesil == 12) tabanFiyat += 1000;
            else if (nesil <= 9) tabanFiyat -= 4000;
        } else { // AMD
            if (nesil >= 9000) tabanFiyat += 8000;
            else if (nesil >= 7000) tabanFiyat += 4000;
            else if (nesil >= 5000) tabanFiyat += 1000;
            else if (nesil <= 4000) tabanFiyat -= 4000;
        }

        // --- 3. EKRAN KARTI (GPU) HESAPLAMASI ---
        String gpu = pc.getEkranKartiModeli() != null ? pc.getEkranKartiModeli().toLowerCase() : "";

        if (gpu.contains("dahili")) {
            tabanFiyat += 0;
        } else if (gpu.contains("4090") || gpu.contains("7900")) {
            tabanFiyat += 85000;
        } else if (gpu.contains("4080") || gpu.contains("4070 ti") || gpu.contains("7800") || gpu.contains("3090")) {
            tabanFiyat += 50000;
        } else if (gpu.contains("4070") || gpu.contains("3080") || gpu.contains("7700") || gpu.contains("6900") || gpu.contains("6950")) {
            tabanFiyat += 30000;
        } else if (gpu.contains("4060") || gpu.contains("3070") || gpu.contains("6800") || gpu.contains("6700") || gpu.contains("7600")) {
            tabanFiyat += 18000;
        } else if (gpu.contains("4050") || gpu.contains("3060") || gpu.contains("2080") || gpu.contains("2070") || gpu.contains("6600") || gpu.contains("a770")) {
            tabanFiyat += 10000;
        } else if (gpu.contains("3050") || gpu.contains("2060") || gpu.contains("1660") || gpu.contains("1650") || gpu.contains("a750")) {
            tabanFiyat += 5000;
        } else if (!gpu.contains("seçiniz") && !gpu.isEmpty()) {
            tabanFiyat += 2000;
        }

        // --- 4. RAM KAPASİTESİ VE TİPİ ---
        if (pc.getRamKapasitesiGB() > 8) {
            tabanFiyat += (pc.getRamKapasitesiGB() - 8) * 300; // GB başına maliyet 300 TL'ye çekildi
        }

        String ramTipi = pc.getRamTipi() != null ? pc.getRamTipi().toUpperCase() : "";
        if (ramTipi.contains("DDR5") || ramTipi.contains("LPDDR5")) {
            tabanFiyat += 2500;
        } else if (ramTipi.contains("DDR3")) {
            tabanFiyat -= 1500;
        }

        // --- 5. DEPOLAMA KAPASİTESİ VE TİPİ ---
        if (pc.getDepolamaKapasitesiGB() > 256) {
            tabanFiyat += (pc.getDepolamaKapasitesiGB() - 256) * 4.0; // GB başına maliyet 4.0 TL'ye çekildi
        }

        String depolamaTipi = pc.getDepolamaTipi() != null ? pc.getDepolamaTipi().toLowerCase() : "";
        if (depolamaTipi.contains("gen4") || depolamaTipi.contains("gen5")) {
            tabanFiyat += 2000;
        } else if (depolamaTipi.contains("sata")) {
            tabanFiyat -= 1000;
        } else if (depolamaTipi.contains("hdd")) {
            tabanFiyat -= 2000;
        }

        // --- 6. CİHAZ TİPİ VE BATARYA SAĞLIĞI ---
        String cihazTipi = pc.getCihazTipi() != null ? pc.getCihazTipi().toLowerCase() : "";
        if (cihazTipi.contains("dizüstü")) {
            tabanFiyat += 5000;

            int batarya = pc.getBataryaSagligiYuzdesi();
            if (batarya <= 0) {
                tabanFiyat -= 5000;
            } else if (batarya < 80) {
                tabanFiyat -= 2500;
            }
        }

        // --- 7. AMORTİSMAN (Yıpranma Payı) VE KOZMETİK ---
        int yas = pc.getCihazYasiYil();
        double yasDususuOrani = 0.0;

        if (yas == 1) yasDususuOrani = 0.15;
        else if (yas == 2) yasDususuOrani = 0.25;
        else if (yas == 3) yasDususuOrani = 0.35;
        else if (yas > 3) yasDususuOrani = 0.35 + ((yas - 3) * 0.08);

        if (yasDususuOrani > 0.70) yasDususuOrani = 0.70;

        tabanFiyat -= (tabanFiyat * yasDususuOrani);

        // --- 8. SON DOKUNUŞLAR ---
        if (pc.isKozmetikHasarVarMi()) tabanFiyat *= 0.85;
        if (pc.isGarantisiVarMi()) tabanFiyat += 4000;

        if (tabanFiyat < 3000) tabanFiyat = 3000;

        pc.setTahminiDeger(tabanFiyat);
        return tabanFiyat;
    }
}
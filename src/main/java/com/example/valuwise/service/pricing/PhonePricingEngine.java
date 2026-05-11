package com.example.valuwise.service.pricing;

import com.example.valuwise.model.Phone;

public class PhonePricingEngine implements IPricingStrategy<Phone> {

    // Fiyat aralığı için alt/üst çarpanlar (%10 sapma)
    public static final double ALT_CARPAN = 0.90;
    public static final double UST_CARPAN = 1.10;

    @Override
    public double tahminiDegerHesapla(Phone phone) {
        return tahminiDegerHesapla(phone, 15000.0, 0.0, false, false, 50, 10, 0);
    }

    public double tahminiDegerHesapla(Phone phone, double tabanFiyat) {
        return tahminiDegerHesapla(phone, tabanFiyat, 0.0, false, false, 50, 10, 0);
    }

    /**
     * Hesaplamayı ve her adımın etkisini döndüren tam metod.
     * @return sonuç fiyatı
     */
    public double tahminiDegerHesapla(Phone phone, double tabanFiyat,
                                       double hasarKesinti,
                                       boolean besG, boolean hizliSarj,
                                       int arkaKameraMP, int onKameraMP,
                                       int kalanGarantiAy) {
        return hesapla(phone, tabanFiyat, hasarKesinti, besG, hizliSarj, arkaKameraMP, onKameraMP, kalanGarantiAy, null);
    }

    /**
     * Faktör detaylarını da doldurarak hesaplar.
     * @param faktorler boş liste; metod içinde doldurulur
     */
    public double tahminiDegerHesaplaDetayli(Phone phone, double tabanFiyat,
                                              double hasarKesinti,
                                              boolean besG, boolean hizliSarj,
                                              int arkaKameraMP, int onKameraMP,
                                              int kalanGarantiAy,
                                              java.util.List<String> faktorler) {
        return hesapla(phone, tabanFiyat, hasarKesinti, besG, hizliSarj, arkaKameraMP, onKameraMP, kalanGarantiAy, faktorler);
    }

    private double hesapla(Phone phone, double fiyat,
                            double hasarKesinti, boolean besG, boolean hizliSarj,
                            int arkaKameraMP, int onKameraMP, int kalanGarantiAy,
                            java.util.List<String> faktorler) {

        double baslangic = fiyat;

        // 1. DEPOLAMA
        int dep = phone.getDepolamaKapasitesi();
        double depCarpan = 1.0;
        if      (dep >= 1024) depCarpan = 1.22;
        else if (dep >= 512)  depCarpan = 1.15;
        else if (dep >= 256)  depCarpan = 1.08;
        else if (dep <= 64)   depCarpan = 0.90;
        fiyat *= depCarpan;
        if (faktorler != null && depCarpan != 1.0)
            faktorler.add(String.format("Depolama (%dGB): %s%.0f ₺",
                dep, depCarpan > 1 ? "+" : "", fiyat - (fiyat / depCarpan)));

        // 2. YAŞ
        int ay = Math.max(0, phone.getCihazYasiAy());
        double yasK;
        if      (ay <= 1)  yasK = 1.28;
        else if (ay <= 3)  yasK = 1.18;
        else if (ay <= 6)  yasK = 1.08;
        else if (ay <= 9)  yasK = 1.03;
        else if (ay <= 12) yasK = 1.00;
        else if (ay <= 24) yasK = 1.00 - ((ay - 12) * 0.015);
        else if (ay <= 36) yasK = 0.82 - ((ay - 24) * 0.010);
        else if (ay <= 48) yasK = 0.70 - ((ay - 36) * 0.006);
        else               yasK = 0.61 - Math.min((ay - 48) * 0.004, 0.20);
        yasK = Math.max(yasK, 0.25);
        double onceki = fiyat;
        fiyat *= yasK;
        if (faktorler != null)
            faktorler.add(String.format("Cihaz yaşı (%d ay): %.0f ₺", ay, fiyat - onceki));

        // 3. PIL
        int pil = phone.getPilSagligi();
        double pilK = 1.0;
        if      (pil >= 95) pilK = 1.04;
        else if (pil >= 90) pilK = 1.00;
        else if (pil >= 85) pilK = 0.95;
        else if (pil >= 80) pilK = 0.89;
        else if (pil >= 75) pilK = 0.82;
        else if (pil >= 70) pilK = 0.73;
        else                pilK = 0.62;
        onceki = fiyat;
        fiyat *= pilK;
        if (faktorler != null && pilK != 1.0)
            faktorler.add(String.format("Pil sağlığı (%d%%): %s%.0f ₺",
                pil, pilK > 1 ? "+" : "", fiyat - onceki));

        // 4. GARANTİ
        if (phone.isGarantisiVarMi()) {
            double gK;
            if      (kalanGarantiAy >= 24) gK = 1.15;
            else if (kalanGarantiAy >= 19) gK = 1.11;
            else if (kalanGarantiAy >= 13) gK = 1.08;
            else if (kalanGarantiAy >= 7)  gK = 1.05;
            else if (kalanGarantiAy >= 4)  gK = 1.03;
            else                           gK = 1.01;
            onceki = fiyat;
            fiyat *= gK;
            if (faktorler != null)
                faktorler.add(String.format("Garanti (%d ay): +%.0f ₺", kalanGarantiAy, fiyat - onceki));
        }

        // 5. KUTU/FATURA
        if (phone.isKutuFaturaVarMi()) {
            onceki = fiyat;
            fiyat *= 1.04;
            if (faktorler != null)
                faktorler.add(String.format("Kutu/Fatura: +%.0f ₺", fiyat - onceki));
        }

        // 6. TEKNOLOJİ
        if (besG) {
            onceki = fiyat; fiyat *= 1.03;
            if (faktorler != null) faktorler.add(String.format("5G: +%.0f ₺", fiyat - onceki));
        }
        if (hizliSarj) {
            onceki = fiyat; fiyat *= 1.02;
            if (faktorler != null) faktorler.add(String.format("Hızlı Şarj: +%.0f ₺", fiyat - onceki));
        }

        // 7. KAMERA
        double kamK = 1.0;
        if      (arkaKameraMP >= 200) kamK = 1.05;
        else if (arkaKameraMP >= 108) kamK = 1.03;
        else if (arkaKameraMP >= 50)  kamK = 1.01;
        if (onKameraMP >= 32) kamK *= 1.02;
        if (kamK != 1.0) {
            onceki = fiyat; fiyat *= kamK;
            if (faktorler != null)
                faktorler.add(String.format("Kamera (%d MP): +%.0f ₺", arkaKameraMP, fiyat - onceki));
        }

        // 8. HASAR/TAMİR
        if (phone.isTamirGorduMu()) {
            onceki = fiyat; fiyat *= 0.75;
            if (faktorler != null) faktorler.add(String.format("Tamir gördü: %.0f ₺", fiyat - onceki));
        }
        if (phone.isKozmetikHasarVarMi()) {
            onceki = fiyat; fiyat *= 0.90;
            if (faktorler != null) faktorler.add(String.format("Kozmetik hasar: %.0f ₺", fiyat - onceki));
        }
        if (hasarKesinti > 0) {
            onceki = fiyat; fiyat *= (1.0 - Math.min(hasarKesinti, 0.65));
            if (faktorler != null) faktorler.add(String.format("Hasar/Tamir kalemleri: %.0f ₺", fiyat - onceki));
        }

        // 9. İMEİ
        if (!phone.isImeiKaydiVarMi()) {
            onceki = fiyat; fiyat *= 0.38;
            if (faktorler != null) faktorler.add(String.format("İMEİ kayıtsız: %.0f ₺", fiyat - onceki));
        }

        // 10. YUVARLAMA
        fiyat = fiyat < 5000
            ? Math.round(fiyat / 100.0) * 100.0
            : Math.round(fiyat / 500.0) * 500.0;

        phone.setTahminiDeger(fiyat);
        return fiyat;
    }
}

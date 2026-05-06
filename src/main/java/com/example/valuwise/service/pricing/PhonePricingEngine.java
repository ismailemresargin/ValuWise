package com.example.valuwise.service.pricing;

import com.example.valuwise.model.Phone;

/**
 * Sahibinden.com ikinci el fiyat hesaplama motoru — Mayıs 2025
 *
 * YAKLAŞIM:
 * TELEFON_DB'deki taban fiyat = o modelin sahibinden'deki GERÇEK ikinci el
 * ortalama fiyatı (128 GB, temiz durum, IMEI kayıtlı, ~12 aylık cihaz).
 *
 * Motor bu tabanı kullanıcının girdiği koşullara göre düzeltir:
 *   - Cihaz 12 aydan GENÇ → fiyat yukarı
 *   - Cihaz 12 aydan YAŞLI → fiyat aşağı
 *   - Depolama, pil, hasar, garanti vb. → ek düzeltme
 */
public class PhonePricingEngine implements IPricingStrategy<Phone> {

    @Override
    public double tahminiDegerHesapla(Phone phone) {
        return tahminiDegerHesapla(phone, 15000.0, 0.0, false, false, 50, 10, 0);
    }

    public double tahminiDegerHesapla(Phone phone, double tabanFiyat) {
        return tahminiDegerHesapla(phone, tabanFiyat, 0.0, false, false, 50, 10, 0);
    }

    public double tahminiDegerHesapla(Phone phone, double tabanFiyat,
                                       double hasarKesinti,
                                       boolean besG, boolean hizliSarj,
                                       int arkaKameraMP, int onKameraMP,
                                       int kalanGarantiAy) {

        // ── 1. DEPOLAMA DÜZELTME ─────────────────────────────────
        // Sahibinden fiyat farkı gözlemi: 256GB ~%8, 512GB ~%15, 1TB ~%22 prim
        int dep = phone.getDepolamaKapasitesi();
        if      (dep >= 1024) tabanFiyat *= 1.22;
        else if (dep >= 512)  tabanFiyat *= 1.15;
        else if (dep >= 256)  tabanFiyat *= 1.08;
        else if (dep <= 64)   tabanFiyat *= 0.90;
        // 128 GB = referans, değişiklik yok

        // ── 2. YAŞ DÜZELTME ─────────────────────────────────────
        // Taban fiyat ~12 aylık cihaz için. Daha genç/yaşlı cihazlar düzeltilir.
        // Sahibinden'de gözlemlenen değer/ay değişimi:
        //   0-6 ay:   taban × 1.30 → 1.00  (yeni cihaz sıfıra yakın)
        //   6-12 ay:  taban × 1.00 → 1.00  (referans bölge)
        //  12-24 ay:  aylık ~%1.5 ekstra düşüş
        //  24-36 ay:  aylık ~%1.0 ekstra düşüş
        //  36+ ay:    aylık ~%0.6 ekstra düşüş, min %25
        int ay = Math.max(0, phone.getCihazYasiAy());
        double yasKatsayisi;
        if (ay <= 1) {
            yasKatsayisi = 1.28;  // neredeyse sıfır
        } else if (ay <= 3) {
            yasKatsayisi = 1.18;
        } else if (ay <= 6) {
            yasKatsayisi = 1.08;
        } else if (ay <= 9) {
            yasKatsayisi = 1.03;
        } else if (ay <= 12) {
            yasKatsayisi = 1.00;  // referans nokta
        } else if (ay <= 24) {
            yasKatsayisi = 1.00 - ((ay - 12) * 0.015);  // 12-24 ay
        } else if (ay <= 36) {
            yasKatsayisi = 0.82 - ((ay - 24) * 0.010);  // 24-36 ay
        } else if (ay <= 48) {
            yasKatsayisi = 0.70 - ((ay - 36) * 0.006);  // 36-48 ay
        } else {
            yasKatsayisi = 0.61 - Math.min((ay - 48) * 0.004, 0.20); // 4 yıl+
        }
        tabanFiyat *= Math.max(yasKatsayisi, 0.25); // minimum %25

        // ── 3. PİL SAĞLIĞI ───────────────────────────────────────
        // Sahibinden'de %90+ pil ciddi fark yaratıyor, özellikle Apple'da.
        // Pil değişim maliyeti ~1500-3000 TL direkt fiyata yansır.
        int pil = phone.getPilSagligi();
        if      (pil >= 95) tabanFiyat *= 1.04;   // "mükemmel pil" avantajı
        else if (pil >= 90) tabanFiyat *= 1.00;   // standart
        else if (pil >= 85) tabanFiyat *= 0.95;
        else if (pil >= 80) tabanFiyat *= 0.89;   // alıcı pil değişim maliyeti düşer
        else if (pil >= 75) tabanFiyat *= 0.82;
        else if (pil >= 70) tabanFiyat *= 0.73;
        else                tabanFiyat *= 0.62;   // %70 altı: pil + güven sorunu

        // ── 4. GARANTİ ───────────────────────────────────────────
        // Garantili telefon sahibinden'de gerçek fark yaratıyor
        if (phone.isGarantisiVarMi()) {
            if      (kalanGarantiAy >= 24) tabanFiyat *= 1.15;
            else if (kalanGarantiAy >= 19) tabanFiyat *= 1.11;
            else if (kalanGarantiAy >= 13) tabanFiyat *= 1.08;
            else if (kalanGarantiAy >= 7)  tabanFiyat *= 1.05;
            else if (kalanGarantiAy >= 4)  tabanFiyat *= 1.03;
            else                           tabanFiyat *= 1.01;
        }

        // ── 5. KUTU / FATURA ─────────────────────────────────────
        if (phone.isKutuFaturaVarMi()) tabanFiyat *= 1.04;

        // ── 6. TEKNOLOJİ ─────────────────────────────────────────
        if (besG)      tabanFiyat *= 1.03;
        if (hizliSarj) tabanFiyat *= 1.02;

        // ── 7. KAMERA ────────────────────────────────────────────
        if      (arkaKameraMP >= 200) tabanFiyat *= 1.05;
        else if (arkaKameraMP >= 108) tabanFiyat *= 1.03;
        else if (arkaKameraMP >= 50)  tabanFiyat *= 1.01;
        if      (onKameraMP >= 32)    tabanFiyat *= 1.02;

        // ── 8. HASAR / TAMİR ─────────────────────────────────────
        // Sahibinden'de tamir görmüş cihaz çok zor satılır, alıcılar ısrarcı iner
        if (phone.isTamirGorduMu())       tabanFiyat *= 0.75;
        if (phone.isKozmetikHasarVarMi()) tabanFiyat *= 0.90;

        // Detaylı hasar kalemleri (CheckComboBox'tan)
        if (hasarKesinti > 0) {
            tabanFiyat *= (1.0 - Math.min(hasarKesinti, 0.65));
        }

        // ── 9. İMEİ KAYDI ────────────────────────────────────────
        // Türkiye'de kayıtsız = fiilen kullanılamaz → çok düşük fiyat
        if (!phone.isImeiKaydiVarMi()) {
            tabanFiyat *= 0.38;
        }

        // ── 10. YUVARLAMA ────────────────────────────────────────
        // 500 TL'nin altında ise 100 TL'ye, üstünde 500 TL'ye yuvarla
        if (tabanFiyat < 5000) {
            tabanFiyat = Math.round(tabanFiyat / 100.0) * 100.0;
        } else {
            tabanFiyat = Math.round(tabanFiyat / 500.0) * 500.0;
        }

        phone.setTahminiDeger(tabanFiyat);
        return tabanFiyat;
    }
}

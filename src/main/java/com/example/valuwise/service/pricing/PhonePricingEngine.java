//Sadece telefon fiyatı hesaplama algoritması
package com.example.valuwise.service.pricing;

import com.example.valuwise.model.Phone;

public class PhonePricingEngine implements IPricingStrategy<Phone> {

    @Override
    public double tahminiDegerHesapla(Phone phone) {
        // Başlangıç fiyatı (Model ve Markaya göre normalde DB'den gelir, şimdilik sabit)
        double tabanFiyat = 15000.0;

        // Depolama kapasitesine göre ekle (Örn: 128GB üstü her GB için 15 TL)
        if (phone.getDepolamaKapasitesi() > 128) {
            tabanFiyat += (phone.getDepolamaKapasitesi() - 128) * 15;
        }

        // Cihaz yaşına göre değer kaybı (Her ay için %2 düşüş)
        tabanFiyat -= (phone.getCihazYasiAy() * 0.02) * tabanFiyat;

        // Pil sağlığı kritik bir veri
        if (phone.getPilSagligi() < 85) {
            tabanFiyat -= 2000; // Pil değişimi gerektirebilir
        }

        // Bonuslar ve Kesintiler
        if (phone.isGarantisiVarMi()) tabanFiyat += 1500;
        if (phone.isKutuFaturaVarMi()) tabanFiyat += 500;
        if (phone.isTamirGorduMu()) tabanFiyat *= 0.75; // %25 büyük kesinti
        if (phone.isKozmetikHasarVarMi()) tabanFiyat *= 0.90; // %10 kesinti

        // IMEI Kaydı yoksa telefonun değeri %60 düşer (Önemli bir Türkiye gerçeği)
        if (!phone.isImeiKaydiVarMi()) {
            tabanFiyat *= 0.40;
        }

        phone.setTahminiDeger(tabanFiyat);
        return tabanFiyat;
    }
}
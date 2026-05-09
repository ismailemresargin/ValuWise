package com.example.valuwise.service.pricing;

import com.example.valuwise.model.House;
import com.example.valuwise.util.BolgeFiyatManager;

public class HousePricingEngine {
    public double tahminiDegerHesapla(House house) {
        // konut tipine gore baslangic m2 fiyati (baz fiyat)
        double m2BirimFiyati = BolgeFiyatManager.getBirimFiyat(house.getAdres(), house.getIlce()); // standart daire baz fiyati

        switch (house.getKonutTipi()) {
            case "Villa": m2BirimFiyati *= 2.2; break;
            case "Yalı / Yalı Dairesi": m2BirimFiyati *= 4.5; break;
            case "Rezidans": m2BirimFiyati *= 1.5; break;
            case "Müstakil Ev": m2BirimFiyati *= 1.3; break;
            case "Gecekondu": m2BirimFiyati *= 0.5; break;
            case "Prefabrik Ev": m2BirimFiyati *= 0.6; break;
            // Daire, yazlik vb. icin carpan 1.0 kalir, lokasyon fiyati neyse o olur.
        }

        double tabanFiyat = house.getMetrekare() * m2BirimFiyati;

        // oda ve banyo bonusu
        // her oda icin fiyata 150.000 tl, her banyo icin 75.000 tl ekliyoruz
        tabanFiyat += (house.getOdaSayisi() * 150000);
        tabanFiyat += (house.getBanyoSayisi() * 75000);

        // isinma tipi carpani
        switch (house.getIsinmaTipi()) {
            case "Kombi (Doğalgaz)": tabanFiyat *= 1.15; break; // dogalgaz %15 deger katar
            case "Merkezi": tabanFiyat *= 1.10; break;
            case "Soba": tabanFiyat *= 0.85; break; // soba fiyati %15 dusurur
            case "Klima": tabanFiyat *= 0.95; break;
        }

        //kat avantaji
        // giris kat ise %10 ucuz, ara katsa (1-5 arasi) %10 pahali
        if (house.getBulunduguKat() == 0) tabanFiyat *= 0.90;
        else if (house.getBulunduguKat() > 0 && house.getBulunduguKat() < 6) tabanFiyat *= 1.10;
        else if (house.getBulunduguKat() >= 6) tabanFiyat *= 1.15; // 6 ve ustu ekstra degerli

        // kredi ve balkon etkisi
        if (house.isKrediyeUygunMu()) tabanFiyat *= 1.08; // krediye uygunluk %8 deger katar
        if (house.isBalkonVarMi()) tabanFiyat += 100000;

        // deprem dayanikliligi
        if (house.isDepremeDayanikliMi()) tabanFiyat *= 1.25;

        // her yil icin %1.5 deger kaybi
        double yipranmaOrani = house.getBinaYasi() * 0.015;
        tabanFiyat = tabanFiyat * (1 - yipranmaOrani);

        //ekstra ozellikler (sabit artislar)
        if (house.isDenizManzarasiVarMi()) tabanFiyat += 600000;
        if (house.isOtoparkVarMi()) tabanFiyat += 150000;
        if (house.isAsansorVarMi()) tabanFiyat += 100000;

        // degerin cok sacma (eksi veya cok dusuk) cikmamasi icin alt sinir
        if (tabanFiyat < 0) tabanFiyat = 0;

        house.setTahminiDeger(tabanFiyat);
        return tabanFiyat;
    }
}
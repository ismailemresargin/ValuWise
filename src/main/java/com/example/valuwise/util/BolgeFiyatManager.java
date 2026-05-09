package com.example.valuwise.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.util.Map;

public class BolgeFiyatManager {
    private static Map<String, Map<String, Double>> fiyatDeposu;

    static {
        try {
            ObjectMapper mapper = new ObjectMapper();
            // dosyayi ariyoruz
            InputStream is = BolgeFiyatManager.class.getResourceAsStream("/fiyatlar.json");
            if (is == null) {
                is = BolgeFiyatManager.class.getResourceAsStream("/com/example/valuwise/fiyatlar.json");
            }

            if (is != null) {
                fiyatDeposu = mapper.readValue(is, new TypeReference<Map<String, Map<String, Double>>>() {});
                System.out.println("BAŞARI: Fiyatlar yüklendi.");
            } else {
                System.err.println("HATA: fiyatlar.json bulunamadı!");
            }
        } catch (Exception e) {
            System.err.println("Fiyat JSON okuma hatası!");
            e.printStackTrace();
        }
    }

    public static double getBirimFiyat(String sehir, String ilce) {
        if (fiyatDeposu == null) return 22000.0; // dosya okunamazsa dondur

        Map<String, Double> sehirFiyatlari = fiyatDeposu.get(sehir);
        if (sehirFiyatlari != null && sehirFiyatlari.containsKey(ilce)) {
            // arayuzden secilen sehir ve ilce eslesirse fiyati don
            return sehirFiyatlari.get(ilce);
        }

        // arayuz kilitli oldugu icin normalde buraya hic dusmeyecek
        return 22000.0;
    }
}
package com.example.valuwise.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.util.*;

public class SehirVerileri {
    private static Map<String, List<String>> veriDeposu = new HashMap<>();

    static {
        try {
            ObjectMapper mapper = new ObjectMapper();
            // Dosyayı her iki ihtimale karşı arıyoruz (Hem /'li hem /'siz)
            InputStream is = SehirVerileri.class.getResourceAsStream("/iller.json");
            if (is == null) {
                is = SehirVerileri.class.getResourceAsStream("/com/example/valuwise/iller.json");
            }

            if (is != null) {
                veriDeposu = mapper.readValue(is, new TypeReference<Map<String, List<String>>>() {});
                System.out.println("BAŞARI: JSON yüklendi. İl sayısı: " + veriDeposu.size());
            } else {
                System.err.println("KRİTİK HATA: iller.json dosyası hiçbir yerde bulunamadı!");
            }
        } catch (Exception e) {
            System.err.println("JSON OKUMA HATASI: Format bozuk olabilir!");
            e.printStackTrace(); // Bu satır bize hatanın tam yerini söyleyecek
        }
    }

    public static List<String> getIller() {
        if (veriDeposu.isEmpty()) {
            // Eğer JSON okunamazsa uygulama çökmesin diye geçici veri
            return new ArrayList<>(Arrays.asList("Veri Yuklenemedi", "Trabzon", "Istanbul"));
        }
        List<String> iller = new ArrayList<>(veriDeposu.keySet());
        Collections.sort(iller);
        return iller;
    }

    public static List<String> getIlceler(String il) {
        return veriDeposu.getOrDefault(il, Collections.singletonList("Merkez"));
    }
}
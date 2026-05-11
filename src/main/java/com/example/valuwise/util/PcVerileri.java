package com.example.valuwise.util;

import java.util.*;

public class PcVerileri {
    private static final Map<String, List<String>> MARKA_MODEL_MAP = new LinkedHashMap<>();
    private static final Map<String, List<String>> CPU_MARKA_AILE_MAP = new LinkedHashMap<>();

    static {
        // Marka ve Model Grupları
        MARKA_MODEL_MAP.put("Apple", Arrays.asList("MacBook Air", "MacBook Pro", "iMac", "Mac Mini", "Mac Studio"));
        MARKA_MODEL_MAP.put("Asus", Arrays.asList("ROG Serisi", "TUF Serisi", "ZenBook", "VivoBook", "ExpertBook"));
        MARKA_MODEL_MAP.put("Lenovo", Arrays.asList("Legion", "LOQ", "ThinkPad", "IdeaPad", "Yoga"));
        MARKA_MODEL_MAP.put("HP", Arrays.asList("Omen", "Victus", "Pavilion", "EliteBook", "ZBook"));
        MARKA_MODEL_MAP.put("Dell", Arrays.asList("Alienware", "XPS", "Latitude", "Inspiron", "G Serisi"));
        MARKA_MODEL_MAP.put("MSI", Arrays.asList("Titan/Raider", "Stealth", "Katana/Cyborg", "Prestige", "Modern"));
        MARKA_MODEL_MAP.put("Monster", Arrays.asList("Abra", "Tulpar", "Semruk", "Huma", "Markut"));
        MARKA_MODEL_MAP.put("Acer", Arrays.asList("Predator", "Nitro", "Swift", "Aspire"));
        MARKA_MODEL_MAP.put("Diğer / Toplama", Arrays.asList("Oyun Kasası", "Ofis Kasası", "Mini PC / NUC", "All-in-One"));

        // İşlemci Marka ve Aileleri
        CPU_MARKA_AILE_MAP.put("Intel", Arrays.asList("Core i9", "Core i7", "Core i5", "Core i3", "Core Ultra", "Xeon", "Pentium"));
        CPU_MARKA_AILE_MAP.put("AMD", Arrays.asList("Ryzen 9", "Ryzen 7", "Ryzen 5", "Ryzen 3", "Threadripper", "Athlon"));
        CPU_MARKA_AILE_MAP.put("Apple", Arrays.asList("M3 Max/Ultra", "M3 Pro", "M3", "M2 Serisi", "M1 Serisi"));
    }

    // İşlemci Markasına Göre Nesil/Seri Veritabanı
    private static final Map<String, List<String>> CPU_NESIL_MAP = new LinkedHashMap<>();
    static {
        CPU_NESIL_MAP.put("Intel", Arrays.asList("14. Nesil", "13. Nesil", "12. Nesil", "11. Nesil", "10. Nesil", "9. Nesil ve altı"));
        CPU_NESIL_MAP.put("AMD", Arrays.asList("Ryzen 9000 Serisi", "Ryzen 8000/7000 Serisi", "Ryzen 6000/5000 Serisi", "Ryzen 4000/3000 Serisi", "Eski Nesil"));
        CPU_NESIL_MAP.put("Apple", Arrays.asList("Apple Silicon (M Serisi)"));
    }

    // Bu metodu da sınıfın en altına (diğer getter'ların yanına) ekle
    public static List<String> getCpuNesilleri(String marka) {
        return CPU_NESIL_MAP.getOrDefault(marka, Collections.emptyList());
    }

    public static Set<String> getMarkalar() { return MARKA_MODEL_MAP.keySet(); }
    public static List<String> getModeller(String marka) { return MARKA_MODEL_MAP.getOrDefault(marka, Collections.emptyList()); }

    public static Set<String> getCpuMarkalar() { return CPU_MARKA_AILE_MAP.keySet(); }
    public static List<String> getCpuAileleri(String marka) { return CPU_MARKA_AILE_MAP.getOrDefault(marka, Collections.emptyList()); }

    // Ekran Kartları Listesi - Üst/Alt segment ayrımı yapılarak detaylandırıldı
    public static List<String> getEkranKartlari() {
        return Arrays.asList(
                "Seçiniz",
                "Dahili (Intel Iris/UHD, AMD Radeon, Apple M-Core)",

                // NVIDIA RTX 40 Serisi
                "NVIDIA RTX 4090",
                "NVIDIA RTX 4080 / 4080 Super",
                "NVIDIA RTX 4070 Ti / 4070 Ti Super",
                "NVIDIA RTX 4070 / 4070 Super",
                "NVIDIA RTX 4060 Ti",
                "NVIDIA RTX 4060",
                "NVIDIA RTX 4050 (Mobil/Laptop)",

                // NVIDIA RTX 30 Serisi
                "NVIDIA RTX 3090 / 3090 Ti",
                "NVIDIA RTX 3080 / 3080 Ti",
                "NVIDIA RTX 3070 / 3070 Ti",
                "NVIDIA RTX 3060 Ti",
                "NVIDIA RTX 3060",
                "NVIDIA RTX 3050 / 3050 Ti",

                // NVIDIA RTX 20 Serisi
                "NVIDIA RTX 2080 / 2080 Ti / 2080 Super",
                "NVIDIA RTX 2070 / 2070 Super",
                "NVIDIA RTX 2060 / 2060 Super",

                // NVIDIA GTX 16 ve Eski Nesil
                "NVIDIA GTX 1660 / 1660 Ti / 1660 Super",
                "NVIDIA GTX 1650 / 1650 Ti",
                "NVIDIA GTX 1080 / 1080 Ti / 1070",
                "NVIDIA GTX 1060 / 1050 Ti",

                // NVIDIA Profesyonel / Workstation
                "NVIDIA Quadro / RTX (A-Serisi, T-Serisi vb.)",

                // AMD Radeon RX 7000 Serisi
                "AMD Radeon RX 7900 XTX / XT / GRE",
                "AMD Radeon RX 7800 XT",
                "AMD Radeon RX 7700 XT",
                "AMD Radeon RX 7600 / 7600 XT",

                // AMD Radeon RX 6000 Serisi
                "AMD Radeon RX 6950 XT / 6900 XT",
                "AMD Radeon RX 6800 / 6800 XT",
                "AMD Radeon RX 6700 / 6700 XT / 6750 XT",
                "AMD Radeon RX 6600 / 6600 XT / 6650 XT",
                "AMD Radeon RX 6500 XT / 6400",

                // Intel Harici Kartlar
                "Intel Arc A770 / A750",
                "Intel Arc A580 / A380"
        );
    }
}
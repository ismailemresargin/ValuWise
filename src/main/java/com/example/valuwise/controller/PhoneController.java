package com.example.valuwise.controller;

import com.example.valuwise.model.Phone;
import com.example.valuwise.service.pricing.PhonePricingEngine;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.*;
import org.controlsfx.control.CheckComboBox;
import javafx.collections.ObservableList;

public class PhoneController {


    /**
     * Model → [arkaKameraMP, onKameraMP]
     * Resmi teknik özelliklerden alınmıştır.
     */
    private static final Map<String, int[]> KAMERA_DB = new LinkedHashMap<>();
    static {
        // Apple
        KAMERA_DB.put("iPhone 11",          new int[]{12, 12});
        KAMERA_DB.put("iPhone 11 Pro",      new int[]{12, 12});
        KAMERA_DB.put("iPhone 12",          new int[]{12, 12});
        KAMERA_DB.put("iPhone 12 Pro",      new int[]{12, 12});
        KAMERA_DB.put("iPhone 12 Pro Max",  new int[]{12, 12});
        KAMERA_DB.put("iPhone 13",          new int[]{12, 12});
        KAMERA_DB.put("iPhone 13 Mini",     new int[]{12, 12});
        KAMERA_DB.put("iPhone 13 Pro",      new int[]{12, 12});
        KAMERA_DB.put("iPhone 13 Pro Max",  new int[]{12, 12});
        KAMERA_DB.put("iPhone 14",          new int[]{12, 12});
        KAMERA_DB.put("iPhone 14 Plus",     new int[]{12, 12});
        KAMERA_DB.put("iPhone 14 Pro",      new int[]{48, 12});
        KAMERA_DB.put("iPhone 14 Pro Max",  new int[]{48, 12});
        KAMERA_DB.put("iPhone 15",          new int[]{48, 12});
        KAMERA_DB.put("iPhone 15 Plus",     new int[]{48, 12});
        KAMERA_DB.put("iPhone 15 Pro",      new int[]{48, 12});
        KAMERA_DB.put("iPhone 15 Pro Max",  new int[]{48, 12});
        KAMERA_DB.put("iPhone 16",          new int[]{48, 12});
        KAMERA_DB.put("iPhone 16 Plus",     new int[]{48, 12});
        KAMERA_DB.put("iPhone 16 Pro",      new int[]{48, 12});
        KAMERA_DB.put("iPhone 16 Pro Max",  new int[]{48, 12});
        // Samsung
        KAMERA_DB.put("Galaxy A34",         new int[]{48, 13});
        KAMERA_DB.put("Galaxy A54",         new int[]{50, 32});
        KAMERA_DB.put("Galaxy A55",         new int[]{50, 32});
        KAMERA_DB.put("Galaxy A35",         new int[]{50, 13});
        KAMERA_DB.put("Galaxy S22",         new int[]{50, 10});
        KAMERA_DB.put("Galaxy S22 Plus",    new int[]{50, 10});
        KAMERA_DB.put("Galaxy S22 Ultra",   new int[]{108, 40});
        KAMERA_DB.put("Galaxy S23",         new int[]{50, 12});
        KAMERA_DB.put("Galaxy S23 Plus",    new int[]{50, 12});
        KAMERA_DB.put("Galaxy S23 Ultra",   new int[]{200, 12});
        KAMERA_DB.put("Galaxy S23 FE",      new int[]{50, 10});
        KAMERA_DB.put("Galaxy S24",         new int[]{50, 12});
        KAMERA_DB.put("Galaxy S24 Plus",    new int[]{50, 12});
        KAMERA_DB.put("Galaxy S24 Ultra",   new int[]{200, 12});
        KAMERA_DB.put("Galaxy S24 FE",      new int[]{50, 10});
        KAMERA_DB.put("Galaxy Z Fold 5",    new int[]{50, 10});
        KAMERA_DB.put("Galaxy Z Flip 5",    new int[]{12, 10});
        // Xiaomi
        KAMERA_DB.put("Redmi Note 12",      new int[]{50, 13});
        KAMERA_DB.put("Redmi Note 12 Pro",  new int[]{50, 16});
        KAMERA_DB.put("Redmi Note 13",      new int[]{108, 16});
        KAMERA_DB.put("Redmi Note 13 Pro",  new int[]{200, 16});
        KAMERA_DB.put("Redmi Note 13 Pro+", new int[]{200, 20});
        KAMERA_DB.put("Xiaomi 12",          new int[]{50, 32});
        KAMERA_DB.put("Xiaomi 12 Pro",      new int[]{50, 32});
        KAMERA_DB.put("Xiaomi 13",          new int[]{54, 32});
        KAMERA_DB.put("Xiaomi 13 Pro",      new int[]{50, 32});
        KAMERA_DB.put("Xiaomi 13T",         new int[]{50, 20});
        KAMERA_DB.put("Xiaomi 13T Pro",     new int[]{50, 20});
        KAMERA_DB.put("Xiaomi 14",          new int[]{50, 32});
        KAMERA_DB.put("Xiaomi 14 Ultra",    new int[]{50, 32});
        // OPPO
        KAMERA_DB.put("Reno 10",            new int[]{64, 32});
        KAMERA_DB.put("Reno 10 Pro",        new int[]{50, 32});
        KAMERA_DB.put("Reno 11",            new int[]{50, 32});
        KAMERA_DB.put("Reno 11 Pro",        new int[]{50, 32});
        KAMERA_DB.put("Find X6 Pro",        new int[]{50, 32});
        KAMERA_DB.put("Find X7 Pro",        new int[]{50, 32});
        // OnePlus
        KAMERA_DB.put("OnePlus 11",         new int[]{50, 16});
        KAMERA_DB.put("OnePlus 12",         new int[]{50, 32});
        KAMERA_DB.put("OnePlus Nord 3",     new int[]{50, 16});
        KAMERA_DB.put("OnePlus Nord CE4",   new int[]{50, 16});
        // Google
        KAMERA_DB.put("Pixel 7",            new int[]{50, 10});
        KAMERA_DB.put("Pixel 7 Pro",        new int[]{50, 10});
        KAMERA_DB.put("Pixel 7a",           new int[]{64, 13});
        KAMERA_DB.put("Pixel 8",            new int[]{50, 10});
        KAMERA_DB.put("Pixel 8 Pro",        new int[]{50, 10});
        KAMERA_DB.put("Pixel 8a",           new int[]{64, 13});
        KAMERA_DB.put("Pixel 9",            new int[]{50, 10});
        KAMERA_DB.put("Pixel 9 Pro",        new int[]{50, 42});
        // Huawei
        KAMERA_DB.put("Nova 11",            new int[]{50, 60});
        KAMERA_DB.put("Nova 12",            new int[]{50, 60});
        KAMERA_DB.put("P50 Pro",            new int[]{50, 13});
        KAMERA_DB.put("P60 Pro",            new int[]{48, 13});
        KAMERA_DB.put("Mate 50 Pro",        new int[]{50, 13});
        KAMERA_DB.put("Mate 60 Pro",        new int[]{50, 13});
        // Motorola
        KAMERA_DB.put("Moto G84",           new int[]{50, 16});
        KAMERA_DB.put("Moto G Power",       new int[]{50, 16});
        KAMERA_DB.put("Edge 40",            new int[]{50, 32});
        KAMERA_DB.put("Edge 40 Pro",        new int[]{50, 60});
        KAMERA_DB.put("Edge 50 Pro",        new int[]{50, 50});
        KAMERA_DB.put("Razr 40 Ultra",      new int[]{12, 32});
    }


    /** Model → ekran boyutu (inç) */
    private static final Map<String, Double> EKRAN_DB = new LinkedHashMap<>();
    static {
        // Apple
        EKRAN_DB.put("iPhone 11",          6.1);
        EKRAN_DB.put("iPhone 11 Pro",      5.8);
        EKRAN_DB.put("iPhone 12",          6.1);
        EKRAN_DB.put("iPhone 12 Pro",      6.1);
        EKRAN_DB.put("iPhone 12 Pro Max",  6.7);
        EKRAN_DB.put("iPhone 13",          6.1);
        EKRAN_DB.put("iPhone 13 Mini",     5.4);
        EKRAN_DB.put("iPhone 13 Pro",      6.1);
        EKRAN_DB.put("iPhone 13 Pro Max",  6.7);
        EKRAN_DB.put("iPhone 14",          6.1);
        EKRAN_DB.put("iPhone 14 Plus",     6.7);
        EKRAN_DB.put("iPhone 14 Pro",      6.1);
        EKRAN_DB.put("iPhone 14 Pro Max",  6.7);
        EKRAN_DB.put("iPhone 15",          6.1);
        EKRAN_DB.put("iPhone 15 Plus",     6.7);
        EKRAN_DB.put("iPhone 15 Pro",      6.1);
        EKRAN_DB.put("iPhone 15 Pro Max",  6.7);
        EKRAN_DB.put("iPhone 16",          6.1);
        EKRAN_DB.put("iPhone 16 Plus",     6.7);
        EKRAN_DB.put("iPhone 16 Pro",      6.3);
        EKRAN_DB.put("iPhone 16 Pro Max",  6.9);
        // Samsung
        EKRAN_DB.put("Galaxy A34",         6.6);
        EKRAN_DB.put("Galaxy A54",         6.4);
        EKRAN_DB.put("Galaxy A55",         6.6);
        EKRAN_DB.put("Galaxy A35",         6.6);
        EKRAN_DB.put("Galaxy S22",         6.1);
        EKRAN_DB.put("Galaxy S22 Plus",    6.6);
        EKRAN_DB.put("Galaxy S22 Ultra",   6.8);
        EKRAN_DB.put("Galaxy S23",         6.1);
        EKRAN_DB.put("Galaxy S23 Plus",    6.6);
        EKRAN_DB.put("Galaxy S23 Ultra",   6.8);
        EKRAN_DB.put("Galaxy S23 FE",      6.4);
        EKRAN_DB.put("Galaxy S24",         6.2);
        EKRAN_DB.put("Galaxy S24 Plus",    6.7);
        EKRAN_DB.put("Galaxy S24 Ultra",   6.8);
        EKRAN_DB.put("Galaxy S24 FE",      6.7);
        EKRAN_DB.put("Galaxy Z Fold 5",    7.6);
        EKRAN_DB.put("Galaxy Z Flip 5",    6.7);
        // Xiaomi
        EKRAN_DB.put("Redmi Note 12",      6.67);
        EKRAN_DB.put("Redmi Note 12 Pro",  6.67);
        EKRAN_DB.put("Redmi Note 13",      6.67);
        EKRAN_DB.put("Redmi Note 13 Pro",  6.67);
        EKRAN_DB.put("Redmi Note 13 Pro+", 6.67);
        EKRAN_DB.put("Xiaomi 12",          6.28);
        EKRAN_DB.put("Xiaomi 12 Pro",      6.73);
        EKRAN_DB.put("Xiaomi 13",          6.36);
        EKRAN_DB.put("Xiaomi 13 Pro",      6.73);
        EKRAN_DB.put("Xiaomi 13T",         6.67);
        EKRAN_DB.put("Xiaomi 13T Pro",     6.67);
        EKRAN_DB.put("Xiaomi 14",          6.36);
        EKRAN_DB.put("Xiaomi 14 Ultra",    6.73);
        // OPPO
        EKRAN_DB.put("Reno 10",            6.7);
        EKRAN_DB.put("Reno 10 Pro",        6.7);
        EKRAN_DB.put("Reno 11",            6.7);
        EKRAN_DB.put("Reno 11 Pro",        6.7);
        EKRAN_DB.put("Find X6 Pro",        6.82);
        EKRAN_DB.put("Find X7 Pro",        6.82);
        // OnePlus
        EKRAN_DB.put("OnePlus 11",         6.7);
        EKRAN_DB.put("OnePlus 12",         6.82);
        EKRAN_DB.put("OnePlus Nord 3",     6.74);
        EKRAN_DB.put("OnePlus Nord CE4",   6.67);
        // Google
        EKRAN_DB.put("Pixel 7",            6.3);
        EKRAN_DB.put("Pixel 7 Pro",        6.7);
        EKRAN_DB.put("Pixel 7a",           6.1);
        EKRAN_DB.put("Pixel 8",            6.2);
        EKRAN_DB.put("Pixel 8 Pro",        6.7);
        EKRAN_DB.put("Pixel 8a",           6.1);
        EKRAN_DB.put("Pixel 9",            6.3);
        EKRAN_DB.put("Pixel 9 Pro",        6.3);
        // Huawei
        EKRAN_DB.put("Nova 11",            6.7);
        EKRAN_DB.put("Nova 12",            6.7);
        EKRAN_DB.put("P50 Pro",            6.6);
        EKRAN_DB.put("P60 Pro",            6.67);
        EKRAN_DB.put("Mate 50 Pro",        6.74);
        EKRAN_DB.put("Mate 60 Pro",        6.82);
        // Motorola
        EKRAN_DB.put("Moto G84",           6.55);
        EKRAN_DB.put("Moto G Power",       6.5);
        EKRAN_DB.put("Edge 40",            6.55);
        EKRAN_DB.put("Edge 40 Pro",        6.67);
        EKRAN_DB.put("Edge 50 Pro",        6.7);
        EKRAN_DB.put("Razr 40 Ultra",      6.9);
    }

    /**
     * Türkiye ikinci el piyasası (Sahibinden.com) baz alınarak belirlenmiş
     * sıfır/çıkış fiyatları — Mayıs 2025 ortalamalarına göre.
     * Format: "Model Adı" -> sıfır piyasa fiyatı (TL)
     */
    private static final Map<String, Map<String, Double>> TELEFON_DB = new LinkedHashMap<>();

    static {
        /*
         * TELEFON_DB — Sahibinden.com ikinci el piyasası referans fiyatları
         * Mayıs 2025 ortalaması | 128 GB | Temiz durum | IMEI kayıtlı | ~12 aylık cihaz
         * Bu fiyatlar hesaplama motorunun BAZASI — kullanıcının seçtiği koşullar
         * (yaş, pil, hasar, garanti vb.) bu taban üzerine uygulanır.
         */

        // ── APPLE ──────────────────────────────────────────────
        Map<String, Double> apple = new LinkedHashMap<>();
        apple.put("iPhone 11",          9000.0);   // letgo ~9.500 TL (64GB), 128GB ~10K
        apple.put("iPhone 11 Pro",      12000.0);
        apple.put("iPhone 12",          13000.0);
        apple.put("iPhone 12 Pro",      16000.0);
        apple.put("iPhone 12 Pro Max",  19000.0);
        apple.put("iPhone 13",          22000.0);  // letgo ~25K garantili, ~20K temiz
        apple.put("iPhone 13 Mini",     18000.0);
        apple.put("iPhone 13 Pro",      29000.0);  // Yepy alım teklifi 34K → satış ~29K
        apple.put("iPhone 13 Pro Max",  33000.0);
        apple.put("iPhone 14",          30000.0);  // letgo ~34K (temiz)
        apple.put("iPhone 14 Plus",     33000.0);
        apple.put("iPhone 14 Pro",      38000.0);
        apple.put("iPhone 14 Pro Max",  44000.0);
        apple.put("iPhone 15",          36000.0);
        apple.put("iPhone 15 Plus",     40000.0);
        apple.put("iPhone 15 Pro",      48000.0);
        apple.put("iPhone 15 Pro Max",  56000.0);  // letgo 32K YD 256GB → yurt içi ~56K
        apple.put("iPhone 16",          46000.0);
        apple.put("iPhone 16 Plus",     52000.0);
        apple.put("iPhone 16 Pro",      60000.0);
        apple.put("iPhone 16 Pro Max",  70000.0);
        TELEFON_DB.put("Apple", apple);

        // ── SAMSUNG ────────────────────────────────────────────
        Map<String, Double> samsung = new LinkedHashMap<>();
        // Akakçe sıfır: S22 128GB ~33K, S23 128GB ~45K, S24 128GB ~32K, S23 Ultra 512GB ~64K
        // Sahibinden ikinci el genellikle sıfırın %60-75'i
        samsung.put("Galaxy A34",        6500.0);
        samsung.put("Galaxy A54",        8500.0);
        samsung.put("Galaxy A55",       10000.0);
        samsung.put("Galaxy A35",        7500.0);
        samsung.put("Galaxy S22",       14000.0);  // sıfır 33K → ikinci el ~14K (2 yaş)
        samsung.put("Galaxy S22 Plus",  17000.0);
        samsung.put("Galaxy S22 Ultra", 24000.0);
        samsung.put("Galaxy S23",       20000.0);  // forum: 22K garantili
        samsung.put("Galaxy S23 Plus",  24000.0);
        samsung.put("Galaxy S23 Ultra", 36000.0);  // Sahibinden yurt içi 30-35K
        samsung.put("Galaxy S23 FE",    14000.0);
        samsung.put("Galaxy S24",       24000.0);  // sıfır 32K → ikinci el ~24K
        samsung.put("Galaxy S24 Plus",  30000.0);
        samsung.put("Galaxy S24 Ultra", 46000.0);  // Sahibinden yurt içi 30-35K (çıkışta), şimdi ~46K
        samsung.put("Galaxy S24 FE",    17000.0);
        samsung.put("Galaxy Z Fold 5",  60000.0);
        samsung.put("Galaxy Z Flip 5",  30000.0);
        TELEFON_DB.put("Samsung", samsung);

        // ── XIAOMI ─────────────────────────────────────────────
        Map<String, Double> xiaomi = new LinkedHashMap<>();
        // Xiaomi ikinci el değer kaybı Samsung/Apple'dan hızlı (~%50-60 ilk yıl)
        xiaomi.put("Redmi Note 12",      5000.0);
        xiaomi.put("Redmi Note 12 Pro",  6500.0);
        xiaomi.put("Redmi Note 13",      6500.0);
        xiaomi.put("Redmi Note 13 Pro",  8500.0);
        xiaomi.put("Redmi Note 13 Pro+", 10000.0);
        xiaomi.put("Xiaomi 12",         11000.0);
        xiaomi.put("Xiaomi 12 Pro",     15000.0);
        xiaomi.put("Xiaomi 13",         16000.0);
        xiaomi.put("Xiaomi 13 Pro",     21000.0);
        xiaomi.put("Xiaomi 13T",        14000.0);
        xiaomi.put("Xiaomi 13T Pro",    18000.0);
        xiaomi.put("Xiaomi 14",         24000.0);
        xiaomi.put("Xiaomi 14 Ultra",   38000.0);
        TELEFON_DB.put("Xiaomi", xiaomi);

        // ── OPPO ───────────────────────────────────────────────
        Map<String, Double> oppo = new LinkedHashMap<>();
        oppo.put("Reno 10",             10000.0);
        oppo.put("Reno 10 Pro",         14000.0);
        oppo.put("Reno 11",             12000.0);
        oppo.put("Reno 11 Pro",         16000.0);
        oppo.put("Find X6 Pro",         30000.0);
        oppo.put("Find X7 Pro",         42000.0);
        TELEFON_DB.put("OPPO", oppo);

        // ── OnePlus ────────────────────────────────────────────
        Map<String, Double> oneplus = new LinkedHashMap<>();
        oneplus.put("OnePlus 11",       20000.0);
        oneplus.put("OnePlus 12",       30000.0);
        oneplus.put("OnePlus Nord 3",   11000.0);
        oneplus.put("OnePlus Nord CE4",  9500.0);
        TELEFON_DB.put("OnePlus", oneplus);

        // ── GOOGLE ─────────────────────────────────────────────
        Map<String, Double> google = new LinkedHashMap<>();
        // Google Pixel Türkiye'de az bulunur, fiyatlar belirsiz ama genellikle
        // Apple'a yakın değer tutar
        google.put("Pixel 7",      18000.0);
        google.put("Pixel 7 Pro",  24000.0);
        google.put("Pixel 7a",     15000.0);
        google.put("Pixel 8",      28000.0);
        google.put("Pixel 8 Pro",  36000.0);
        google.put("Pixel 8a",     22000.0);
        google.put("Pixel 9",      36000.0);
        google.put("Pixel 9 Pro",  46000.0);
        TELEFON_DB.put("Google", google);

        // ── HUAWEI ─────────────────────────────────────────────
        Map<String, Double> huawei = new LinkedHashMap<>();
        // Huawei Google servisleri sorunu nedeniyle değer kaybı yüksek
        huawei.put("Nova 11",      9000.0);
        huawei.put("Nova 12",      11000.0);
        huawei.put("P50 Pro",      18000.0);
        huawei.put("P60 Pro",      26000.0);
        huawei.put("Mate 50 Pro",  22000.0);
        huawei.put("Mate 60 Pro",  36000.0);
        TELEFON_DB.put("Huawei", huawei);

        // ── MOTOROLA ───────────────────────────────────────────
        Map<String, Double> motorola = new LinkedHashMap<>();
        // Motorola Türkiye'de düşük marka bilinirliği → hızlı değer kaybı
        motorola.put("Moto G84",        6000.0);
        motorola.put("Moto G Power",    5500.0);
        motorola.put("Edge 40",        10000.0);
        motorola.put("Edge 40 Pro",    14000.0);
        motorola.put("Edge 50 Pro",    18000.0);
        motorola.put("Razr 40 Ultra",  32000.0);
        TELEFON_DB.put("Motorola", motorola);
    }

    @FXML private ComboBox<String> markaComboBox;
    @FXML private ComboBox<String> modelComboBox;
    @FXML private ComboBox<Integer> depolamaComboBox;
    @FXML private Slider pilSagligiSlider;
    @FXML private Label pilSagligiLabel;
    @FXML private TextField cihazYasiField;
    @FXML private CheckBox garantiCheck;
    @FXML private ComboBox<String> garantiAyComboBox;
    @FXML private CheckBox kutuFaturaCheck;
    @FXML private CheckBox imeiCheck;
    @FXML private Label arkaKameraLabel;
    @FXML private Label ekranBoyutuLabel;
    @FXML private Label onKameraLabel;
    @FXML private CheckBox besGCheck;       // 5G desteği
    @FXML private CheckBox hizliSarjCheck;  // Hızlı şarj
    @FXML private CheckComboBox<String> hasarTamirComboBox;
    @FXML private Label phoneResultLabel;

    @FXML
    public void initialize() {
        markaComboBox.getItems().clear();
        markaComboBox.getItems().add("Marka Seçiniz");
        markaComboBox.getItems().addAll(TELEFON_DB.keySet());
        markaComboBox.getSelectionModel().select(0);

        modelComboBox.getItems().clear();
        modelComboBox.getItems().add("Model Seçiniz");
        modelComboBox.getSelectionModel().select(0);
        modelComboBox.setDisable(true);

        depolamaComboBox.getItems().addAll(64, 128, 256, 512, 1024);
        depolamaComboBox.getSelectionModel().select(1);

        // Garanti ay seçenekleri
        garantiAyComboBox.getItems().addAll(
            "1-3 ay kaldı", "4-6 ay kaldı", "7-12 ay kaldı",
            "13-18 ay kaldı", "19-24 ay kaldı", "24+ ay kaldı"
        );
        garantiAyComboBox.setDisable(true);

        // Garanti CheckBox değişince ComboBox'ı aç/kapat
        garantiCheck.selectedProperty().addListener((obs, oldVal, newVal) -> {
            garantiAyComboBox.setDisable(!newVal);
            if (!newVal) garantiAyComboBox.getSelectionModel().clearSelection();
        });

        pilSagligiSlider.setMin(50);
        pilSagligiSlider.setMax(100);
        pilSagligiSlider.setValue(90);
        pilSagligiLabel.setText("90%");
        pilSagligiSlider.setMajorTickUnit(10);
        pilSagligiSlider.setShowTickLabels(true);
        pilSagligiSlider.setShowTickMarks(true);
        pilSagligiLabel.setText("90%");
        pilSagligiSlider.valueProperty().addListener((obs, oldVal, newVal) ->
                pilSagligiLabel.setText(newVal.intValue() + "%"));

        hasarTamirComboBox.getItems().addAll(
            "Kozmetik Hasar (Çizik/Çatlak)",
            "Ön Cam Kırık",
            "Arka Cam Kırık",
            "Ekran Değişimi Gördü",
            "Batarya Değişimi Gördü",
            "Kamera Değişimi Gördü",
            "Anakart Tamiri Gördü",
            "Su Hasarı Var"
        );

        markaComboBox.setOnAction(event -> {
            String secilenMarka = markaComboBox.getValue();
            if (secilenMarka != null && !secilenMarka.equals("Marka Seçiniz")) {
                modelComboBox.getItems().clear();
                modelComboBox.getItems().add("Model Seçiniz");
                modelComboBox.getItems().addAll(TELEFON_DB.get(secilenMarka).keySet());
                modelComboBox.getSelectionModel().select(0);
                modelComboBox.setDisable(false);

                // Model seçilince kamera bilgisini güncelle
                modelComboBox.setOnAction(modelEvent -> {
                    String secilenModel = modelComboBox.getValue();
                    if (secilenModel != null && !secilenModel.equals("Model Seçiniz")
                            && KAMERA_DB.containsKey(secilenModel)) {
                        int[] kamera = KAMERA_DB.get(secilenModel);
                        arkaKameraLabel.setText(kamera[0] + " MP");
                        onKameraLabel.setText(kamera[1] + " MP");
                    } else {
                        arkaKameraLabel.setText("—");
                        onKameraLabel.setText("—");
                    }
                    if (EKRAN_DB.containsKey(secilenModel)) {
                        ekranBoyutuLabel.setText(EKRAN_DB.get(secilenModel) + " inç");
                    } else {
                        ekranBoyutuLabel.setText("—");
                    }
                });
            } else {
                modelComboBox.getItems().clear();
                modelComboBox.getItems().add("Model Seçiniz");
                modelComboBox.getSelectionModel().select(0);
                modelComboBox.setDisable(true);
            }
        });
    }

    @FXML
    protected void onCalculateButtonClick() {
        try {
            String marka = markaComboBox.getValue();
            String model = modelComboBox.getValue();

            if (marka == null || marka.equals("Marka Seçiniz") ||
                model == null || model.equals("Model Seçiniz")) {
                phoneResultLabel.setText("Lütfen marka ve model seçiniz!");
                return;
            }

            int depolama  = depolamaComboBox.getValue();
            int pilSagligi = (int) pilSagligiSlider.getValue();
            int cihazYasi = Integer.parseInt(cihazYasiField.getText().trim());

            ObservableList<String> secilenHasarlar = hasarTamirComboBox.getCheckModel().getCheckedItems();
            boolean kozmetikHasar = secilenHasarlar.stream()
                    .anyMatch(s -> s.contains("Kozmetik") || s.contains("Cam") || s.contains("Ekran"));
            boolean tamirGordu = secilenHasarlar.stream()
                    .anyMatch(s -> s.contains("Değişimi") || s.contains("Tamiri") || s.contains("Su"));

            Phone telefon = new Phone(
                    marka, model, depolama, pilSagligi, cihazYasi,
                    garantiCheck.isSelected(),
                    kutuFaturaCheck.isSelected(),
                    kozmetikHasar,
                    tamirGordu,
                    imeiCheck.isSelected(),
                    6.1,
                    parseMP(arkaKameraLabel.getText()),
                    parseMP(onKameraLabel.getText())
            );

            // Seçili hasar kalemlerinin toplam kesinti oranı
            double hasarKesinti = 0.0;
            // Sabit kesinti oranları (sıra: liste sırasıyla eşleşir)
            double[] kesintiler = {0.08, 0.15, 0.10, 0.18, 0.05, 0.10, 0.28, 0.38};
            String[] hasarlar = {
                "Kozmetik Hasar (Çizik/Çatlak)", "Ön Cam Kırık", "Arka Cam Kırık",
                "Ekran Değişimi Gördü", "Batarya Değişimi Gördü", "Kamera Değişimi Gördü",
                "Anakart Tamiri Gördü", "Su Hasarı Var"
            };
            for (String hasar : secilenHasarlar) {
                for (int i = 0; i < hasarlar.length; i++) {
                    if (hasar.equals(hasarlar[i])) {
                        hasarKesinti += kesintiler[i];
                        break;
                    }
                }
            }

            // Kalan garanti ayını hesapla
            int kalanGarantiAy = 0;
            if (garantiCheck.isSelected() && garantiAyComboBox.getValue() != null) {
                String g = garantiAyComboBox.getValue();
                if      (g.startsWith("1-3"))  kalanGarantiAy = 2;
                else if (g.startsWith("4-6"))  kalanGarantiAy = 5;
                else if (g.startsWith("7-12")) kalanGarantiAy = 10;
                else if (g.startsWith("13-18"))kalanGarantiAy = 15;
                else if (g.startsWith("19-24"))kalanGarantiAy = 21;
                else if (g.startsWith("24+"))  kalanGarantiAy = 27;
            }

            double tabanFiyat = TELEFON_DB.get(marka).get(model);
            double sonuc = new PhonePricingEngine().tahminiDegerHesapla(
                    telefon, tabanFiyat,
                    hasarKesinti,
                    besGCheck.isSelected(),
                    hizliSarjCheck.isSelected(),
                    parseMP(arkaKameraLabel.getText()),
                    parseMP(onKameraLabel.getText()),
                    kalanGarantiAy
            );

            phoneResultLabel.setText(String.format("Tahmini Değer: %,.0f ₺", sonuc));

        } catch (NumberFormatException e) {
            phoneResultLabel.setText("Hata: Cihaz yaşını ay olarak girin!");
        } catch (Exception e) {
            phoneResultLabel.setText("Hata: Verileri kontrol edin!");
        }
    }

    /** Label metninden (örn: "50 MP" veya "—") int değeri çıkarır */
    private int parseMP(String text) {
        try { return Integer.parseInt(text.replace(" MP", "").trim()); }
        catch (Exception e) { return 12; } // bilinmiyorsa varsayılan
    }

    @FXML
    protected void onClearButtonClick() {
        markaComboBox.getSelectionModel().select(0);
        modelComboBox.getItems().clear();
        modelComboBox.getItems().add("Model Seçiniz");
        modelComboBox.getSelectionModel().select(0);
        modelComboBox.setDisable(true);
        depolamaComboBox.getSelectionModel().select(1);
        pilSagligiSlider.setValue(90);
        pilSagligiLabel.setText("90%");
        cihazYasiField.clear();
        garantiCheck.setSelected(false);
        garantiAyComboBox.getSelectionModel().clearSelection();
        garantiAyComboBox.setDisable(true);
        kutuFaturaCheck.setSelected(false);
        imeiCheck.setSelected(true);
        arkaKameraLabel.setText("—");
        onKameraLabel.setText("—");
        ekranBoyutuLabel.setText("—");
        besGCheck.setSelected(false);
        hizliSarjCheck.setSelected(false);
        hasarTamirComboBox.getCheckModel().clearChecks();
        phoneResultLabel.setText("Tahmini Değer: -- ₺");
    }
}

package com.example.valuwise.controller;

import com.example.valuwise.model.Phone;
import com.example.valuwise.service.pricing.PhonePricingEngine;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import com.example.valuwise.HelloController;
import javafx.scene.control.Alert;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import org.controlsfx.control.CheckComboBox;

public class PhoneController {

    /** Model → ekran boyutu (inç) */
    private static final Map<String, Double> EKRAN_DB = new LinkedHashMap<>();
    static {
        EKRAN_DB.put("iPhone 11", 6.1); EKRAN_DB.put("iPhone 11 Pro", 5.8);
        EKRAN_DB.put("iPhone 12", 6.1); EKRAN_DB.put("iPhone 12 Pro", 6.1); EKRAN_DB.put("iPhone 12 Pro Max", 6.7);
        EKRAN_DB.put("iPhone 13", 6.1); EKRAN_DB.put("iPhone 13 Mini", 5.4); EKRAN_DB.put("iPhone 13 Pro", 6.1); EKRAN_DB.put("iPhone 13 Pro Max", 6.7);
        EKRAN_DB.put("iPhone 14", 6.1); EKRAN_DB.put("iPhone 14 Plus", 6.7); EKRAN_DB.put("iPhone 14 Pro", 6.1); EKRAN_DB.put("iPhone 14 Pro Max", 6.7);
        EKRAN_DB.put("iPhone 15", 6.1); EKRAN_DB.put("iPhone 15 Plus", 6.7); EKRAN_DB.put("iPhone 15 Pro", 6.1); EKRAN_DB.put("iPhone 15 Pro Max", 6.7);
        EKRAN_DB.put("iPhone 16", 6.1); EKRAN_DB.put("iPhone 16 Plus", 6.7); EKRAN_DB.put("iPhone 16 Pro", 6.3); EKRAN_DB.put("iPhone 16 Pro Max", 6.9);
        EKRAN_DB.put("Galaxy A34", 6.6); EKRAN_DB.put("Galaxy A54", 6.4); EKRAN_DB.put("Galaxy A55", 6.6); EKRAN_DB.put("Galaxy A35", 6.6);
        EKRAN_DB.put("Galaxy S22", 6.1); EKRAN_DB.put("Galaxy S22 Plus", 6.6); EKRAN_DB.put("Galaxy S22 Ultra", 6.8);
        EKRAN_DB.put("Galaxy S23", 6.1); EKRAN_DB.put("Galaxy S23 Plus", 6.6); EKRAN_DB.put("Galaxy S23 Ultra", 6.8); EKRAN_DB.put("Galaxy S23 FE", 6.4);
        EKRAN_DB.put("Galaxy S24", 6.2); EKRAN_DB.put("Galaxy S24 Plus", 6.7); EKRAN_DB.put("Galaxy S24 Ultra", 6.8); EKRAN_DB.put("Galaxy S24 FE", 6.7);
        EKRAN_DB.put("Galaxy Z Fold 5", 7.6); EKRAN_DB.put("Galaxy Z Flip 5", 6.7);
        EKRAN_DB.put("Redmi Note 12", 6.67); EKRAN_DB.put("Redmi Note 12 Pro", 6.67);
        EKRAN_DB.put("Redmi Note 13", 6.67); EKRAN_DB.put("Redmi Note 13 Pro", 6.67); EKRAN_DB.put("Redmi Note 13 Pro+", 6.67);
        EKRAN_DB.put("Xiaomi 12", 6.28); EKRAN_DB.put("Xiaomi 12 Pro", 6.73);
        EKRAN_DB.put("Xiaomi 13", 6.36); EKRAN_DB.put("Xiaomi 13 Pro", 6.73); EKRAN_DB.put("Xiaomi 13T", 6.67); EKRAN_DB.put("Xiaomi 13T Pro", 6.67);
        EKRAN_DB.put("Xiaomi 14", 6.36); EKRAN_DB.put("Xiaomi 14 Ultra", 6.73);
        EKRAN_DB.put("Reno 10", 6.7); EKRAN_DB.put("Reno 10 Pro", 6.7); EKRAN_DB.put("Reno 11", 6.7); EKRAN_DB.put("Reno 11 Pro", 6.7);
        EKRAN_DB.put("Find X6 Pro", 6.82); EKRAN_DB.put("Find X7 Pro", 6.82);
        EKRAN_DB.put("OnePlus 11", 6.7); EKRAN_DB.put("OnePlus 12", 6.82); EKRAN_DB.put("OnePlus Nord 3", 6.74); EKRAN_DB.put("OnePlus Nord CE4", 6.67);
        EKRAN_DB.put("Pixel 7", 6.3); EKRAN_DB.put("Pixel 7 Pro", 6.7); EKRAN_DB.put("Pixel 7a", 6.1);
        EKRAN_DB.put("Pixel 8", 6.2); EKRAN_DB.put("Pixel 8 Pro", 6.7); EKRAN_DB.put("Pixel 8a", 6.1);
        EKRAN_DB.put("Pixel 9", 6.3); EKRAN_DB.put("Pixel 9 Pro", 6.3);
        EKRAN_DB.put("Nova 11", 6.7); EKRAN_DB.put("Nova 12", 6.7); EKRAN_DB.put("P50 Pro", 6.6); EKRAN_DB.put("P60 Pro", 6.67);
        EKRAN_DB.put("Mate 50 Pro", 6.74); EKRAN_DB.put("Mate 60 Pro", 6.82);
        EKRAN_DB.put("Moto G84", 6.55); EKRAN_DB.put("Moto G Power", 6.5);
        EKRAN_DB.put("Edge 40", 6.55); EKRAN_DB.put("Edge 40 Pro", 6.67); EKRAN_DB.put("Edge 50 Pro", 6.7); EKRAN_DB.put("Razr 40 Ultra", 6.9);
    }

    /** Model → [arkaKameraMP, onKameraMP] */
    private static final Map<String, int[]> KAMERA_DB = new LinkedHashMap<>();
    static {
        KAMERA_DB.put("iPhone 11", new int[]{12, 12}); KAMERA_DB.put("iPhone 11 Pro", new int[]{12, 12});
        KAMERA_DB.put("iPhone 12", new int[]{12, 12}); KAMERA_DB.put("iPhone 12 Pro", new int[]{12, 12}); KAMERA_DB.put("iPhone 12 Pro Max", new int[]{12, 12});
        KAMERA_DB.put("iPhone 13", new int[]{12, 12}); KAMERA_DB.put("iPhone 13 Mini", new int[]{12, 12}); KAMERA_DB.put("iPhone 13 Pro", new int[]{12, 12}); KAMERA_DB.put("iPhone 13 Pro Max", new int[]{12, 12});
        KAMERA_DB.put("iPhone 14", new int[]{12, 12}); KAMERA_DB.put("iPhone 14 Plus", new int[]{12, 12}); KAMERA_DB.put("iPhone 14 Pro", new int[]{48, 12}); KAMERA_DB.put("iPhone 14 Pro Max", new int[]{48, 12});
        KAMERA_DB.put("iPhone 15", new int[]{48, 12}); KAMERA_DB.put("iPhone 15 Plus", new int[]{48, 12}); KAMERA_DB.put("iPhone 15 Pro", new int[]{48, 12}); KAMERA_DB.put("iPhone 15 Pro Max", new int[]{48, 12});
        KAMERA_DB.put("iPhone 16", new int[]{48, 12}); KAMERA_DB.put("iPhone 16 Plus", new int[]{48, 12}); KAMERA_DB.put("iPhone 16 Pro", new int[]{48, 12}); KAMERA_DB.put("iPhone 16 Pro Max", new int[]{48, 12});
        KAMERA_DB.put("Galaxy A34", new int[]{48, 13}); KAMERA_DB.put("Galaxy A54", new int[]{50, 32}); KAMERA_DB.put("Galaxy A55", new int[]{50, 32}); KAMERA_DB.put("Galaxy A35", new int[]{50, 13});
        KAMERA_DB.put("Galaxy S22", new int[]{50, 10}); KAMERA_DB.put("Galaxy S22 Plus", new int[]{50, 10}); KAMERA_DB.put("Galaxy S22 Ultra", new int[]{108, 40});
        KAMERA_DB.put("Galaxy S23", new int[]{50, 12}); KAMERA_DB.put("Galaxy S23 Plus", new int[]{50, 12}); KAMERA_DB.put("Galaxy S23 Ultra", new int[]{200, 12}); KAMERA_DB.put("Galaxy S23 FE", new int[]{50, 10});
        KAMERA_DB.put("Galaxy S24", new int[]{50, 12}); KAMERA_DB.put("Galaxy S24 Plus", new int[]{50, 12}); KAMERA_DB.put("Galaxy S24 Ultra", new int[]{200, 12}); KAMERA_DB.put("Galaxy S24 FE", new int[]{50, 10});
        KAMERA_DB.put("Galaxy Z Fold 5", new int[]{50, 10}); KAMERA_DB.put("Galaxy Z Flip 5", new int[]{12, 10});
        KAMERA_DB.put("Redmi Note 12", new int[]{50, 13}); KAMERA_DB.put("Redmi Note 12 Pro", new int[]{50, 16});
        KAMERA_DB.put("Redmi Note 13", new int[]{108, 16}); KAMERA_DB.put("Redmi Note 13 Pro", new int[]{200, 16}); KAMERA_DB.put("Redmi Note 13 Pro+", new int[]{200, 20});
        KAMERA_DB.put("Xiaomi 12", new int[]{50, 32}); KAMERA_DB.put("Xiaomi 12 Pro", new int[]{50, 32});
        KAMERA_DB.put("Xiaomi 13", new int[]{54, 32}); KAMERA_DB.put("Xiaomi 13 Pro", new int[]{50, 32}); KAMERA_DB.put("Xiaomi 13T", new int[]{50, 20}); KAMERA_DB.put("Xiaomi 13T Pro", new int[]{50, 20});
        KAMERA_DB.put("Xiaomi 14", new int[]{50, 32}); KAMERA_DB.put("Xiaomi 14 Ultra", new int[]{50, 32});
        KAMERA_DB.put("Reno 10", new int[]{64, 32}); KAMERA_DB.put("Reno 10 Pro", new int[]{50, 32}); KAMERA_DB.put("Reno 11", new int[]{50, 32}); KAMERA_DB.put("Reno 11 Pro", new int[]{50, 32});
        KAMERA_DB.put("Find X6 Pro", new int[]{50, 32}); KAMERA_DB.put("Find X7 Pro", new int[]{50, 32});
        KAMERA_DB.put("OnePlus 11", new int[]{50, 16}); KAMERA_DB.put("OnePlus 12", new int[]{50, 32}); KAMERA_DB.put("OnePlus Nord 3", new int[]{50, 16}); KAMERA_DB.put("OnePlus Nord CE4", new int[]{50, 16});
        KAMERA_DB.put("Pixel 7", new int[]{50, 10}); KAMERA_DB.put("Pixel 7 Pro", new int[]{50, 10}); KAMERA_DB.put("Pixel 7a", new int[]{64, 13});
        KAMERA_DB.put("Pixel 8", new int[]{50, 10}); KAMERA_DB.put("Pixel 8 Pro", new int[]{50, 10}); KAMERA_DB.put("Pixel 8a", new int[]{64, 13});
        KAMERA_DB.put("Pixel 9", new int[]{50, 10}); KAMERA_DB.put("Pixel 9 Pro", new int[]{50, 42});
        KAMERA_DB.put("Nova 11", new int[]{50, 60}); KAMERA_DB.put("Nova 12", new int[]{50, 60}); KAMERA_DB.put("P50 Pro", new int[]{50, 13}); KAMERA_DB.put("P60 Pro", new int[]{48, 13});
        KAMERA_DB.put("Mate 50 Pro", new int[]{50, 13}); KAMERA_DB.put("Mate 60 Pro", new int[]{50, 13});
        KAMERA_DB.put("Moto G84", new int[]{50, 16}); KAMERA_DB.put("Moto G Power", new int[]{50, 16});
        KAMERA_DB.put("Edge 40", new int[]{50, 32}); KAMERA_DB.put("Edge 40 Pro", new int[]{50, 60}); KAMERA_DB.put("Edge 50 Pro", new int[]{50, 50}); KAMERA_DB.put("Razr 40 Ultra", new int[]{12, 32});
    }

    /** Sahibinden.com Mayıs 2025 — 128GB, temiz, IMEI kayıtlı, ~12 aylık referans */
    private static final Map<String, Map<String, Double>> TELEFON_DB = new LinkedHashMap<>();
    static {
        Map<String, Double> apple = new LinkedHashMap<>();
        apple.put("iPhone 11", 9000.0); apple.put("iPhone 11 Pro", 12000.0);
        apple.put("iPhone 12", 13000.0); apple.put("iPhone 12 Pro", 16000.0); apple.put("iPhone 12 Pro Max", 19000.0);
        apple.put("iPhone 13", 22000.0); apple.put("iPhone 13 Mini", 18000.0); apple.put("iPhone 13 Pro", 29000.0); apple.put("iPhone 13 Pro Max", 33000.0);
        apple.put("iPhone 14", 30000.0); apple.put("iPhone 14 Plus", 33000.0); apple.put("iPhone 14 Pro", 38000.0); apple.put("iPhone 14 Pro Max", 44000.0);
        apple.put("iPhone 15", 36000.0); apple.put("iPhone 15 Plus", 40000.0); apple.put("iPhone 15 Pro", 48000.0); apple.put("iPhone 15 Pro Max", 56000.0);
        apple.put("iPhone 16", 46000.0); apple.put("iPhone 16 Plus", 52000.0); apple.put("iPhone 16 Pro", 60000.0); apple.put("iPhone 16 Pro Max", 70000.0);
        TELEFON_DB.put("Apple", apple);

        Map<String, Double> samsung = new LinkedHashMap<>();
        samsung.put("Galaxy A34", 6500.0); samsung.put("Galaxy A54", 8500.0); samsung.put("Galaxy A55", 10000.0); samsung.put("Galaxy A35", 7500.0);
        samsung.put("Galaxy S22", 14000.0); samsung.put("Galaxy S22 Plus", 17000.0); samsung.put("Galaxy S22 Ultra", 24000.0);
        samsung.put("Galaxy S23", 20000.0); samsung.put("Galaxy S23 Plus", 24000.0); samsung.put("Galaxy S23 Ultra", 36000.0); samsung.put("Galaxy S23 FE", 14000.0);
        samsung.put("Galaxy S24", 24000.0); samsung.put("Galaxy S24 Plus", 30000.0); samsung.put("Galaxy S24 Ultra", 46000.0); samsung.put("Galaxy S24 FE", 17000.0);
        samsung.put("Galaxy Z Fold 5", 60000.0); samsung.put("Galaxy Z Flip 5", 30000.0);
        TELEFON_DB.put("Samsung", samsung);

        Map<String, Double> xiaomi = new LinkedHashMap<>();
        xiaomi.put("Redmi Note 12", 5000.0); xiaomi.put("Redmi Note 12 Pro", 6500.0);
        xiaomi.put("Redmi Note 13", 6500.0); xiaomi.put("Redmi Note 13 Pro", 8500.0); xiaomi.put("Redmi Note 13 Pro+", 10000.0);
        xiaomi.put("Xiaomi 12", 11000.0); xiaomi.put("Xiaomi 12 Pro", 15000.0);
        xiaomi.put("Xiaomi 13", 16000.0); xiaomi.put("Xiaomi 13 Pro", 21000.0); xiaomi.put("Xiaomi 13T", 14000.0); xiaomi.put("Xiaomi 13T Pro", 18000.0);
        xiaomi.put("Xiaomi 14", 24000.0); xiaomi.put("Xiaomi 14 Ultra", 38000.0);
        TELEFON_DB.put("Xiaomi", xiaomi);

        Map<String, Double> oppo = new LinkedHashMap<>();
        oppo.put("Reno 10", 10000.0); oppo.put("Reno 10 Pro", 14000.0); oppo.put("Reno 11", 12000.0); oppo.put("Reno 11 Pro", 16000.0);
        oppo.put("Find X6 Pro", 30000.0); oppo.put("Find X7 Pro", 42000.0);
        TELEFON_DB.put("OPPO", oppo);

        Map<String, Double> oneplus = new LinkedHashMap<>();
        oneplus.put("OnePlus 11", 20000.0); oneplus.put("OnePlus 12", 30000.0); oneplus.put("OnePlus Nord 3", 11000.0); oneplus.put("OnePlus Nord CE4", 9500.0);
        TELEFON_DB.put("OnePlus", oneplus);

        Map<String, Double> google = new LinkedHashMap<>();
        google.put("Pixel 7", 18000.0); google.put("Pixel 7 Pro", 24000.0); google.put("Pixel 7a", 15000.0);
        google.put("Pixel 8", 28000.0); google.put("Pixel 8 Pro", 36000.0); google.put("Pixel 8a", 22000.0);
        google.put("Pixel 9", 36000.0); google.put("Pixel 9 Pro", 46000.0);
        TELEFON_DB.put("Google", google);

        Map<String, Double> huawei = new LinkedHashMap<>();
        huawei.put("Nova 11", 9000.0); huawei.put("Nova 12", 11000.0); huawei.put("P50 Pro", 18000.0); huawei.put("P60 Pro", 26000.0);
        huawei.put("Mate 50 Pro", 22000.0); huawei.put("Mate 60 Pro", 36000.0);
        TELEFON_DB.put("Huawei", huawei);

        Map<String, Double> motorola = new LinkedHashMap<>();
        motorola.put("Moto G84", 6000.0); motorola.put("Moto G Power", 5500.0); motorola.put("Edge 40", 10000.0);
        motorola.put("Edge 40 Pro", 14000.0); motorola.put("Edge 50 Pro", 18000.0); motorola.put("Razr 40 Ultra", 32000.0);
        TELEFON_DB.put("Motorola", motorola);
    }

    // Geçmiş sorgular listesi
    private final List<String> gecmisSorgular = new ArrayList<>();
    private double sonSonuc = 0;
    private List<String> sonFaktorler = new ArrayList<>();
    private String sonMarka = "", sonModel = "";

    @FXML private ComboBox<String> markaComboBox;
    @FXML private ComboBox<String> modelComboBox;
    @FXML private ComboBox<Integer> depolamaComboBox;
    @FXML private Slider pilSagligiSlider;
    @FXML private Label pilSagligiLabel;
    @FXML private TextField cihazYasiField;
    @FXML private Label arkaKameraLabel;
    @FXML private Label onKameraLabel;
    @FXML private Label ekranBoyutuLabel;
    @FXML private CheckBox garantiCheck;
    @FXML private ComboBox<String> garantiAyComboBox;
    @FXML private CheckBox kutuFaturaCheck;
    @FXML private CheckBox imeiCheck;
    @FXML private CheckBox besGCheck;
    @FXML private CheckBox hizliSarjCheck;
    @FXML private CheckComboBox<String> hasarTamirComboBox;
    @FXML private Label phoneResultLabel;
    @FXML private Label fiyatAraligLabel;
    @FXML private TextArea faktorOzetArea;
    @FXML private ListView<String> gecmisListView;

    @FXML
    public void initialize() {
        markaComboBox.getItems().add("Marka Seçiniz");
        markaComboBox.getItems().addAll(TELEFON_DB.keySet());
        markaComboBox.getSelectionModel().select(0);

        modelComboBox.getItems().add("Model Seçiniz");
        modelComboBox.getSelectionModel().select(0);
        modelComboBox.setDisable(true);

        depolamaComboBox.getItems().addAll(64, 128, 256, 512, 1024);
        depolamaComboBox.getSelectionModel().select(1);

        pilSagligiSlider.setMin(50); pilSagligiSlider.setMax(100); pilSagligiSlider.setValue(90);
        pilSagligiSlider.setMajorTickUnit(10); pilSagligiSlider.setShowTickLabels(true); pilSagligiSlider.setShowTickMarks(true);
        pilSagligiLabel.setText("90%");
        pilSagligiSlider.valueProperty().addListener((obs, oldVal, newVal) ->
                pilSagligiLabel.setText(newVal.intValue() + "%"));

        garantiAyComboBox.getItems().addAll("1-3 ay kaldı", "4-6 ay kaldı", "7-12 ay kaldı", "13-18 ay kaldı", "19-24 ay kaldı", "24+ ay kaldı");
        garantiAyComboBox.setDisable(true);
        garantiCheck.selectedProperty().addListener((obs, oldVal, newVal) -> {
            garantiAyComboBox.setDisable(!newVal);
            if (!newVal) garantiAyComboBox.getSelectionModel().clearSelection();
        });

        hasarTamirComboBox.getItems().addAll(
            "Kozmetik Hasar (Çizik/Çatlak)", "Ön Cam Kırık", "Arka Cam Kırık",
            "Ekran Değişimi Gördü", "Batarya Değişimi Gördü", "Kamera Değişimi Gördü",
            "Anakart Tamiri Gördü", "Su Hasarı Var"
        );

        markaComboBox.setOnAction(event -> {
            String secilenMarka = markaComboBox.getValue();
            if (secilenMarka != null && !secilenMarka.equals("Marka Seçiniz")) {
                modelComboBox.getItems().clear();
                modelComboBox.getItems().add("Model Seçiniz");
                modelComboBox.getItems().addAll(TELEFON_DB.get(secilenMarka).keySet());
                modelComboBox.getSelectionModel().select(0);
                modelComboBox.setDisable(false);
                sifirlaKameraEkran();

                modelComboBox.setOnAction(modelEvent -> {
                    String secilenModel = modelComboBox.getValue();
                    if (secilenModel != null && !secilenModel.equals("Model Seçiniz")) {
                        if (KAMERA_DB.containsKey(secilenModel)) {
                            int[] k = KAMERA_DB.get(secilenModel);
                            arkaKameraLabel.setText(k[0] + " MP");
                            onKameraLabel.setText(k[1] + " MP");
                        }
                        ekranBoyutuLabel.setText(EKRAN_DB.containsKey(secilenModel)
                            ? EKRAN_DB.get(secilenModel) + " inç" : "—");
                    } else { sifirlaKameraEkran(); }
                });
            } else {
                modelComboBox.getItems().clear();
                modelComboBox.getItems().add("Model Seçiniz");
                modelComboBox.getSelectionModel().select(0);
                modelComboBox.setDisable(true);
                sifirlaKameraEkran();
            }
        });
    }

    private void sifirlaKameraEkran() {
        arkaKameraLabel.setText("—"); onKameraLabel.setText("—"); ekranBoyutuLabel.setText("—");
    }

    @FXML
    protected void onCalculateButtonClick() {
        try {
            String marka = markaComboBox.getValue();
            String model = modelComboBox.getValue();
            if (marka == null || marka.equals("Marka Seçiniz") || model == null || model.equals("Model Seçiniz")) {
                phoneResultLabel.setText("Lütfen marka ve model seçiniz!");
                return;
            }
            int depolama = depolamaComboBox.getValue();
            int pilSagligi = (int) pilSagligiSlider.getValue();
            int cihazYasi = Integer.parseInt(cihazYasiField.getText().trim());

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

            ObservableList<String> secilenHasarlar = hasarTamirComboBox.getCheckModel().getCheckedItems();
            boolean kozmetikHasar = secilenHasarlar.stream().anyMatch(s -> s.contains("Kozmetik") || s.contains("Cam") || s.contains("Ekran"));
            boolean tamirGordu = secilenHasarlar.stream().anyMatch(s -> s.contains("Değişimi") || s.contains("Tamiri") || s.contains("Su"));

            double[] kesintiler = {0.08, 0.15, 0.10, 0.18, 0.05, 0.10, 0.28, 0.38};
            String[] hasarlar = {"Kozmetik Hasar (Çizik/Çatlak)", "Ön Cam Kırık", "Arka Cam Kırık",
                "Ekran Değişimi Gördü", "Batarya Değişimi Gördü", "Kamera Değişimi Gördü", "Anakart Tamiri Gördü", "Su Hasarı Var"};
            double hasarKesinti = 0.0;
            for (String hasar : secilenHasarlar)
                for (int i = 0; i < hasarlar.length; i++)
                    if (hasar.equals(hasarlar[i])) { hasarKesinti += kesintiler[i]; break; }

            int arkaMP = parseMP(arkaKameraLabel.getText());
            int onMP   = parseMP(onKameraLabel.getText());

            Phone telefon = new Phone(marka, model, depolama, pilSagligi, cihazYasi,
                    garantiCheck.isSelected(), kutuFaturaCheck.isSelected(), kozmetikHasar, tamirGordu,
                    imeiCheck.isSelected(), EKRAN_DB.getOrDefault(model, 6.1), arkaMP, onMP);

            double tabanFiyat = TELEFON_DB.get(marka).get(model);
            List<String> faktorler = new ArrayList<>();
            double sonuc = new PhonePricingEngine().tahminiDegerHesaplaDetayli(
                    telefon, tabanFiyat, hasarKesinti,
                    besGCheck.isSelected(), hizliSarjCheck.isSelected(),
                    arkaMP, onMP, kalanGarantiAy, faktorler);

            sonSonuc = sonuc;
            sonFaktorler = faktorler;
            sonMarka = marka;
            sonModel = model;

            // Sonuç
            phoneResultLabel.setText(String.format("Tahmini Değer: TL %,.0f", sonuc));

            // Fiyat aralığı
            double alt = Math.round(sonuc * PhonePricingEngine.ALT_CARPAN / 500.0) * 500.0;
            double ust = Math.round(sonuc * PhonePricingEngine.UST_CARPAN / 500.0) * 500.0;
            fiyatAraligLabel.setText(String.format("Piyasa Aralığı: TL %,.0f  —  TL %,.0f", alt, ust));

            // Faktör özeti
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("Taban fiyat: TL %,.0f%n", tabanFiyat));
            for (String f : faktorler) sb.append("  • ").append(f).append("\n");
            faktorOzetArea.setText(sb.toString());

            // Geçmişe ekle
            String zaman = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM HH:mm"));
            String kayit = String.format("[%s] %s %s → TL %,.0f", zaman, marka, model, sonuc);
            gecmisSorgular.add(0, kayit);
            gecmisListView.getItems().add(0, kayit);

        } catch (NumberFormatException e) {
            phoneResultLabel.setText("Hata: Cihaz yaşını ay olarak girin!");
        } catch (Exception e) {
            phoneResultLabel.setText("Hata: Verileri kontrol edin!");
        }
    }

    @FXML
    protected void onPdfSaveButtonClick() {
        if (sonSonuc == 0) {
            gosterUyari("Önce hesaplama yapın!");
            return;
        }
        FileChooser fc = new FileChooser();
        fc.setTitle("PDF Kaydet");
        fc.setInitialFileName(sonMarka + "_" + sonModel + "_ValuWise.pdf");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Dosyası", "*.pdf"));
        File dosya = fc.showSaveDialog(null);
        if (dosya == null) return;

        try (PDDocument pdf = new PDDocument()) {
            // Türkçe karakter desteği için sistem fontu
            java.io.File fontDosyasi = new java.io.File("/usr/share/fonts/liberation/LiberationSans-Regular.ttf");
            java.io.File fontBoldDosyasi = new java.io.File("/usr/share/fonts/liberation/LiberationSans-Bold.ttf");
            PDType0Font font = PDType0Font.load(pdf, fontDosyasi);
            PDType0Font fontBold = PDType0Font.load(pdf, fontBoldDosyasi);

            PDPage sayfa = new PDPage(PDRectangle.A4);
            pdf.addPage(sayfa);

            try (PDPageContentStream cs = new PDPageContentStream(pdf, sayfa)) {
                float margin = 50;
                float y = sayfa.getMediaBox().getHeight() - margin;
                float satirYuksekligi = 18;

                // Başlık
                cs.beginText();
                cs.setFont(fontBold, 16);
                cs.newLineAtOffset(margin, y);
                cs.showText("ValuWise - Telefon Değerleme Raporu");
                cs.endText();
                y -= satirYuksekligi * 1.5f;

                // Tarih
                cs.beginText();
                cs.setFont(font, 10);
                cs.newLineAtOffset(margin, y);
                cs.showText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
                cs.endText();
                y -= satirYuksekligi * 2;

                // Cihaz
                cs.beginText();
                cs.setFont(fontBold, 12);
                cs.newLineAtOffset(margin, y);
                cs.showText("Cihaz: " + sonMarka + " " + sonModel);
                cs.endText();
                y -= satirYuksekligi * 1.8f;

                // Faktörler başlık
                cs.beginText();
                cs.setFont(fontBold, 11);
                cs.newLineAtOffset(margin, y);
                cs.showText("Faktörler:");
                cs.endText();
                y -= satirYuksekligi;

                for (String f : sonFaktorler) {
                    if (y < margin + 60) break;
                    cs.beginText();
                    cs.setFont(font, 10);
                    cs.newLineAtOffset(margin + 10, y);
                    cs.showText("- " + f.replace("\u20BA", "TL").replace("₺", "TL"));
                    cs.endText();
                    y -= satirYuksekligi;
                }

                y -= satirYuksekligi;

                // Sonuç
                double alt = Math.round(sonSonuc * PhonePricingEngine.ALT_CARPAN / 500.0) * 500.0;
                double ust = Math.round(sonSonuc * PhonePricingEngine.UST_CARPAN / 500.0) * 500.0;
                cs.beginText();
                cs.setFont(fontBold, 14);
                cs.newLineAtOffset(margin, y);
                cs.showText(String.format("Tahmini Deger: TL %,.0f", sonSonuc));
                cs.endText();
                y -= satirYuksekligi * 1.4f;

                cs.beginText();
                cs.setFont(font, 11);
                cs.newLineAtOffset(margin, y);
                cs.showText(String.format("Piyasa Araligi: TL %,.0f - TL %,.0f", alt, ust));
                cs.endText();
            }

            pdf.save(dosya);
            gosterBilgi("PDF kaydedildi:\n" + dosya.getAbsolutePath());
        } catch (Exception e) {
            gosterUyari("PDF oluşturulamadı: " + e.getMessage());
        }
    }

    private void gosterUyari(String mesaj) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Uyarı"); alert.setContentText(mesaj); alert.showAndWait();
    }
    private void gosterBilgi(String mesaj) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Başarılı"); alert.setContentText(mesaj); alert.showAndWait();
    }

    private int parseMP(String text) {
        try { return Integer.parseInt(text.replace(" MP", "").trim()); }
        catch (Exception e) { return 12; }
    }

    @FXML
    protected void onKarsilastirGitButtonClick() {
        if (HelloController.instance != null) {
            HelloController.instance.karsilastirTabineGit();
        }
    }

    @FXML
    protected void onClearButtonClick() {
        markaComboBox.getSelectionModel().select(0);
        modelComboBox.getItems().clear(); modelComboBox.getItems().add("Model Seçiniz");
        modelComboBox.getSelectionModel().select(0); modelComboBox.setDisable(true);
        depolamaComboBox.getSelectionModel().select(1);
        pilSagligiSlider.setValue(90); pilSagligiLabel.setText("90%");
        cihazYasiField.clear(); sifirlaKameraEkran();
        garantiCheck.setSelected(false); garantiAyComboBox.getSelectionModel().clearSelection(); garantiAyComboBox.setDisable(true);
        kutuFaturaCheck.setSelected(false); imeiCheck.setSelected(true);
        besGCheck.setSelected(false); hizliSarjCheck.setSelected(false);
        hasarTamirComboBox.getCheckModel().clearChecks();
        phoneResultLabel.setText("Tahmini Değer: -- ₺");
        fiyatAraligLabel.setText("Piyasa Aralığı: -- ₺  —  -- ₺");
        faktorOzetArea.clear();
    }
}

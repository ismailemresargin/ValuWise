package com.example.valuwise.controller;

import com.example.valuwise.model.Pc;
import com.example.valuwise.service.pricing.PcPricingEngine;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class PcController {

    @FXML private ComboBox<String> pcCihazTipiCombo;
    @FXML private ComboBox<String> pcMarkaCombo;
    @FXML private ComboBox<String> pcModelAilesiCombo;
    @FXML private ComboBox<String> pcCihazYasiCombo;
    @FXML private ComboBox<String> pcIslemciMarkasiCombo;
    @FXML private ComboBox<String> pcIslemciAilesiCombo;
    @FXML private ComboBox<String> pcIslemciNesliCombo;
    @FXML private ComboBox<String> pcEkranKartiCombo;
    @FXML private ComboBox<String> pcRamKapasitesiCombo;
    @FXML private ComboBox<String> pcRamTipiCombo;
    @FXML private ComboBox<String> pcDepolamaKapasitesiCombo;
    @FXML private ComboBox<String> pcDepolamaTipiCombo;
    @FXML private ComboBox<String> pcEkranBoyutuCombo;

    @FXML private Slider pcBataryaSagligiSlider;
    @FXML private Label pcBataryaSagligiLabel;

    @FXML private CheckBox pcKozmetikHasarCheck;
    @FXML private CheckBox pcGarantiCheck;

    @FXML private Label pcResultLabel;
    @FXML private Label errorLabel;

    @FXML
    public void initialize() {
        // --- Combobox Verilerini Doldurma ---
        pcCihazTipiCombo.getItems().addAll("Seçiniz", "Masaüstü", "Dizüstü (Laptop)");
        pcMarkaCombo.getItems().addAll("Seçiniz", "Asus", "Apple", "Lenovo", "HP", "MSI", "Dell", "Monster", "Toplama/Diğer");
        pcModelAilesiCombo.getItems().addAll("Seçiniz", "ROG/Legion/Alienware", "TUF/Victus/Ideapad", "ThinkPad/EliteBook", "MacBook Air", "MacBook Pro", "Standart Ev/Ofis");

        pcIslemciMarkasiCombo.getItems().addAll("Seçiniz", "Intel", "AMD", "Apple");
        pcIslemciAilesiCombo.getItems().addAll("Seçiniz", "Core i3", "Core i5", "Core i7", "Core i9", "Ryzen 3", "Ryzen 5", "Ryzen 7", "Ryzen 9", "M1/M2/M3", "M1/M2/M3 Pro", "M1/M2/M3 Max");
        pcIslemciNesliCombo.getItems().addAll("Seçiniz", "Apple (Nesilsiz)", "14. Nesil (En Yeni)", "13. Nesil", "12. Nesil", "10-11. Nesil", "9. Nesil ve altı");

        pcEkranKartiCombo.getItems().addAll("Seçiniz", "Dahili (Intel/Radeon)", "RTX 4070/4080/4090", "RTX 4050/4060", "RTX 3060/3070/3080", "RTX 3050", "RTX 2060/2070", "GTX 1650/1660", "Radeon RX Serisi");

        pcRamKapasitesiCombo.getItems().addAll("Seçiniz", "4", "8", "16", "32", "64", "128");
        pcRamTipiCombo.getItems().addAll("Seçiniz", "DDR3", "DDR4", "DDR5", "LPDDR5 (Lehimli)");

        pcDepolamaKapasitesiCombo.getItems().addAll("Seçiniz", "128", "256", "512", "1024", "2048");
        pcDepolamaTipiCombo.getItems().addAll("Seçiniz", "HDD (Mekanik)", "SATA SSD", "NVMe M.2 SSD");

        pcEkranBoyutuCombo.getItems().addAll("Seçiniz", "Yok (Kasa)", "13.3", "14.0", "15.6", "16.1", "17.3");

        pcCihazYasiCombo.getItems().addAll("Seçiniz", "0 (Sıfır)", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10+");

        // Varsayılan seçimler
        sifirlaForm();

        // --- Dinamik Kontroller (Listeners) ---

        // Batarya Slider Etiketi
        pcBataryaSagligiSlider.setMin(0); pcBataryaSagligiSlider.setMax(100); pcBataryaSagligiSlider.setValue(90);
        pcBataryaSagligiSlider.valueProperty().addListener((obs, oldVal, newVal) ->
                pcBataryaSagligiLabel.setText(newVal.intValue() + "%")
        );

        // Cihaz Tipi Masaüstü seçilirse, bataryayı ve ekran boyutunu kilitle
        pcCihazTipiCombo.setOnAction(e -> {
            String tip = pcCihazTipiCombo.getValue();
            if ("Masaüstü".equals(tip)) {
                pcBataryaSagligiSlider.setValue(100);
                pcBataryaSagligiSlider.setDisable(true);
                pcEkranBoyutuCombo.getSelectionModel().select("Yok (Kasa)");
                pcEkranBoyutuCombo.setDisable(true);
            } else {
                pcBataryaSagligiSlider.setDisable(false);
                pcEkranBoyutuCombo.setDisable(false);
                pcEkranBoyutuCombo.getSelectionModel().select("Seçiniz");
            }
        });
    }

    @FXML
    protected void onCalculateButtonClick() {
        errorLabel.setVisible(false);
        pcResultLabel.setStyle("-fx-text-fill: #8e44ad;");

        try {
            // Zorunlu alanların kontrolü (Boş mu diye bakıyoruz)
            if (isInvalid(pcCihazTipiCombo) || isInvalid(pcMarkaCombo) || isInvalid(pcIslemciAilesiCombo) ||
                    isInvalid(pcRamKapasitesiCombo) || isInvalid(pcDepolamaKapasitesiCombo) || isInvalid(pcCihazYasiCombo)) {
                throw new IllegalArgumentException("Eksik veri girişi yapıldı!");
            }

            // Sayısal değerleri String'den Çevirme
            int ramKapasite = Integer.parseInt(pcRamKapasitesiCombo.getValue());
            int depolamaKapasite = Integer.parseInt(pcDepolamaKapasitesiCombo.getValue());
            int cihazYasi = 0;
            if (!pcCihazYasiCombo.getValue().equals("10+")) {
                cihazYasi = Integer.parseInt(pcCihazYasiCombo.getValue().split(" ")[0]);
            } else {
                cihazYasi = 10;
            }

            // Nesil çevirici ("12. Nesil" -> 12)
            int nesil = 0;
            String secilenNesil = pcIslemciNesliCombo.getValue();
            if (secilenNesil != null && secilenNesil.contains("Nesil") && !secilenNesil.contains("Apple")) {
                nesil = Integer.parseInt(secilenNesil.replaceAll("[^0-9]", ""));
            }

            // Ekran boyutu çevirici
            double ekranBoyutu = 0.0;
            if (!pcEkranBoyutuCombo.isDisable() && !isInvalid(pcEkranBoyutuCombo)) {
                ekranBoyutu = Double.parseDouble(pcEkranBoyutuCombo.getValue());
            }

            // Modeli Oluştur
            Pc yeniPc = new Pc(
                    "ID-" + System.currentTimeMillis(),
                    pcCihazTipiCombo.getValue(),
                    pcMarkaCombo.getValue(),
                    pcModelAilesiCombo.getValue(),
                    pcIslemciMarkasiCombo.getValue(),
                    pcIslemciAilesiCombo.getValue(),
                    nesil,
                    ramKapasite,
                    pcRamTipiCombo.getValue(),
                    pcEkranKartiCombo.getValue(),
                    pcDepolamaTipiCombo.getValue(),
                    depolamaKapasite,
                    ekranBoyutu,
                    cihazYasi,
                    (int) pcBataryaSagligiSlider.getValue(),
                    pcKozmetikHasarCheck.isSelected(),
                    pcGarantiCheck.isSelected()
            );

            // Motoru (Algoritmayı) Çağır
            double sonuc = new PcPricingEngine().tahminiDegerHesapla(yeniPc);
            pcResultLabel.setText(String.format("Tahmini Değer: %,.2f ₺", sonuc));

        } catch (IllegalArgumentException e) {
            errorLabel.setText("Lütfen zorunlu seçimleri (Tipi, Ram, Depolama vb.) yapınız!");
            errorLabel.setVisible(true);
        } catch (Exception e) {
            pcResultLabel.setText("Hata: Hesaplama yapılamadı!");
            pcResultLabel.setStyle("-fx-text-fill: red;");
            e.printStackTrace(); // Hata takibi için
        }
    }

    @FXML
    protected void onClearButtonClick() {
        sifirlaForm();
        errorLabel.setVisible(false);
        pcResultLabel.setText("Tahmini Değer: -- ₺");
        pcResultLabel.setStyle("-fx-text-fill: #8e44ad;");
    }

    // Helper metotlar
    private void sifirlaForm() {
        pcCihazTipiCombo.getSelectionModel().select(0);
        pcMarkaCombo.getSelectionModel().select(0);
        pcModelAilesiCombo.getSelectionModel().select(0);
        pcCihazYasiCombo.getSelectionModel().select(0);
        pcIslemciMarkasiCombo.getSelectionModel().select(0);
        pcIslemciAilesiCombo.getSelectionModel().select(0);
        pcIslemciNesliCombo.getSelectionModel().select(0);
        pcEkranKartiCombo.getSelectionModel().select(0);
        pcRamKapasitesiCombo.getSelectionModel().select(0);
        pcRamTipiCombo.getSelectionModel().select(0);
        pcDepolamaKapasitesiCombo.getSelectionModel().select(0);
        pcDepolamaTipiCombo.getSelectionModel().select(0);
        pcEkranBoyutuCombo.getSelectionModel().select(0);

        pcKozmetikHasarCheck.setSelected(false);
        pcGarantiCheck.setSelected(false);

        pcBataryaSagligiSlider.setValue(90);
        pcBataryaSagligiSlider.setDisable(false);
        pcEkranBoyutuCombo.setDisable(false);
        pcBataryaSagligiLabel.setText("90%");
    }

    private boolean isInvalid(ComboBox<String> combo) {
        return combo.getValue() == null || combo.getValue().equals("Seçiniz");
    }
}
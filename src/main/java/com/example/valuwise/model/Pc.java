package com.example.valuwise.model;

public class Pc extends Asset {
    private String cihazTipi;
    private String marka;
    private String modelAilesi;

    private String islemciMarkasi;
    private String islemciAilesi;
    private int islemciNesli;

    private int ramKapasitesiGB;
    private String ramTipi;

    private String ekranKartiModeli;

    private String depolamaTipi;
    private int depolamaKapasitesiGB;

    private double ekranBoyutuInc;
    private int cihazYasiYil;
    private int bataryaSagligiYuzdesi;
    private boolean kozmetikHasarVarMi;
    private boolean garantisiVarMi;

    public Pc(String id, String cihazTipi, String marka, String modelAilesi,
                    String islemciMarkasi, String islemciAilesi, int islemciNesli,
                    int ramKapasitesiGB, String ramTipi, String ekranKartiModeli,
                    String depolamaTipi, int depolamaKapasitesiGB, double ekranBoyutuInc,
                    int cihazYasiYil, int bataryaSagligiYuzdesi, boolean kozmetikHasarVarMi,
                    boolean garantisiVarMi) {
        super(id);
        this.cihazTipi = cihazTipi;
        this.marka = marka;
        this.modelAilesi = modelAilesi;
        this.islemciMarkasi = islemciMarkasi;
        this.islemciAilesi = islemciAilesi;
        this.islemciNesli = islemciNesli;
        this.ramKapasitesiGB = ramKapasitesiGB;
        this.ramTipi = ramTipi;
        this.ekranKartiModeli = ekranKartiModeli;
        this.depolamaTipi = depolamaTipi;
        this.depolamaKapasitesiGB = depolamaKapasitesiGB;
        this.ekranBoyutuInc = ekranBoyutuInc;
        this.cihazYasiYil = cihazYasiYil;
        this.bataryaSagligiYuzdesi = bataryaSagligiYuzdesi;
        this.kozmetikHasarVarMi = kozmetikHasarVarMi;
        this.garantisiVarMi = garantisiVarMi;
    }

    // --- Getter Metotları (Algoritma için gerekli) ---
    public String getCihazTipi() { return cihazTipi; }
    public String getMarka() { return marka; }
    public String getModelAilesi() { return modelAilesi; }
    public String getIslemciMarkasi() { return islemciMarkasi; }
    public String getIslemciAilesi() { return islemciAilesi; }
    public int getIslemciNesli() { return islemciNesli; }
    public int getRamKapasitesiGB() { return ramKapasitesiGB; }
    public String getRamTipi() { return ramTipi; }
    public String getEkranKartiModeli() { return ekranKartiModeli; }
    public String getDepolamaTipi() { return depolamaTipi; }
    public int getDepolamaKapasitesiGB() { return depolamaKapasitesiGB; }
    public double getEkranBoyutuInc() { return ekranBoyutuInc; }
    public int getCihazYasiYil() { return cihazYasiYil; }
    public int getBataryaSagligiYuzdesi() { return bataryaSagligiYuzdesi; }
    public boolean isKozmetikHasarVarMi() { return kozmetikHasarVarMi; }
    public boolean isGarantisiVarMi() { return garantisiVarMi; }

    @Override
    public void tahminiDegerHesapla() {
        // Hesaplama sorumluluğu ComputerPricingEngine sınıfında.
    }
}
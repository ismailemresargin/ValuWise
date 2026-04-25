package com.example.valuwise;

public class House extends Asset {
    private String ilanBasligi;
    private String adres;
    private double metrekare;
    private int odaSayisi;
    private int banyoSayisi;
    private int binaYasi;
    private int bulunduguKat;
    private boolean asansorVarMi;
    private boolean otoparkVarMi;
    private boolean denizManzarasiVarMi;
    private boolean depremeDayanikliMi;

    public House(String id, String ilanBasligi, String adres, double metrekare, int odaSayisi, int banyoSayisi,
                 int binaYasi, int bulunduguKat, boolean asansorVarMi, boolean otoparkVarMi,
                 boolean denizManzarasiVarMi, boolean depremeDayanikliMi) {
        super(id);
        this.ilanBasligi = ilanBasligi;
        this.adres = adres;
        this.metrekare = metrekare;
        this.odaSayisi = odaSayisi;
        this.banyoSayisi = banyoSayisi;
        this.binaYasi = binaYasi;
        this.bulunduguKat = bulunduguKat;
        this.asansorVarMi = asansorVarMi;
        this.otoparkVarMi = otoparkVarMi;
        this.denizManzarasiVarMi = denizManzarasiVarMi;
        this.depremeDayanikliMi = depremeDayanikliMi;
    }

    // Getters
    public String getIlanBasligi() { return ilanBasligi; }
    public String getAdres() { return adres; }
    public double getMetrekare() { return metrekare; }
    public int getBinaYasi() { return binaYasi; }
    public boolean isDenizManzarasiVarMi() { return denizManzarasiVarMi; }
    public boolean isOtoparkVarMi() { return otoparkVarMi; }
    public boolean isAsansorVarMi() { return asansorVarMi; }
    public boolean isDepremeDayanikliMi() { return depremeDayanikliMi; }

    @Override
    public void tahminiDegerHesapla() { /* Boş bırakılabilir */ }
}
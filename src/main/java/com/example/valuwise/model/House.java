package com.example.valuwise.model;

public class House extends Asset {
    private String konutTipi;
    private String adres;
    private String ilce;
    private double metrekare;
    private int odaSayisi;
    private int banyoSayisi;
    private int binaYasi;
    private int bulunduguKat;
    private boolean asansorVarMi;
    private boolean otoparkVarMi;
    private boolean denizManzarasiVarMi;
    private boolean depremeDayanikliMi;

    // yeni ekledigim veriler
    private boolean balkonVarMi;
    private boolean krediyeUygunMu;
    private String isinmaTipi;

    public House(String id, String konutTipi, String adres, String ilce, double metrekare, int odaSayisi, int banyoSayisi,
                 int binaYasi, int bulunduguKat, boolean asansorVarMi, boolean otoparkVarMi,
                 boolean denizManzarasiVarMi, boolean depremeDayanikliMi,
                 boolean balkonVarMi, boolean krediyeUygunMu, String isinmaTipi) {
        super(id);
        this.konutTipi = konutTipi;
        this.adres = adres;
        this.ilce = ilce;
        this.metrekare = metrekare;
        this.odaSayisi = odaSayisi;
        this.banyoSayisi = banyoSayisi;
        this.binaYasi = binaYasi;
        this.bulunduguKat = bulunduguKat;
        this.asansorVarMi = asansorVarMi;
        this.otoparkVarMi = otoparkVarMi;
        this.denizManzarasiVarMi = denizManzarasiVarMi;
        this.depremeDayanikliMi = depremeDayanikliMi;
        this.balkonVarMi = balkonVarMi;
        this.krediyeUygunMu = krediyeUygunMu;
        this.isinmaTipi = isinmaTipi;
    }

    // Getters
    public String getKonutTipi() { return konutTipi; }
    public int getOdaSayisi() { return odaSayisi; }
    public int getBanyoSayisi() { return banyoSayisi; }
    public int getBulunduguKat() { return bulunduguKat; }
    public String getAdres() { return adres; }
    public double getMetrekare() { return metrekare; }
    public int getBinaYasi() { return binaYasi; }
    public boolean isDenizManzarasiVarMi() { return denizManzarasiVarMi; }
    public boolean isOtoparkVarMi() { return otoparkVarMi; }
    public boolean isAsansorVarMi() { return asansorVarMi; }
    public boolean isDepremeDayanikliMi() { return depremeDayanikliMi; }
    public boolean isBalkonVarMi() { return balkonVarMi; }
    public boolean isKrediyeUygunMu() { return krediyeUygunMu; }
    public String getIsinmaTipi() { return isinmaTipi; }
    public String getIlce() { return ilce; }

    @Override
    public void tahminiDegerHesapla() { /* Boş bırakılabilir */ }
}
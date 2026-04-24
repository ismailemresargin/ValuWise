package com.example.valuwise.model;

public abstract class Asset {
    //Soyut sınıflar (Abstract Base Class) -----> Marka, Model gibi ortak özellikler

    private String id;           // SQLite için birincil anahtar
    private double tahminiDeger; // Sistemin ürettiği nihai sonuç (TL cinsinden)

    public Asset(String id) {
        this.id = id;
    }

    // Getter ve Setterlar
    public String getId() { return id; }
    public double getTahminiDeger() { return tahminiDeger; }
    public void setTahminiDeger(double tahminiDeger) { this.tahminiDeger = tahminiDeger; }

    /*
     Her ürün; bu metodu kendi içindeki özel değişkenleri kullanarak doldurmak zorundadır.
     */
    public abstract void tahminiDegerHesapla();
}

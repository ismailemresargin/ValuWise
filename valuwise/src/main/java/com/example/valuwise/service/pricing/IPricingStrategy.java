package com.example.valuwise.service.pricing;

/*
 - ValuWise Fiyatlandırma Stratejisi Arayüzü.
 - Bu arayüz, sisteme eklenecek tüm hesaplama motorları için bir standart belirler.
  <T> Değerlemesi yapılacak ürün tipi (House, Car, Phone vb.)
 */
public interface IPricingStrategy<T> {
    /* Ortak Algoritmaların fonksiyonları bu bölüme yazılacak
    Verilen nesnenin özelliklerine göre tahmini piyasa değerini hesaplar. */

    double tahminiDegerHesapla(T entity);
}
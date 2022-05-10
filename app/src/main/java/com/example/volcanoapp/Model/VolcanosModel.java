package com.example.volcanoapp.Model;

public class VolcanosModel {

    String nama, bentuk, tinggi, estimasi, geolokasi, gambar;

    public VolcanosModel() {

    }
    public VolcanosModel(String nama, String bentuk, String tinggi, String estimasi, String geolokasi, String gambar) {

        this.nama       =   nama;
        this.bentuk     =  bentuk;
        this.tinggi     =   tinggi;
        this.estimasi   =   estimasi;
        this.geolokasi  =   geolokasi;
        this.gambar     =   gambar;


    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getBentuk() {
        return bentuk;
    }

    public void setBentuk(String bentuk) {
        this.bentuk = bentuk;
    }

    public String getTinggi() {
        return tinggi;
    }

    public void setTinggi(String tinggi) {
        this.tinggi = tinggi;
    }

    public String getEstimasi() {
        return estimasi;
    }

    public void setEstimasi(String estimasi) {
        this.estimasi = estimasi;
    }

    public String getGeolokasi() {
        return geolokasi;
    }

    public void setGeolokasi(String geolokasi) {
        this.geolokasi = geolokasi;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }
}

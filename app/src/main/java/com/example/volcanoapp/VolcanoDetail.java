package com.example.volcanoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class VolcanoDetail extends AppCompatActivity {

    TextView tvNama, tvBentuk, tvTinggi, tvEstimasi, tvGeo;
    ImageView imgGunung;

    String image, nama, bentuk, tinggi, estimasi, geolokasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        // Inisialisasi imageView dan TextView pada DetailActivity

        tvNama          =   (TextView) findViewById(R.id.det_nama_gunung);
        tvBentuk        =   (TextView) findViewById(R.id.det_bentuk_gunung);
        tvTinggi        =   (TextView) findViewById(R.id.det_tinggi);
        tvEstimasi      =   (TextView) findViewById(R.id.det_estimasi);
        tvGeo           =   (TextView) findViewById(R.id.det_geo);
        imgGunung       =   (ImageView) findViewById(R.id.det_gambar_gunung);


        // Memanggil intent kemudian mengambil data yang telah dikirimkan menggunakan intent pada mainActivity


        Intent intent = getIntent();
        this.image      =   intent.getStringExtra("gambar");
        this.nama       =   intent.getStringExtra("nama_gunung");
        this.bentuk     =   intent.getStringExtra("bentuk_gunung");
        this.tinggi     =   intent.getStringExtra("tinggi_gunung");
        this.estimasi   =   intent.getStringExtra("estimasi_letusan");
        this.geolokasi  =   intent.getStringExtra("geolokasi");


        // Settext data ke dalam textView


        tvNama.setText(nama);
        tvBentuk.setText(bentuk);
        tvTinggi.setText(tinggi);
        tvEstimasi.setText(estimasi);
        tvGeo.setText(geolokasi);

        // Load ImageView menggunakan Glide

        Glide.with(VolcanoDetail.this)
                .load(image)
                .into(imgGunung);
    }
}
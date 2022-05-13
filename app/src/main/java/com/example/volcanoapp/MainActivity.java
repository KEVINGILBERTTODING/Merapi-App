package com.example.volcanoapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.volcanoapp.Adapter.AdapterData;
import com.example.volcanoapp.Adapter.SliderAdapter;
import com.example.volcanoapp.Model.SliderItem;
import com.example.volcanoapp.Model.VolcanosModel;
import com.example.volcanoapp.Utill.AppController;
import com.example.volcanoapp.Utill.ServerAPI;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import androidx.appcompat.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterData.ItemClickListener {
    RecyclerView mRecyclerview;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mManager;
    List<VolcanosModel> mItems;
    ProgressDialog pd;



    AdapterData adapterData;

    SliderView sliderView;
    private SliderAdapter adapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // Fungsi untuk menyembunyikan navbar

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);





        mRecyclerview = (RecyclerView) findViewById(R.id.recylerVolcano);
        pd = new ProgressDialog(MainActivity.this);
        mItems = new ArrayList<>();


        // Memanggil method loadJson

        loadJson();


        // Mengatur agar recycler view terbagi menjadi 2 rows

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);

//        mManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        mRecyclerview.setLayoutManager(gridLayoutManager);

        adapterData = new AdapterData(MainActivity.this,mItems);
        mRecyclerview.setAdapter(adapterData);
        adapterData.setClickListener(this);

        // Inisialisasi ImageButton kategori obat

        sliderView = findViewById(R.id.imageSlider);

        adapter = new SliderAdapter(this);
        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();

        sliderView.setOnIndicatorClickListener(new DrawController.ClickListener() {
            @Override
            public void onIndicatorClicked(int position) {
                Log.i("GGG", "onIndicatorClicked: " + sliderView.getCurrentPagePosition());
            }
        });

        renewItems();



        // Inisialisasi searchView

        SearchView searchView = findViewById(R.id.search_bar);
        searchView.clearFocus();


        // Fungsi saat memasukkan kata ke dalam searchview

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String querry) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });


    }

    // Method untuk realtime searchview

    private void filter(String newText) {

        ArrayList<VolcanosModel> filteredList = new ArrayList<>();

        for (VolcanosModel item : mItems) {
            if (item.getNama().toLowerCase().contains(newText.toLowerCase())) {
                filteredList.add(item);

            }
        }


        adapterData.filterList(filteredList);

//        mItems.clear();
//        mItems.addAll(filteredList);




        if (filteredList.isEmpty()) {
            Toast.makeText(this, "Tidak ditemukan", Toast.LENGTH_SHORT).show();
        } else {
            adapterData.filterList(filteredList);
        }



    }


    // Method untuk load data dari API


    private void loadJson() {
        pd.setMessage("Loading...");
        pd.setCancelable(false);
        pd.show();

        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerAPI.URL_LIST_VOLCANOS, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        pd.cancel();
                        Log.d("volley", "response : " + response.toString());
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                VolcanosModel md = new VolcanosModel();
                                md.setNama(data.getString("nama"));
                                md.setBentuk(data.getString("bentuk"));
                                md.setTinggi(data.getString("tinggi_meter"));
                                md.setEstimasi(data.getString("estimasi_letusan_terakhir"));
                                md.setGeolokasi(data.getString("geolokasi"));
                                md.setGambar(data.getString("image"));
                                mItems.add(md);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.cancel();
                        Log.d("volley", "error : " + error.getMessage());
                    }
                });

        AppController.getInstance().addToRequestQueue(reqData);
    }


    // Method untuk menambahkan image pada sliderImage

    public void renewItems() {
        List<SliderItem> sliderItemList = new ArrayList<>();
        //dummy data
        for (int i = 0; i < 2; i++) {
            SliderItem sliderItem = new SliderItem();
//            sliderItem.setDescription("Apoteker " + i); // for slider description
            if (i % 2 == 0) {
                sliderItem.setImageUrl("https://raw.githubusercontent.com/KEVINGILBERTTODING/.json/master/volcanoes/slider/slider1.png");
            }
            else {
                sliderItem.setImageUrl("https://raw.githubusercontent.com/KEVINGILBERTTODING/.json/master/volcanoes/slider/slider2.png");
            }
            sliderItemList.add(sliderItem);
        }
        adapter.renewItems(sliderItemList);
    }



    public void onClick(View view, int position) {

        final VolcanosModel volcanosModel = mItems.get(position);
        switch (view.getId()) {
//            case R.id.nama_gunung:
//                Intent detailVolcano = new Intent(MainActivity.this, DetailActivity.class);
//                detailVolcano.putExtra("gambar", volcanosModel.getGambar());
//                detailVolcano.putExtra("nama_gunung", volcanosModel.getNama());
//                detailVolcano.putExtra("bentuk_gunung", volcanosModel.getBentuk());
//                detailVolcano.putExtra("tinggi_gunung", volcanosModel.getTinggi());
//                detailVolcano.putExtra("estimasi_letusan", volcanosModel.getEstimasi());
//                detailVolcano.putExtra("geolokasi", volcanosModel.getGeolokasi());
//
//                startActivity(detailVolcano);
//
//                return;
//
//            case  R.id.gambar_gunung:
//                Intent intent1 = new Intent(MainActivity.this, DetailActivity.class);
//                intent1.putExtra("gambar", volcanosModel.getGambar());
//                intent1.putExtra("nama_gunung", volcanosModel.getNama());
//                intent1.putExtra("bentuk_gunung", volcanosModel.getBentuk());
//                intent1.putExtra("tinggi_gunung", volcanosModel.getTinggi());
//                intent1.putExtra("estimasi_letusan", volcanosModel.getEstimasi());
//                intent1.putExtra("geolokasi", volcanosModel.getGeolokasi());
//
//                startActivity(intent1);
//
//                return;
            default:
                Intent intent2 = new Intent(MainActivity.this, VolcanoDetail.class);
                intent2.putExtra("gambar", volcanosModel.getGambar());
                intent2.putExtra("nama_gunung", volcanosModel.getNama());
                intent2.putExtra("bentuk_gunung", volcanosModel.getBentuk());
                intent2.putExtra("tinggi_gunung", volcanosModel.getTinggi());
                intent2.putExtra("estimasi_letusan", volcanosModel.getEstimasi());
                intent2.putExtra("geolokasi", volcanosModel.getGeolokasi());

                startActivity(intent2);

        }

    }
}
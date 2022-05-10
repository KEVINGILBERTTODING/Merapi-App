package com.example.volcanoapp;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.volcanoapp.Adapter.AdapterData;
import com.example.volcanoapp.Model.VolcanosModel;
import com.example.volcanoapp.Utill.AppController;
import com.example.volcanoapp.Utill.ServerAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    RecyclerView mRecyclerview;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mManager;
    List<VolcanosModel> mItems;
    ProgressDialog pd;



    AdapterData adapterData;

    SearchView searchView;



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

        loadJson();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);

//        mManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        mRecyclerview.setLayoutManager(gridLayoutManager);

        adapterData = new AdapterData(MainActivity.this,mItems);
        mRecyclerview.setAdapter(adapterData);

        searchView = findViewById(R.id.search_bar);
        searchView.clearFocus();
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

    private void filter(String newText) {

        List<VolcanosModel> filteredList = new ArrayList<>();

        for (VolcanosModel item : mItems) {
            if (item.getNama().toLowerCase().contains(newText.toLowerCase())) {
                filteredList.add(item);
            }
        }

        adapterData.filterList(filteredList);

        if (filteredList.isEmpty()) {
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        } else {
            adapterData.filterList(filteredList);
        }



    }


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


//    private void filterList(String text) {
//
//        List<VolcanosModel> filteredList = new ArrayList<>();
//
//        for (VolcanosModel volcanosModel : mItems) {
//            if (volcanosModel.getNama().toLowerCase().contains(text.toLowerCase())) {
//
//                filteredList.add(volcanosModel);
//            }
//        }
//
//        if (filteredList.isEmpty()) {
//            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
//        } else {
//            adapterData.setFilteredList(filteredList);
//        }
//
//
//
//    }


}
package com.example.volcanoapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.volcanoapp.Model.VolcanosModel;
import com.example.volcanoapp.R;


import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;
import java.util.List;

public class AdapterData extends RecyclerView.Adapter<AdapterData.HolderData> {
    private List<VolcanosModel> mItems;
    private Context context;

    public AdapterData(Context context, List<VolcanosModel> items) {
        this.mItems = items;
        this.context = context;
    }



    @Override
    public HolderData onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_volcano, parent, false);
        HolderData holderData = new HolderData(layout);
        return holderData;
    }

    @Override
    public void onBindViewHolder(HolderData holder, int position) {
        VolcanosModel md = mItems.get(position);
        holder.tvnmGunung.setText(md.getNama());
        holder.tvBentuk.setText(md.getBentuk());
        holder.tvTinggi.setText(md.getTinggi());
        holder.tvEstimasi.setText(md.getEstimasi());
        holder.tvGeo.setText(md.getGeolokasi());

        Glide.with(context) //konteks bisa didapat dari activity yang sedang berjalan
                .load(mItems.get(position).getGambar()) // mengambildata dengan cara "list.get(position)" mendapatkan isi berupa objek Menu. kemudian "Menu.geturlGambar"
                .thumbnail(0.5f) // resize gambar menjadi setengahnya
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.imgVolcano); // mengisikan ke imageView

        holder.md = md;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void filterList(List<VolcanosModel> filteredList) {

        mItems = filteredList;
        notifyDataSetChanged();

    }



    class HolderData extends RecyclerView.ViewHolder {
        TextView tvnmGunung, tvBentuk, tvTinggi, tvEstimasi, tvGeo;
        ImageView imgVolcano;

        VolcanosModel md;

        public HolderData(View view) {
            super(view);

            tvnmGunung          = (TextView) view.findViewById(R.id.nama_gunung);
            tvBentuk            = (TextView) view.findViewById(R.id.bentuk_gunung);
            tvTinggi            = (TextView) view.findViewById(R.id.tinggi_gunung);
            tvEstimasi          = (TextView) view.findViewById(R.id.est_letusan);
            tvGeo               = (TextView) view.findViewById(R.id.geolokasi_gunung);
            imgVolcano          = (ImageView) view.findViewById(R.id.gambar_gunung);


        }
    }
}

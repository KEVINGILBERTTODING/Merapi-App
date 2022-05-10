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

    public interface ItemClickListener {
        void onClick(View view, int position);
    }



    private List<VolcanosModel> mItems;
    private Context context;

    public AdapterData(Context context, List<VolcanosModel> items) {
        this.mItems = items;
        this.context = context;
    }

    // Inisialisas itemClickListner


    private ItemClickListener itemClickListener;



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


        // Load gambar gunung menggunakan library glide

        Glide.with(context)
                .load(mItems.get(position).getGambar())
                .thumbnail(0.5f)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.imgVolcano);

        holder.md = md;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    // Method untuk filterlist

    public void filterList(List<VolcanosModel> filteredList) {

        mItems = filteredList;
        notifyDataSetChanged();

    }


    class HolderData extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvnmGunung, tvBentuk, tvTinggi, tvEstimasi, tvGeo;
        ImageView imgVolcano;

        VolcanosModel md;

        public HolderData(View view) {
            super(view);

            // Inisialisasi textView dan ImageView pada list_volcano.xml

            tvnmGunung          = (TextView) view.findViewById(R.id.nama_gunung);
            tvBentuk            = (TextView) view.findViewById(R.id.bentuk_gunung);
            tvTinggi            = (TextView) view.findViewById(R.id.tinggi_gunung);
            tvEstimasi          = (TextView) view.findViewById(R.id.est_letusan);
            tvGeo               = (TextView) view.findViewById(R.id.geolokasi_gunung);
            imgVolcano          = (ImageView) view.findViewById(R.id.gambar_gunung);


            // Memanggil object

            tvnmGunung.setOnClickListener(this);
            imgVolcano.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {

            if (itemClickListener != null) itemClickListener.onClick(view, getAdapterPosition());

        }
    }
}

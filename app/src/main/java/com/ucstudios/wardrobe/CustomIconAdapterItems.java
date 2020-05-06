package com.ucstudios.wardrobe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;


import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class CustomIconAdapterItems extends BaseAdapter {


    private static final String TAG = "CustomIconAdapterItems";
    private Integer[] Icons = {
            R.drawable.nobrand,
            R.drawable.ic_nike,
            R.drawable.ic_adidas,
            R.drawable.ic_aber,
            R.drawable.ic_armani,
            R.drawable.ic_asics,
            R.drawable.ic_diadora,
            R.drawable.ic_eddiebauer,
            R.drawable.ic_bershka,
            R.drawable.ic_fila,
            R.drawable.ic_gaudi,
            R.drawable.ic_hollister,
            R.drawable.ic_kappa,
            R.drawable.ic_lacoste,
            R.drawable.ic_oakley,
            R.drawable.ic_pullbear,
            R.drawable.ic_seiko,
            R.drawable.ic_americaneagle,
            R.drawable.ic_billabong,
            R.drawable.ic_burton,
            R.drawable.ic_ck,
            R.drawable.ic_celio,
            R.drawable.ic_champions,
            R.drawable.ic_chanel,
            R.drawable.ic_coach,
            R.drawable.ic_columbia,
            R.drawable.ic_dior,
            R.drawable.ic_gap,
            R.drawable.ic_gucci,
            R.drawable.ic_hm,
            R.drawable.ic_hermes,
            R.drawable.ic_boss,
            R.drawable.ic_lee,
            R.drawable.ic_levis,
            R.drawable.ic_lv,
            R.drawable.ic_mk,
            R.drawable.ic_napa,
            R.drawable.ic_nb,
            R.drawable.ic_puma,
            R.drawable.ic_quicksilver,
            R.drawable.ic_rayban,
            R.drawable.ic_stradivarius,
            R.drawable.ic_supreme,
            R.drawable.ic_northface,
            R.drawable.ic_trasher,
            R.drawable.ic_timberland,
            R.drawable.ic_under,
            R.drawable.ic_vans,
            R.drawable.ic_volcom,
            R.drawable.ic_wrangler,
            R.drawable.ic_zara,

    };

    private int imageLabels;
    private LayoutInflater thisInflater;
    private Context mContext;
    int mResource;

    public CustomIconAdapterItems(Context context, int labs){
        this.thisInflater = LayoutInflater.from(context);
        this.imageLabels = labs;

    }





    @Override
    public int getCount() {
        return Icons.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ViewHolder")
    public View getView(int position, View convertView, ViewGroup parent) {


            convertView = thisInflater.inflate(R.layout.adapter_icon_picker, parent, false);

            ImageView imageView = convertView.findViewById(R.id.imageView2);
            imageView.setImageResource(Icons[position]);




        return convertView;




    }
}
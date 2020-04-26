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

public class CustomIconAdapter extends BaseAdapter {


    private static final String TAG = "CustomIconAdapter";
    MainActivity mainActivity;
    private Integer[] Icons = {
            R.drawable.ic_sweater,
            R.drawable.ic_jeans,
            R.drawable.ic_hoodie,
            R.drawable.ic_shoes,
            R.drawable.ic_backpack,
            R.drawable.ic_denim,
            R.drawable.ic_shirt,
            R.drawable.ic_watch,
            R.drawable.ic_basketballjersey,
            R.drawable.ic_bathrobe,
            R.drawable.ic_belt,
            R.drawable.ic_blouse,
            R.drawable.ic_boot,
            R.drawable.ic_boot2,
            R.drawable.ic_bowtie,
            R.drawable.ic_bra,
            R.drawable.ic_cap,
            R.drawable.ic_coat,
            R.drawable.ic_dress,
            R.drawable.ic_glasses,
            R.drawable.ic_gloves,
            R.drawable.ic_bag,
            R.drawable.ic_hat,
            R.drawable.ic_heels,
            R.drawable.ic_jacket,
            R.drawable.ic_pimuno,
            R.drawable.ic_jacket2,
            R.drawable.ic_necklace,
            R.drawable.ic_salopette,
            R.drawable.ic_mutandefemmina,
            R.drawable.ic_cargo,
            R.drawable.ic_polo,
            R.drawable.ic_24h,
            R.drawable.ic_purse,
            R.drawable.ic_scarf,
            R.drawable.ic_tee,
            R.drawable.ic_top,
            R.drawable.ic_mocasso,
            R.drawable.ic_shorts,
            R.drawable.ic_papere,
            R.drawable.ic_skirt,
            R.drawable.ic_slippers,
            R.drawable.ic_socks,
            R.drawable.ic_tie,
            R.drawable.ic_trench,
            R.drawable.ic_underwear,
            R.drawable.ic_vest,
            R.drawable.ic_wallet,
            R.drawable.ic_winterhat,







    };

    private int imageLabels;
    private LayoutInflater thisInflater;
    private Context mContext;
    int mResource;

    public CustomIconAdapter(Context context, int labs){
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

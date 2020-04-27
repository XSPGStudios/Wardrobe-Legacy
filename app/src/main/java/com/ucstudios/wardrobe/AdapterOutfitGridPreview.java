package com.ucstudios.wardrobe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;


public class AdapterOutfitGridPreview extends BaseAdapter {
    public static final String TAG = "AdapterOutfitGridPreview";
    private LayoutInflater thisInflater;
    ArrayList<ArrayList<byte[]>> imagedata;
    ImageView imageView;

    public AdapterOutfitGridPreview(Context context, ArrayList<ArrayList<byte[]>> imagedata){
        this.thisInflater= LayoutInflater.from(context);
        this.imagedata = imagedata;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = thisInflater.inflate(R.layout.singlepreviewitemoutfitgrid,parent,false);
        imageView = convertView.findViewById(R.id.imageviewpreview);

        imageView.setImageBitmap(Utils.getImage(imagedata.get(position).get(1)));
        return convertView;
    }
}

package com.ucstudios.wardrobe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AdapterOutfitGrid extends BaseAdapter {
    public static final String TAG = "AdapterOutfitGrid";
    private LayoutInflater thisInflater;
    private Context mContext;
    ArrayList<String> itemname;
    TextView text;
    ArrayList<ArrayList<byte[]>> imagedata;
    ImageView imageView;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    GridView ssss;
    public AdapterOutfitGrid(Context context, ArrayList<String> itemname,ArrayList<ArrayList<byte[]>> alloutfitimagess) {
        this.thisInflater = LayoutInflater.from(context);
        this.mContext=context;
        this.itemname = itemname;
        this.imagedata = alloutfitimagess;
    }

    @Override
    public int getCount(){
        return itemname.size();
    }

    @Override
    public Object getItem(int position){return null;}

    @Override
    public long getItemId(int position){
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        convertView = thisInflater.inflate(R.layout.adapter_outfit_grid,parent,false);
        text = convertView.findViewById(R.id.textViewGridOutfit);
        text.setText(itemname.get(position));
        ssss=convertView.findViewById(R.id.gridViewOutfits);
        AdapterOutfitGridPreview adapterOutfitGridPreview = new AdapterOutfitGridPreview(mContext,imagedata);
        ssss.setAdapter(adapterOutfitGridPreview);


        return convertView;
    }

}


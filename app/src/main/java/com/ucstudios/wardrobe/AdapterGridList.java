package com.ucstudios.wardrobe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AdapterGridList extends BaseAdapter {

    public static final String TAG = "AdapterGridList";

    private LayoutInflater thisInflater;
    private Context mContext;
    int mResource;
    ArrayList<byte[]> drugs;
    ArrayList<String> tattico;
    ImageView image;
    TextView text;
    public AdapterGridList(Context context, ArrayList<byte[]> drugs,ArrayList<String> tattico){
        this.thisInflater=LayoutInflater.from(context);
        this.drugs=drugs;
        this.tattico=tattico;
    }



    @Override
    public int getCount() {
        return drugs.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = thisInflater.inflate(R.layout.adapter_grid_list,parent,false);
        image = convertView.findViewById(R.id.imageView2312);
        text = convertView.findViewById(R.id.textView666);
        image.setClipToOutline(true);
        image.setImageBitmap(Utils.getImage(drugs.get(position)));
        text.setText(tattico.get(position));


        return convertView;
    }
}

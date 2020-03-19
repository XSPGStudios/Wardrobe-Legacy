package com.ucstudios.wardrobe;

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
    private Integer[] Icons = {
            R.drawable.ic_sweater,
            R.drawable.ic_jeans,
            R.drawable.ic_hoodie,


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

    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null) {

            convertView = thisInflater.inflate(R.layout.adapter_icon_picker, parent, false);

            ImageView imageView = convertView.findViewById(R.id.imageView2);
            imageView.setImageResource(Icons[position]);

        }


        return convertView;



    }
}

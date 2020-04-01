package com.ucstudios.wardrobe;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

public class AdapterListView extends ArrayAdapter<String> {


    private static final String TAG = "AdapterList";
    private Context mContext;
    int mResource;

    CustomEditDialog dialog = new CustomEditDialog(getContext());
    MainActivity mainActivity;

    private Integer[] Icons = {
            R.drawable.ic_nike,
            R.drawable.ic_adidas,
            R.drawable.ic_alexmc,


    };




    public DatabaseHelper mDatabaseHelper;


    public AdapterListView(Context context, int adapter_list,List<String> listData) {
        super(context, adapter_list, listData);
        mResource = adapter_list;
        this.mContext=context;


    }













    public View getView(int position, View convertView, ViewGroup parent){

        mDatabaseHelper = new DatabaseHelper(getContext());



        String category = getItem(position);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);
        final LinearLayout linearLayout = convertView.findViewById(R.id.LinearLayout123);
        final TextView textView = convertView.findViewById(R.id.textView4);
        final ImageView imageView = convertView.findViewById(R.id.imageView);
        final ConstraintLayout cocco = convertView.findViewById(R.id.cocconegro);


        textView.setText(category);

        return convertView;



    }






}

package com.ucstudios.wardrobe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.List;

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
        final ImageView imageView = convertView.findViewById(R.id.imageView231);
        final ConstraintLayout cocco = convertView.findViewById(R.id.cocconegro);


        textView.setText(category);

        return convertView;



    }






}

package com.ucstudios.wardrobe;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class VisualDialogOutfit extends Dialog{
    RecyclerView mRecyclerView;
    ArrayList<byte[]> imagedata;
    List<String> ecco;
    ArrayList<String> categories;
    ArrayList<Integer> positions;


    public VisualDialogOutfit(Context context, ArrayList<byte[]> imagedata, List<String> ecco,ArrayList<String> Categories, ArrayList<Integer> positions){
        super(context);
        this.imagedata=imagedata;
        this.ecco=ecco;
        this.categories=Categories;
        this.positions=positions;
    }

        //mettere sdk 22 al posto di 19
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_visual_outfit);
        mRecyclerView = findViewById(R.id.spezzamoltepliciossa);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        RecyclerAdapterOutfitVisual adapter = new RecyclerAdapterOutfitVisual(getContext(),imagedata,ecco,categories,positions);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(adapter);

    }
}

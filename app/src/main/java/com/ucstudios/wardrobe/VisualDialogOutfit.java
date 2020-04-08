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


    public VisualDialogOutfit(Context context, ArrayList<byte[]> imagedata, List<String> ecco){
        super(context);
        this.imagedata=imagedata;
        this.ecco=ecco;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_visual_outfit);
        mRecyclerView = findViewById(R.id.spezzamoltepliciossa);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        RecyclerAdapterOutfitVisual adapter = new RecyclerAdapterOutfitVisual(getContext(),imagedata,ecco);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(adapter);

    }
}

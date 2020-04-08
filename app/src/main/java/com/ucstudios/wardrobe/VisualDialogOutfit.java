package com.ucstudios.wardrobe;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class VisualDialogOutfit extends Dialog{
    RecyclerView mRecyclerView;
    ArrayList<byte[]> imagedata;
    int position;


    public VisualDialogOutfit(Context context, ArrayList<byte[]> imagedata, int position){
        super(context);
        this.imagedata=imagedata;
        this.position=position;
    }

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_visual_outfit);
        mRecyclerView = findViewById(R.id.spezzamoltepliciossa);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        RecyclerAdapterOutfitVisual adapter = new RecyclerAdapterOutfitVisual(getContext(),imagedata,position);
        mRecyclerView.setAdapter(adapter);
    }
}

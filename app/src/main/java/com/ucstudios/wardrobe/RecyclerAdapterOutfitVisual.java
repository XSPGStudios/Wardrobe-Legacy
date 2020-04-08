package com.ucstudios.wardrobe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RecyclerAdapterOutfitVisual extends RecyclerView.Adapter<RecyclerAdapterOutfitVisual.ViewHolder> implements ItemTouchHelperAdapter{

    private static final String TAG = "RecyclerAdapterOutfit";
    int count = 0;
    String[] items;
    Context mContext;
    View.OnClickListener mClickListener;
    ArrayList<byte[]> imagedata;
    int position;



    public RecyclerAdapterOutfitVisual(Context context, ArrayList<byte[]> imagedata, int position) {
        this.mContext=context;
        this.imagedata=imagedata;
        this.position=position;


    }




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Log.i(TAG,"onCreateViewHolder: " + count++ );
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View view = layoutInflater.inflate(R.layout.adapter_outfit_visual, parent, false);
        RecyclerAdapterOutfitVisual.ViewHolder viewHolder = new RecyclerAdapterOutfitVisual.ViewHolder(view);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onClick(view);
            }
        });


        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    holder.imageView.setImageBitmap(Utils.getImage(imagedata.get(position)));

    }

    @Override
    public int getItemCount() {

        return items.length;
    }

    @Override
    public void onItemDismiss(int position) {
        notifyItemRemoved(position);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(Arrays.asList(items), i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(Arrays.asList(items), i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    public void setClickListener(View.OnClickListener callback){
        mClickListener = callback;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView23a);





        }
    }
}
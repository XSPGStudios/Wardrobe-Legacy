package com.ucstudios.wardrobe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.icu.text.MessagePattern;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RecyclerAdapterOutfitVisual extends RecyclerView.Adapter<RecyclerAdapterOutfitVisual.ViewHolder>{

    private static final String TAG = "RecyclerAdapterOutfit";
    int count = 0;

    Context mContext;
    View.OnClickListener mClickListener;
    ArrayList<byte[]> imagedatas;
    String[] ecco;
    ArrayList<String> categories;
    ArrayList<Integer> positions;
    private Integer[] Icons = {
            R.drawable.ic_sweater,
            R.drawable.ic_jeans,
            R.drawable.ic_hoodie,
            R.drawable.ic_shoes,
            R.drawable.ic_backpack,
            R.drawable.ic_denim,
            R.drawable.ic_shirt,
            R.drawable.ic_watch,
            R.drawable.ic_basketballjersey,
            R.drawable.ic_bathrobe,
            R.drawable.ic_belt,
            R.drawable.ic_blouse,
            R.drawable.ic_boot,
            R.drawable.ic_boot2,
            R.drawable.ic_bowtie,
            R.drawable.ic_bra,
            R.drawable.ic_coat,
            R.drawable.ic_dress,
            R.drawable.ic_glasses,
            R.drawable.ic_gloves,
            R.drawable.ic_bag,
            R.drawable.ic_hat,
            R.drawable.ic_heels,
            R.drawable.ic_jacket,
            R.drawable.ic_pimuno,
            R.drawable.ic_jacket2,
            R.drawable.ic_necklace,
            R.drawable.ic_salopette,
            R.drawable.ic_mutandefemmina,
            R.drawable.ic_cargo,
            R.drawable.ic_polo,
            R.drawable.ic_24h,
            R.drawable.ic_purse,
            R.drawable.ic_scarf,
            R.drawable.ic_tee,
            R.drawable.ic_top,
            R.drawable.ic_mocasso,
            R.drawable.ic_shorts,
            R.drawable.ic_papere,
            R.drawable.ic_skirt,
            R.drawable.ic_slippers,
            R.drawable.ic_socks,
            R.drawable.ic_tie,
            R.drawable.ic_trench,
            R.drawable.ic_underwear,
            R.drawable.ic_vest,
            R.drawable.ic_wallet,
            R.drawable.ic_winterhat,


    };



    public RecyclerAdapterOutfitVisual(Context context, ArrayList<byte[]> imagedata, List<String> ecco,ArrayList<String> Categories,ArrayList<Integer> Positions) {
        this.mContext=context;
        this.imagedatas=imagedata;
        this.ecco=ecco.toArray(new String[0]);
        this.categories=Categories;
        this.positions=Positions;


    }




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Log.i(TAG,"onCreateViewHolder: " + count++ );
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View view = layoutInflater.inflate(R.layout.adapter_outfit_visual, parent, false);
        RecyclerAdapterOutfitVisual.ViewHolder viewHolder = new RecyclerAdapterOutfitVisual.ViewHolder(view);
     /*   viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onClick(view);
            }
        });*/


        return viewHolder;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imageView.setClipToOutline(true);
         holder.imageView.setImageBitmap(Utils.getImage(imagedatas.get(position)));
        int positionprecise = positions.get(position);
         switch(positionprecise){
             case 1:
                 holder.positionView.setImageResource(R.drawable.ic_basket);
                 break;
             case 2:
                 holder.positionView.setImageResource(R.drawable.ic_wm);
                 break;
             case 3:
                 break;


        }
        String category = categories.get(position);
         holder.categoryView.setImageResource(Icons[Integer.parseInt(category)]);


    }

    @Override
    public int getItemCount() {
        return ecco.length;
    }

  //  @Override
    //public void onItemDismiss(int position) {
      //  notifyItemRemoved(position);
    //}

    /*@Override
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
    }*/


    public void setClickListener(View.OnClickListener callback){
        mClickListener = callback;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        ImageView categoryView;
        ImageView positionView;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView23a);
            textView = itemView.findViewById(R.id.as);
            categoryView = itemView.findViewById(R.id.imageViewCAT);
            positionView = itemView.findViewById(R.id.imageViewPOS);






        }
    }
}
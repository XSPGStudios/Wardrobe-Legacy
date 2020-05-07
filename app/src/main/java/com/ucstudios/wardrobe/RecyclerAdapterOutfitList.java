package com.ucstudios.wardrobe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RecyclerAdapterOutfitList extends RecyclerView.Adapter<RecyclerAdapterOutfitList.ViewHolder> implements ItemTouchHelperAdapter{

    private static final String TAG = "RecyclerAdapterOutfit";
    int count = 0;
    String[] items;
    Context mContext;
    View.OnClickListener mClickListener;
    DatabaseHelper mDatabaseHelper;
    int[] wearabilitycontrol;



    public RecyclerAdapterOutfitList(Context context,List <String> items,int[] wearability) {
        this.mContext=context;
        this.items = items.toArray(new String[0]);
        this.wearabilitycontrol=wearability;

    }




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mDatabaseHelper = new DatabaseHelper(mContext);
        Log.i(TAG,"onCreateViewHolder: " + count++ );
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View view = layoutInflater.inflate(R.layout.adapter_list, parent, false);
        RecyclerAdapterOutfitList.ViewHolder viewHolder = new RecyclerAdapterOutfitList.ViewHolder(view);
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

        holder.textView.setText(items[position]);
        holder.imageView.setImageResource(R.drawable.ic_outfit);
        if(wearabilitycontrol[position]==1){
        holder.iconView.setVisibility(View.VISIBLE);           //If outfit has at least one item not available, it won't be available overall;
        holder.iconView.setImageResource(R.drawable.ic_wm);
        }
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
        try{
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
        mDatabaseHelper.SwapRowsOutfit(fromPosition+1,toPosition+1);

        return true;
    }catch (Exception e){
            return false;
        }

    }

    public void setClickListener(View.OnClickListener callback){
        mClickListener = callback;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;
        ImageView iconView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);


        imageView = itemView.findViewById(R.id.imageView231);
        textView = itemView.findViewById(R.id.textView4);
        iconView = itemView.findViewById(R.id.imageView5);



    }
    }
}
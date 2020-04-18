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



    public RecyclerAdapterOutfitList(Context context,List <String> items) {
        this.mContext=context;
        this.items = items.toArray(new String[0]);

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
        mDatabaseHelper.SwapRowsOutfit(fromPosition+1,toPosition+1);

        return true;
    }

    public void setClickListener(View.OnClickListener callback){
        mClickListener = callback;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);


        imageView = itemView.findViewById(R.id.imageView231);
        textView = itemView.findViewById(R.id.textView4);



    }
    }
}
package com.ucstudios.wardrobe;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RecyclerAdapterItems extends RecyclerView.Adapter<RecyclerAdapterItems.ViewHolder> implements  ItemTouchHelperAdapter{

    private static final String TAG = "RecyclerAdapter";
    Context mContext;
    int count = 0;
    String[] items;




    public RecyclerAdapterItems(Context context, List<String> items) {
        this.mContext=context;
        this.items = items.toArray(new String[0]);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        Log.i(TAG,"onCreateViewHolder: " + count++ );
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.adapter_list, parent, false);
        RecyclerAdapterItems.ViewHolder viewHolder = new RecyclerAdapterItems.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(items[position]);
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






    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView4);




        }
    }
}

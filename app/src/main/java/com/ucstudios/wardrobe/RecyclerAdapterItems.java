package com.ucstudios.wardrobe;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class RecyclerAdapterItems extends RecyclerView.Adapter<RecyclerAdapterItems.ViewHolder> implements  ItemTouchHelperAdapter{

    private static final String TAG = "RecyclerAdapter";
    DatabaseHelper mDatabaseHelper;
    Context mContext;
    int count = 0;
    String[] items;
    View.OnClickListener mClickListener;
    Integer[] ppos;
    Integer[] icons;
    List<byte[]>tech;
    private Integer[] Icons = {
            R.drawable.ic_nike,
            R.drawable.ic_adidas,
            R.drawable.ic_alexmc,


    };

    private Integer[] iconwm ={
            R.drawable.ic_wm,
            R.drawable.ic_basket,
    };
    AdapterListResult mAdapterResult;

    String table;




    public RecyclerAdapterItems(Context context,List<Integer> position2, List<String> items,List<Integer> icons,List<byte[]> tech,String table) {
        this.mContext=context;
        this.items = items.toArray(new String[0]);
        this.ppos = position2.toArray(new Integer[0]);
        this.icons = icons.toArray(new Integer[0]);
        this.tech= tech;
        this.table=table;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mDatabaseHelper = new DatabaseHelper(mContext);
        Log.i(TAG,"onCreateViewHolder: " + count++ );
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View view = layoutInflater.inflate(R.layout.adapter_list, parent, false);
        RecyclerAdapterItems.ViewHolder viewHolder = new RecyclerAdapterItems.ViewHolder(view);
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

        holder.imageView.setImageResource(Icons[icons[position]]);
        //holder.imageView.setImageBitmap(Utils.getImage(tech.get(position))); Mostra immagine vera
        if(ppos[position]==1){
            holder.imageView2.setImageResource(iconwm[1]);
            holder.imageView2.setVisibility(View.VISIBLE);

        }
        if(ppos[position]==2){
            holder.imageView2.setImageResource(iconwm[0]);
            holder.imageView2.setVisibility(View.VISIBLE);
        }
        /*Crea varibile GifImageView che contiene appunto la gif,
        dopodichè passala all'holder gif, se è uguale a 3, allora starta la gif*/
        /*if(ppos[position]==3){
            holder.imageView2.setImageResource();
            holder.imageView2.setVisibility(View.VISIBLE);
        }*/
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
        Log.i("position1","ecco"+fromPosition);
        Log.i("position2","ecco"+toPosition);
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
        mDatabaseHelper.SwapRows(fromPosition+1,toPosition+1,table);

        return true;
    }



    public void setClickListener(View.OnClickListener callback){
        mClickListener = callback;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        ImageView imageView2;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView2 = itemView.findViewById(R.id.imageView5);
            imageView = itemView.findViewById(R.id.imageView231);
            textView = itemView.findViewById(R.id.textView4);




        }
    }

    public void setAdapterResult(AdapterListResult adapterResult){

        mAdapterResult = adapterResult;
    }



    public interface AdapterListResult{
        void finish(String result);
    }
}

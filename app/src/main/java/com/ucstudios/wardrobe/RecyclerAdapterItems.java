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

public class RecyclerAdapterItems extends RecyclerView.Adapter<RecyclerAdapterItems.ViewHolder> implements  ItemTouchHelperAdapter{

    private static final String TAG = "RecyclerAdapter";
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
            R.drawable.ic_basket
    };
    AdapterListResult mAdapterResult;
    ArrayList<String> asd;




    public RecyclerAdapterItems(Context context,List<Integer> position2, List<String> items,List<Integer> icons,List<byte[]> tech) {
        this.mContext=context;
        this.items = items.toArray(new String[0]);
        this.ppos = position2.toArray(new Integer[0]);
        this.icons = icons.toArray(new Integer[0]);
        this.tech= tech;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


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

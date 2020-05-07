package com.ucstudios.wardrobe;

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

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> implements ItemTouchHelperAdapter{

    private static final String TAG = "RecyclerAdapter";
    int count = 0;
    String[] items;
    DatabaseHelper db;
    public ArrayList<Integer> position2;
    ArrayList<Integer> icons;
    private Integer[] Icons = {
            R.drawable.nobrand,
            R.drawable.ic_nike,
            R.drawable.ic_adidas,
            R.drawable.ic_aber,
            R.drawable.ic_armani,
            R.drawable.ic_asics,
            R.drawable.ic_diadora,
            R.drawable.ic_eddiebauer,
            R.drawable.ic_bershka,
            R.drawable.ic_fila,
            R.drawable.ic_gaudi,
            R.drawable.ic_hollister,
            R.drawable.ic_kappa,
            R.drawable.ic_lacoste,
            R.drawable.ic_oakley,
            R.drawable.ic_pullbear,
            R.drawable.ic_seiko,
            R.drawable.ic_americaneagle,
            R.drawable.ic_billabong,
            R.drawable.ic_burton,
            R.drawable.ic_ck,
            R.drawable.ic_celio,
            R.drawable.ic_champions,
            R.drawable.ic_chanel,
            R.drawable.ic_coach,
            R.drawable.ic_columbia,
            R.drawable.ic_dior,
            R.drawable.ic_gap,
            R.drawable.ic_gucci,
            R.drawable.ic_hm,
            R.drawable.ic_hermes,
            R.drawable.ic_boss,
            R.drawable.ic_lee,
            R.drawable.ic_levis,
            R.drawable.ic_lv,
            R.drawable.ic_mk,
            R.drawable.ic_napa,
            R.drawable.ic_nb,
            R.drawable.ic_puma,
            R.drawable.ic_quicksilver,
            R.drawable.ic_rayban,
            R.drawable.ic_stradivarius,
            R.drawable.ic_supreme,
            R.drawable.ic_northface,
            R.drawable.ic_trasher,
            R.drawable.ic_timberland,
            R.drawable.ic_under,
            R.drawable.ic_vans,
            R.drawable.ic_volcom,
            R.drawable.ic_wrangler,
            R.drawable.ic_zara,


    };

    public RecyclerAdapter(List <String> items,ArrayList<Integer> icons) {
        this.items = items.toArray(new String[0]);
        this.icons = icons;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Log.i(TAG,"onCreateViewHolder: " + count++ );
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);



        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.textView.setText(items[position]);
        holder.imageView.setImageResource(Icons[icons.get(position)]);

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
        Button button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            imageView = itemView.findViewById(R.id.imageView231);
                textView = itemView.findViewById(R.id.textView4);
                    button = itemView.findViewById(R.id.buttonWardrobe);


        }
    }
}

package com.ucstudios.wardrobe;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RecyclerAdapterCategories extends RecyclerView.Adapter<RecyclerAdapterCategories.ViewHolder> implements ItemTouchHelperAdapter {

    private static final String TAG = "RecyclerAdapterCategories";
    DatabaseHelper mDatabaseHelper;
    Context mContext;                   //Dichiara variabili globali
    View.OnClickListener mClickListener;
    EditCategoriesDialog dialog;
    Integer[] icons;
    ArrayList<String> listdata=new ArrayList<>();
    private Integer[] Icons = {
            R.drawable.ic_sweater,
            R.drawable.ic_jeans,
            R.drawable.ic_hoodie,
            R.drawable.ic_shoes,
            R.drawable.ic_backpack,
            R.drawable.ic_denim,
            R.drawable.ic_shirt,
            R.drawable.ic_watch,


    };
    String[] items;
    MainActivity mainActivity;


    public RecyclerAdapterCategories(Context context, ArrayList<String> listdata,List<Integer> icons){
        this.mContext=context; //Setta in context le variabili
        this.items= listdata.toArray(new String[0]);
        this.icons=icons.toArray(new Integer[0]);

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mDatabaseHelper=new DatabaseHelper(mContext);
        dialog = new EditCategoriesDialog(mContext,1);
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View view = layoutInflater.inflate(R.layout.adapter_list, parent, false);
        RecyclerAdapterCategories.ViewHolder viewHolder = new RecyclerAdapterCategories.ViewHolder(view);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onClick(view);
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterCategories.ViewHolder holder, int position) {
                     holder.textView.setText(items[position]);
                     holder.imageView.setImageResource(Icons[icons[position]]);
                     //setta i valori degli holders
    }

    @Override
    public int getItemCount() {
        return items.length;
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
        mDatabaseHelper.SwapRowsCategories(fromPosition+1,toPosition+1);

        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        notifyItemRemoved(position);

    }

    public void setClickListener(View.OnClickListener callback){mClickListener = callback;}

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        ImageView imageView2;  //chiama gli hodler

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView2 = itemView.findViewById(R.id.imageView5);
            imageView = itemView.findViewById(R.id.imageView231);
            textView = itemView.findViewById(R.id.textView4);


            //inizializza gli holder



        }
    }


}

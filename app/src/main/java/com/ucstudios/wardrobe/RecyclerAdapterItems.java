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
    onMyAdapterinoResult onmyAdapterinoResult;



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
    public void setAdapterResult(onMyAdapterinoResult adapterResult){

        onmyAdapterinoResult = adapterResult;

    }





    public interface onMyAdapterinoResult{

        void finish(String result);
    }






    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView4);
            final Button buttonEdit = itemView.findViewById(R.id.buttonEdit);
            final Button buttonWash = itemView.findViewById(R.id.button6);
            final ConstraintLayout cocco = itemView.findViewById(R.id.cocconegro);
            final LinearLayout linearLayout = itemView.findViewById(R.id.LinearLayout123);


            buttonEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()){
                        case R.id.buttonEdit:
                            final CustomEditDialog dialog = new CustomEditDialog(mContext);
                            dialog.show();
                            dialog.setDialogResult(new CustomEditDialog.OnMyDialogResult() {
                                @Override
                                public void finish(String result) {

                                    String cocaina = String.valueOf(result);
                                    Log.i("Coca ", "ecco "+cocaina);
                                    if(!cocaina.equals(""))
                                    {
                                        if(!cocaina.equals("CANE")){
                                            textView.setText(result);}
                                        onmyAdapterinoResult.finish(result);
                                        dialog.dismiss();}

                                    else{
                                        Toast.makeText(mContext, "IT MUST CONTAIN SOMETHING!", Toast.LENGTH_SHORT).show();

                                    }


                                }

                            });
                            buttonEdit.setVisibility(View.GONE);
                            buttonWash.setVisibility(View.GONE);


                    }
                }
            });



            linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    buttonWash.setVisibility(View.VISIBLE);
                    buttonEdit.setVisibility(View.VISIBLE);





                    cocco.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            buttonWash.setVisibility(View.GONE);
                            buttonEdit.setVisibility(View.GONE);
                            cocco.setClickable(false);

                        }
                    });



                    return false;
                }
            });


        }
    }
}

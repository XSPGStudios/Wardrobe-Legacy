package com.ucstudios.wardrobe;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.List;

public class AdapterCategories extends ArrayAdapter<String> {

    private static final String TAG = "AdapterCategories";
    private Context mContext;
    int mResource;
    private onMyAdapterResult1 mAdapterResult12;
    EditCategoriesDialog dialog = new EditCategoriesDialog(getContext());
    DatabaseHelper mDatabaseHelper = new DatabaseHelper(getContext());

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


    public AdapterCategories( Context context, int adapter_categories, List<String> listData) {
        super(context, adapter_categories, listData);
        mResource = adapter_categories;
        this.mContext=context;
    }

    public void setAdapterResult(onMyAdapterResult1 adapterResult){
       mAdapterResult12 = adapterResult;
    }

    public interface onMyAdapterResult1{

        void finish(String result);
    }

    public View getView(final int position, View convertView, ViewGroup parent){

        Cursor data = mDatabaseHelper.getData();
        final ArrayList<String> IconData = new ArrayList<>();
        while (data.moveToNext()) {
            IconData.add(data.getString(2));
        }


        String category = getItem(position);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);
        final TextView textView = convertView.findViewById(R.id.textView4);
        final Button editButton = convertView.findViewById(R.id.buttonEdit);
        final ImageView imageView = convertView.findViewById(R.id.imageView);
        LinearLayout linearLayout = convertView.findViewById(R.id.LinearLayout123);
        final ConstraintLayout constraintLayout = convertView.findViewById(R.id.cocconegro);


        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("Msg","TEST");
                dialog.show();
                dialog.setDialogResult(new EditCategoriesDialog.OnMyDialogResult4() {
                    @Override
                    public void finish(String result1,int icon) {

                        String cocaina = String.valueOf(result1);
                        Log.i("Coca", "ecco "+cocaina);
                        if(!cocaina.equals(""))
                        {
                            if(!cocaina.equals("CANE")&!cocaina.equals("culocane")){
                                textView.setText(result1);}
                                mAdapterResult12.finish(result1);
                                dialog.dismiss();
                            if(cocaina=="culocane"){
                                mAdapterResult12.finish(result1);
                                mDatabaseHelper.ReplaceIcon(icon,position+1);
                                imageView.setImageResource(Icons[icon]);
                                dialog.dismiss();

                            }
                            }


                        else if(cocaina=="CANE"){
                            dialog.dismiss();
                        }


                        else {
                                Toast.makeText(mContext,"IT MUST CONTAIN SOMETHING!", Toast.LENGTH_SHORT).show();
                            }
                        }

                });
                editButton.setVisibility(View.GONE);
            }
        });





        linearLayout.setOnLongClickListener(new View.OnLongClickListener() {




            @Override
            public boolean onLongClick(View v) {

                editButton.setVisibility(View.VISIBLE);

                constraintLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        editButton.setVisibility(View.GONE);
                        constraintLayout.setClickable(false);
                    }
                });

//

                return false;

            }
        });





        imageView.setImageResource(Icons[Integer.parseInt(IconData.get(position))]);
        textView.setText(category);

        return  convertView;

    }
}

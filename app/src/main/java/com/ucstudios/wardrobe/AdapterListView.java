package com.ucstudios.wardrobe;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.security.PublicKey;
import java.util.List;
import java.util.jar.Attributes;

public class AdapterListView extends ArrayAdapter<String> {

    private static final String TAG = "AdapterList";
    private Context mContext;
    int mResource;
    onMyAdapterResult mAdapterResult;
    CustomEditDialog dialog = new CustomEditDialog(getContext());
    MainActivity mainActivity;




    public DatabaseHelper mDatabaseHelper;


    public AdapterListView(Context context, int adapter_list,List<String> listData) {
        super(context, adapter_list, listData);
        mResource = adapter_list;
        this.mContext=context;


    }


    public void setAdapterResult(onMyAdapterResult adapterResult){

        mAdapterResult = adapterResult;

    }





    public interface onMyAdapterResult{

        void finish(String result);
    }










    public View getView(int position, View convertView, ViewGroup parent){

        mDatabaseHelper = new DatabaseHelper(getContext());

        String category = getItem(position);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);
        final LinearLayout linearLayout = convertView.findViewById(R.id.LinearLayout123);
        final TextView textView = convertView.findViewById(R.id.textView4);
        final ImageView imageView = convertView.findViewById(R.id.imageView);
        final Button buttonEdit = convertView.findViewById(R.id.buttonEdit);


        final Button buttonWash = convertView.findViewById(R.id.button6);
        final ConstraintLayout cocco = convertView.findViewById(R.id.cocconegro);

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.buttonEdit:

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
                                        mAdapterResult.finish(result);
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






        textView.setText(category);

        return convertView;



    }






}

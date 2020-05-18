package com.ucstudios.wardrobe;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


public class CalendarAddEvent extends Dialog {
    DatabaseHelper mDatabaseHelper;
    ArrayList<Integer> DateSelected;
    boolean isedit;
    Spinner mSpinner;
    ArrayList<byte[]> imagedatas;
    List<String> ecco;
    ArrayList<String> categoriesa;
    ArrayList<Integer>positions;
    RecyclerView mRecyclerView;
    public CalendarAddEvent(Context context,ArrayList<Integer> DateSelected,Boolean isedit){
        super(context);
        this.DateSelected=DateSelected;
        this.isedit=isedit;



    }




    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.calendaraddevent);
        mDatabaseHelper = new DatabaseHelper(getContext());
        String Day = DateSelected.get(0).toString();
        String Month = DateSelected.get(1).toString();
        String Year = DateSelected.get(2).toString();
        ArrayList<String> patetico2 = new ArrayList<>();




        if(Month.length()==1){
            List<String> patetico = new ArrayList<>();
            patetico.add("0");
            patetico.add(Month);

            Month= TextUtils.join("",patetico);
        }


        patetico2.add(Day);
        patetico2.add(Month);
        patetico2.add(Year);
        final String totalcode = TextUtils.join("",patetico2);
        final String total = TextUtils.join(" ",patetico2);
        final Integer Datecode = Integer.parseInt(totalcode);


        Button button = findViewById(R.id.buttonInsertEvent);
        mRecyclerView = findViewById(R.id.spezzamoltepliciossaeventspreview);
        TextView textView = findViewById(R.id.CurrentDateSelected);
        Button buttondelete = findViewById(R.id.buttondelete);
        textView.setText(total);


        Cursor data = mDatabaseHelper.getData2();
        final ArrayList<String> listData = new ArrayList<>();

        while (data.moveToNext()) {
            listData.add(data.getString(0));

        }

        mSpinner = findViewById(R.id.OutfitPickerSpinner);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                previewpopulation();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getContext(), android.R.layout.simple_spinner_item, listData);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        previewpopulation();




        if(isedit){
            button.setText("Replace");
            buttondelete.setVisibility(View.VISIBLE);
            buttondelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDatabaseHelper.deleteevent(Datecode);
                    dismiss();
                    Toast.makeText(getContext(),"Event deleted!",Toast.LENGTH_SHORT).show();
                }
            });
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isedit){
                mDatabaseHelper.addEvent(Datecode,mSpinner.getSelectedItem().toString());
                Toast.makeText(getContext(),"Event created on "+ total,Toast.LENGTH_SHORT).show();}
                else{
                    mDatabaseHelper.ReplaceOutfitEvent(Datecode,mSpinner.getSelectedItem().toString());
                    Toast.makeText(getContext(),"Outfit Replaced",Toast.LENGTH_SHORT).show();
                }
                dismiss();
            }
        });




    }

    protected void previewpopulation(){

        Cursor categories = mDatabaseHelper.getData();
        final ArrayList<String> categories2 = new ArrayList<>();
        while (categories.moveToNext()){
            categories2.add(categories.getString(0)); //Categorie
        }
        Cursor data = mDatabaseHelper.getData2();
        final ArrayList<String> listData = new ArrayList<>();

        while (data.moveToNext()) {
            listData.add(data.getString(0));//Outfits

        }
        int position=0;
        for(int i=0;i<listData.size();i++){
            if(listData.get(i).equals(mSpinner.getSelectedItem().toString())){
                position=i;
            }

        }

        final ArrayList<String> OutfitComponents = new ArrayList<>();
        final ArrayList<String> OutfitCategories = new ArrayList<>();
        final ArrayList<Integer> OutfitPOS = new ArrayList<>();
        final ArrayList<String> AllComponents = new ArrayList<>();
        Cursor GetCatIcon = mDatabaseHelper.getData();
        while (GetCatIcon.moveToNext()){
            AllComponents.add(GetCatIcon.getString(1));
        }

        for(int i = 0; i<categories2.size();i++){

            Cursor c = mDatabaseHelper.GetItemOutfit(categories2.get(i),listData.get(position));
            while(c.moveToNext()){



                if(c.getString(0)!=null){

                    OutfitComponents.add(c.getString(0));
                    OutfitCategories.add(AllComponents.get(i));
                }
                else{

                }
            }

        }
        final ArrayList<byte[]> fumo = new ArrayList<>();
        for(int u=0;u<categories2.size();u++){
            for(int i =0;i<OutfitComponents.size();i++) {
                Cursor cursor =  mDatabaseHelper.GetByteOutfit(categories2.get(u),OutfitComponents.get(i));
                Cursor cursorpos = mDatabaseHelper.getPrecisePos(categories2.get(u),OutfitComponents.get(i));
                while(cursorpos.moveToNext()){
                    if(OutfitComponents.get(i)!="null"){
                        OutfitPOS.add(cursorpos.getInt(0));
                    }
                }
                while(cursor.moveToNext()){
                    if(OutfitComponents.get(i)!="null") {
                        fumo.add(cursor.getBlob(0));
                    }
                }
            }
        }


        Log.i("Componenti outfit ","Ecco "+OutfitComponents);

        imagedatas=fumo;
        ecco=OutfitComponents;
        categoriesa=OutfitCategories;
        positions=OutfitPOS;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        RecyclerAdapterOutfitVisual recyclerAdapterOutfitVisual = new RecyclerAdapterOutfitVisual(getContext(),imagedatas,ecco,categoriesa,positions);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(recyclerAdapterOutfitVisual);



    }





}

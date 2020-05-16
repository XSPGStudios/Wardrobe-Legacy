package com.ucstudios.wardrobe;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EventVisualDialog extends Dialog {
    DatabaseHelper mDatabaseHelper;
    String outfitame;
    ArrayList<byte[]> imagedatas;
    List<String> ecco;
    ArrayList<String> categoriesa;
    ArrayList<Integer>positions;
    ArrayList<Integer> DateSelected;
    String total;

    public EventVisualDialog (Context context,String Outfit,ArrayList<Integer> dateselected){
        super(context);
        this.outfitame=Outfit;
        this.DateSelected=dateselected;


    }

    void Trafficadata(){
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

        total = TextUtils.join(" ",patetico2);


    }

    protected void onCreate(Bundle savedIstanceState){
        super.onCreate(savedIstanceState);
        Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_visual_event);
        mDatabaseHelper = new DatabaseHelper(getContext());
        RecyclerView mRecyclerView = findViewById(R.id.spezzamoltepliciossaevents);
        TextView textview = findViewById(R.id.OutfitName);
        Trafficadata();
        TextView textvieweventdate = findViewById(R.id.nameeevent);
        textvieweventdate.setText(total);
        textview.setText(outfitame);
        Button buttonedit = findViewById(R.id.editevent);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        previewpopulation();
        RecyclerAdapterOutfitVisual adapterOutfitVisual = new RecyclerAdapterOutfitVisual(getContext(),imagedatas,ecco,categoriesa,positions);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(adapterOutfitVisual);


        buttonedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalendarAddEvent dialog = new CalendarAddEvent(getContext(),DateSelected,true);
                dialog.show();
                dialog.setOnDismissListener(new OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        dismiss();
                    }
                });
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
            if(listData.get(i).equals(outfitame)){
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


    }



}

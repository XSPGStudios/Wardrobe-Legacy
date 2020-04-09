package com.ucstudios.wardrobe;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class OutfitGridFragment extends Fragment implements View.OnClickListener{

    DatabaseHelper mDatabasehelper;
    GridView gridView;

    public OutfitGridFragment(){}

    public static OutfitGridFragment newInstance(){
        OutfitGridFragment fragment = new OutfitGridFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_outfit_grid,container,false);
        FloatingActionButton floatingActionButton = view.findViewById(R.id.floating_action_button_grid_outfit);
        floatingActionButton.setOnClickListener(this);
        Button listbutton = view.findViewById(R.id.buttonlistOutfit);
        listbutton.setOnClickListener(this);
        mDatabasehelper = new DatabaseHelper(getActivity());
        gridView = view.findViewById(R.id.gridViewOutfit);
        Spinner spinner = view.findViewById(R.id.spinner_navs);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position ==1){
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.container,new SmsFragment());
                    transaction.commit();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }



        });
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getActivity(),R.array.options2,android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter1);
        populategridoutfit();
        return view;
    }

    public void populategridoutfit(){
        Cursor data = mDatabasehelper.getData2();
        final ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()){
            listData.add(data.getString(0));
        }
        Cursor categories = mDatabasehelper.getData();
        final ArrayList<String> categories2 = new ArrayList<>();
        while (categories.moveToNext()){
            categories2.add(categories.getString(1));
        }
        final ArrayList<ArrayList<String>> AllOutfitComponents = new ArrayList<>();

        for(int i=0;i<listData.size();i++){
            final ArrayList<String> currentrow = new ArrayList<>();
            for(int u=0;u<categories2.size();u++){
                Cursor c = mDatabasehelper.GetItemOutfit(categories2.get(u),listData.get(i));

               while(c.moveToNext()){
                    if(c.getString(0)!=null){
                    currentrow.add(c.getString(0));
                }}

            }
            AllOutfitComponents.add(currentrow);

        }

        final ArrayList<ArrayList<byte[]>> AllOutfitComponentsPics = new ArrayList<>();
        for(int i=0;i<AllOutfitComponents.size();i++){
            final ArrayList<byte[]> currentrow = new ArrayList<>();
            for(int u=0;u<categories2.size();u++){
                for(int z=0;z<AllOutfitComponents.get(i).size();z++){
                    Cursor c = mDatabasehelper.GetByteOutfit(categories2.get(u),AllOutfitComponents.get(i).get(z));
                    while(c.moveToNext()){
                        currentrow.add(c.getBlob(0));
                    }
                }
            }
            AllOutfitComponentsPics.add(currentrow);
        }
        Log.i("magianegrissima  ","ecco "+ AllOutfitComponents);
       AdapterOutfitGrid adapterOutfitGrid = new AdapterOutfitGrid(getContext(),listData,AllOutfitComponentsPics);
        gridView.setAdapter(adapterOutfitGrid);
    }


    @Override
    public void onClick(View v) {
       switch (v.getId()){
        case R.id.buttonlistOutfit:
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.container, new OutfitFragment());
            transaction.commit();

       }

    }
}

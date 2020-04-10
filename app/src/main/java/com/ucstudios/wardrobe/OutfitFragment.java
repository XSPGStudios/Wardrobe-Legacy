package com.ucstudios.wardrobe;


import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OutfitFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";



    DatabaseHelper mDatabaseHelper;
    RecyclerView mListview;
    private String mParam1;
    private String mParam2;
    public int a=0;
    ArrayList<String> dro = new ArrayList<>();
    ArrayList<String> drog = new ArrayList<>();


    public OutfitFragment() {

    }


    public static OutfitFragment newInstance() {
        OutfitFragment fragment = new OutfitFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_outfit, container, false);
        FloatingActionButton floatingActionButton = view.findViewById(R.id.floating_action_button);
        floatingActionButton.setOnClickListener(this);
        Button gridbutton = view.findViewById(R.id.gridbutton1);
        gridbutton.setOnClickListener(this);
        mDatabaseHelper = new DatabaseHelper(getActivity());
        mListview = view.findViewById(R.id.spezzaossa4);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(mListview);

        Spinner spinner = view.findViewById(R.id.spinner_nav);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.container, new SmsFragment());
                    transaction.commit();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getActivity(),
                R.array.options2, android.R.layout.simple_spinner_item);

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter1);

        populateOutfits();


        return view;
    }

    public void populateOutfits() {
        Cursor data = mDatabaseHelper.getData2();
        final ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()) {
            listData.add(data.getString(0));
            dro.add("0");
        }
        Cursor categories = mDatabaseHelper.getData();
        final ArrayList<String> categories2 = new ArrayList<>();
        while (categories.moveToNext()){
            categories2.add(categories.getString(1));
        }
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        final RecyclerAdapterOutfitList adapter = new RecyclerAdapterOutfitList(getContext(), listData);
        mDatabaseHelper.GetNullOutfitName();
        mListview.setLayoutManager(layoutManager);
        mListview.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
        mListview.addItemDecoration(dividerItemDecoration);
        boolean risultato = longClickListener.onLongClick(mListview);
        adapter.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = mListview.indexOfChild(v);
                final ArrayList<String> OutfitComponents = new ArrayList<>();
                for(int i = 0; i<categories2.size();i++){
                    Cursor c = mDatabaseHelper.GetItemOutfit(categories2.get(i),listData.get(position));
                    while(c.moveToNext()){
                        if(c.getString(0)!=null){
                        OutfitComponents.add(c.getString(0));
                        drog.add("1");
                    }
                        else{
                            drog.add("0");
                        }
                    }

                }
                final ArrayList<byte[]> fumo = new ArrayList<>();
                for(int u=0;u<categories2.size();u++){
                    for(int i =0;i<OutfitComponents.size();i++) {
                      Cursor cursor =  mDatabaseHelper.GetByteOutfit(categories2.get(u),OutfitComponents.get(i));
                        while(cursor.moveToNext()){
                            if(OutfitComponents.get(i)!="null") {
                                fumo.add(cursor.getBlob(0));
                            }
                        }
                    }
                }


                Log.i("Componenti outfit ","Ecco "+OutfitComponents);
               VisualDialogOutfit dialog = new VisualDialogOutfit(getContext(),fumo,OutfitComponents);
               dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
               dialog.show();

            }
        });

    }

    View.OnLongClickListener longClickListener = new View.OnLongClickListener() {

        @Override
        public boolean onLongClick(View v) {


            ClipData merda = ClipData.newPlainText("", "");
            View.DragShadowBuilder myShadowBuilder = new View.DragShadowBuilder(v);
            v.startDrag(merda, myShadowBuilder, v, 0);
            return true;
        }
    };

   public void AddOutfit(String cocco){
        mDatabaseHelper.addData2(cocco);

   }

   ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

   @SuppressLint("SetTextI18n")
    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        final int position = viewHolder.getAdapterPosition();

        switch (direction) {

            case ItemTouchHelper.LEFT:
                Cursor data = mDatabaseHelper.getData2();
                final ArrayList<String> listData = new ArrayList<>();
                while (data.moveToNext()) {
                    listData.add(data.getString(0));
                    dro.add("0");
                }
                Cursor categories = mDatabaseHelper.getData();
                final ArrayList<String> categories2 = new ArrayList<>();
                while (categories.moveToNext()){
                    categories2.add(categories.getString(1));
                }
                final ArrayList<String> OutfitComponents = new ArrayList<>();
                for(int i = 0; i<categories2.size();i++){
                    Cursor c = mDatabaseHelper.GetItemOutfit(categories2.get(i),listData.get(position));

                    while(c.moveToNext()){
                        if(c.getString(0)!=null){
                            OutfitComponents.add(c.getString(0));
                            drog.add("1");

                        }
                        else{
                            drog.add("0");
                        }


                    }


                }
                final ArrayList<Integer> SpinnerValue = new ArrayList<>();
                for(int i=0;i<categories2.size();i++){
                    if(drog.get(i).equals("0")){
                        SpinnerValue.add(-1);
                    }
                    for(int u=0;u<OutfitComponents.size();u++){
                        Cursor c = mDatabaseHelper.GetSpecificIdItem(categories2.get(i),OutfitComponents.get(u));
                        while(c.moveToNext()){
                        if(!drog.get(i).equals("0")){
                            SpinnerValue.add(c.getInt(0));
                        }

                    }

                    }


                }
                Log.i("Ecco componenti","Ecco"+SpinnerValue);
                Log.i("Ecco Valori","Ecco"+drog);
                CustomDialogClass customDialogClass = new CustomDialogClass(getActivity(),drog,1,SpinnerValue);
                customDialogClass.show();
                customDialogClass.setAdapterResult(new CustomDialogClass.OnMyAdapterResult() {
                    @Override
                    public void finish(String result) {

                        if(!result.equals("")&&!result.equals("ELIMINAZIONETOTALE")){
                            mDatabaseHelper.ReplaceOutfit(result,position+1);
                            mDatabaseHelper.GetNullOutfitName();

                        }
                        else if(result.equals("ELIMINAZIONETOTALE")){
                            mDatabaseHelper.delete4("Outfit_Table",listData.get(position));
                            populateOutfits();
                            Log.i("msg","Eliminazione completata!");
                        }

                    }
                });

                customDialogClass.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        mDatabaseHelper.GetNullOutfitName();
                        populateOutfits();

                    }
                });


                break;


        }
    }



    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                .addSwipeLeftBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorAccent))
                .addSwipeLeftActionIcon(R.drawable.ic_edit24)
                .create()
                .decorate();
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }
};



    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.floating_action_button:
                ArrayList<Integer> cocco = new ArrayList<>();
                CustomDialogClass cdd = new CustomDialogClass(getActivity(),dro,0,cocco);
                cdd.show();
                cdd.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        mDatabaseHelper.GetNullOutfitName();
                    }
                });
                cdd.setAdapterResult(new CustomDialogClass.OnMyAdapterResult() {
                    @Override
                    public void finish(String result) {
                       if (!result.equals("mattototale")){AddOutfit(result);
                        populateOutfits(); }
                       else{
                           Toast.makeText(getContext(),"Creazione Outfit interrotta!",Toast.LENGTH_SHORT).show();
                       }
                    }
                });
                break;

            case R.id.gridbutton1:

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container,new OutfitGridFragment());
                transaction.commit();

break;
        }



    }}



package com.ucstudios.wardrobe;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
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
import android.view.DragEvent;
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
    int controllodivider;


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
        controllodivider=0;
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

        deleteEmptyOutfit();
        populateOutfits();


        return view;
    }

    public void deleteEmptyOutfit(){
        Cursor data = mDatabaseHelper.getData2();
        final ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()) {
            listData.add(data.getString(0));
            dro.add("0");
        }
        Cursor categories = mDatabaseHelper.getData();
        final ArrayList<String> categories2 = new ArrayList<>();
        while (categories.moveToNext()){
            categories2.add(categories.getString(0));
        }

        for(int u=0;u<listData.size();u++){
            int controllomatto=0;
            for(int i=0;i<categories2.size();i++){
                Cursor c = mDatabaseHelper.GetItemOutfit(categories2.get(i),listData.get(u));

                while(c.moveToNext()){
                    if(c.getString(0)!=null){
                        controllomatto++;
                    }
                }


            }
            if(controllomatto==0){
                mDatabaseHelper.delete3("outfit_table",listData.get(u));
                Toast.makeText(getContext(),"Outfit "+listData.get(u)+" automatically deleted",Toast.LENGTH_SHORT).show();


            }
        }
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
            categories2.add(categories.getString(0));
        }
        mDatabaseHelper.GetNullOutfitName();
        if(controllodivider==0) {
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
            mListview.addItemDecoration(dividerItemDecoration);
            controllodivider = 1;
        }
        final int[] WearabilityControl = new int[listData.size()];
        for(int u=0;u<listData.size();u++){
            int controllomatto=0;
            WearabilityControl[u]=0;
            for(int i=0;i<categories2.size();i++){
                Cursor c = mDatabaseHelper.GetItemOutfit(categories2.get(i),listData.get(u));
                while(c.moveToNext()){
                    Cursor WearabilityCursor = mDatabaseHelper.getData1(categories2.get(i));
                    while(WearabilityCursor.moveToNext()){
                      if(WearabilityCursor.getInt(2)==1||WearabilityCursor.getInt(2)==2||WearabilityCursor.getInt(2)==3){
                          WearabilityControl[u]=1;
                      }
                   }
                    if(c.getString(0)!=null){
                        controllomatto++;
                    }
                }
            }
            if(controllomatto==0){
                mDatabaseHelper.DeleteOutfit(listData.get(u));
                Toast.makeText(getContext(),"Outfit "+listData.get(u)+" automatically deleted",Toast.LENGTH_SHORT).show();

            }
        }

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        final RecyclerAdapterOutfitList adapter = new RecyclerAdapterOutfitList(getContext(), listData, WearabilityControl);
        mListview.setLayoutManager(layoutManager);
        mListview.setAdapter(adapter);

        boolean risultato = longClickListener.onLongClick(mListview);
        ItemTouchHelper.Callback callback =
                new SimpleItemTouchHelperCallback(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(mListview);
        adapter.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = mListview.indexOfChild(v);
                if(WearabilityControl[position]!=0){
                    Toast.makeText(getContext(),"Not Wearable!",Toast.LENGTH_SHORT).show();
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
                        Cursor GetPOS = mDatabaseHelper.getData1(categories2.get(i)); //Ottenere pos per item

                        while(GetPOS.moveToNext()){
                            OutfitPOS.add(GetPOS.getInt(2));
                        }
                        if(c.getString(0)!=null){
                        OutfitComponents.add(c.getString(0));
                        OutfitCategories.add(AllComponents.get(i));
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
               VisualDialogOutfit dialog = new VisualDialogOutfit(getContext(),fumo,OutfitComponents,OutfitCategories,OutfitPOS);
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

    View.OnDragListener dragListener = new View.OnDragListener() {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            int dragEvent = event.getAction(); //aspetta
            switch(dragEvent) {
                case DragEvent.ACTION_DRAG_ENTERED:
                    Log.i("msg","PICCHIASPORHI");
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    Log.i("msg","PICCHIASPORHI");
                    break;
                case DragEvent.ACTION_DROP:
                    Log.i("msg","PICCHIASPORHI");
                    break;
            }
            return true;
        }
    };

   ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(1, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

   @SuppressLint("SetTextI18n")
    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        final int position = viewHolder.getAdapterPosition();

        switch (direction) {

            case ItemTouchHelper.LEFT : {
                Cursor data = mDatabaseHelper.getData2();
                final ArrayList<String> listData = new ArrayList<>();
                final ArrayList<String> spinnarcontrol = new ArrayList<>();
                while (data.moveToNext()) {
                    listData.add(data.getString(0));
                    dro.add("0");
                }
                Cursor categories = mDatabaseHelper.getData();
                final ArrayList<String> categories2 = new ArrayList<>();
                while (categories.moveToNext()) {
                    categories2.add(categories.getString(0));
                }
                final ArrayList<String> OutfitComponents = new ArrayList<>();
                for (int i = 0; i < categories2.size(); i++) {
                    Cursor c = mDatabaseHelper.GetItemOutfit(categories2.get(i), listData.get(position));

                    while (c.moveToNext()) {
                        if (c.getString(0) != null) {
                            OutfitComponents.add(c.getString(0));
                            spinnarcontrol.add("1");

                        } else {
                            spinnarcontrol.add("0");
                        }


                    }


                }
                final ArrayList<Integer> SpinnerValue = new ArrayList<>();
                for (int i = 0; i < categories2.size(); i++) {
                    if (spinnarcontrol.get(i).equals("0")) {
                        SpinnerValue.add(-1);
                    }
                    for (int u = 0; u < OutfitComponents.size(); u++) {
                        Cursor c = mDatabaseHelper.GetSpecificIdItem(categories2.get(i), OutfitComponents.get(u));
                        while (c.moveToNext()) {
                            if (!spinnarcontrol.get(i).equals("0")) {
                                SpinnerValue.add(c.getInt(0));
                            }

                        }

                    }


                }
                Log.i("Ecco componenti", "Ecco" + SpinnerValue);
                Log.i("Ecco Valori", "Ecco" + drog);
                final CustomDialogClass customDialogClass = new CustomDialogClass(getActivity(), spinnarcontrol, 1, SpinnerValue, position);
                customDialogClass.show();
                customDialogClass.setAdapterResult(new CustomDialogClass.OnMyAdapterResult() {
                    @Override
                    public void finish(String result) {

                        if (!result.equals("") && !result.equals("ELIMINAZIONETOTALE")) {
                            Cursor c = mDatabaseHelper.getData2();
                            final ArrayList<String> UniquenessControl = new ArrayList<>();
                            int control = 0;
                            while (c.moveToNext()) {
                                UniquenessControl.add(c.getString(0));
                            }
                            for (int i = 0; i < UniquenessControl.size(); i++) {
                                if (!result.equals(listData.get(position))) {
                                    if (result.equals(UniquenessControl.get(i))) {
                                        control++;
                                    }
                                }
                            }
                            if (control == 0) {
                                mDatabaseHelper.ReplaceOutfit(result, position + 1);
                                mDatabaseHelper.GetNullOutfitName();
                                deleteEmptyOutfit();
                                populateOutfits();
                            } else {
                                Toast.makeText(getContext(), "An Outfit with that name already exists! Change name", Toast.LENGTH_SHORT).show();
                            }


                        } else if (result.equals("ELIMINAZIONETOTALE")) {
                            mDatabaseHelper.delete3("Outfit_Table", listData.get(position));
                            deleteEmptyOutfit();
                            populateOutfits();
                            Log.i("msg", "Eliminazione completata!");
                        }

                    }
                });

                customDialogClass.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        mDatabaseHelper.GetNullOutfitName();
                        deleteEmptyOutfit();
                        populateOutfits();

                    }
                });
            }

                break;

                case ItemTouchHelper.RIGHT:{Cursor data = mDatabaseHelper.getData2();
                    final ArrayList<String> listData = new ArrayList<>();
                    final ArrayList<String> spinnarcontrol = new ArrayList<>();
                    while (data.moveToNext()) {
                        listData.add(data.getString(0));
                        dro.add("0");
                    }
                    Cursor categories = mDatabaseHelper.getData();
                    final ArrayList<String> categories2 = new ArrayList<>();
                    while (categories.moveToNext()){
                        categories2.add(categories.getString(0));
                    }
                    final ArrayList<String> OutfitComponents = new ArrayList<>();
                    for(int i = 0; i<categories2.size();i++){
                        Cursor c = mDatabaseHelper.GetItemOutfit(categories2.get(i),listData.get(position));

                        while(c.moveToNext()){
                            if(c.getString(0)!=null){
                                OutfitComponents.add(c.getString(0));
                                spinnarcontrol.add("1");

                            }
                            else{
                                spinnarcontrol.add("0");
                            }


                        }


                    }
                    final ArrayList<Integer> SpinnerValue = new ArrayList<>();
                    for(int i=0;i<categories2.size();i++){
                        if(spinnarcontrol.get(i).equals("0")){
                            SpinnerValue.add(-1);
                        }
                        for(int u=0;u<OutfitComponents.size();u++){
                            Cursor c = mDatabaseHelper.GetSpecificIdItem(categories2.get(i),OutfitComponents.get(u));
                            while(c.moveToNext()){
                                if(!spinnarcontrol.get(i).equals("0")){
                                    SpinnerValue.add(c.getInt(0));
                                }

                            }

                        }


                    }
                    Log.i("Ecco componenti","Ecco"+SpinnerValue);
                    Log.i("Ecco Valori","Ecco"+drog);
                    final CustomDialogClass customDialogClass = new CustomDialogClass(getActivity(), spinnarcontrol, 1, SpinnerValue,position);
                    customDialogClass.show();
                    customDialogClass.setAdapterResult(new CustomDialogClass.OnMyAdapterResult() {
                        @Override
                        public void finish(String result) {

                            if(!result.equals("")&&!result.equals("ELIMINAZIONETOTALE")){
                                Cursor c = mDatabaseHelper.getData2();
                                final ArrayList<String> UniquenessControl = new ArrayList<>();
                                int control =0;
                                while(c.moveToNext()){
                                    UniquenessControl.add(c.getString(0));
                                }
                                for(int i=0;i<UniquenessControl.size();i++){
                                    if(!result.equals(listData.get(position))){
                                        if(result.equals(UniquenessControl.get(i))){
                                            control++;
                                        }
                                    }
                                }
                                if(control==0){
                                    mDatabaseHelper.ReplaceOutfit(result,position+1);
                                    mDatabaseHelper.GetNullOutfitName();
                                    deleteEmptyOutfit();
                                    populateOutfits();}
                                else{
                                    Toast.makeText(getContext(),"An Outfit with that name already exists! Change name", Toast.LENGTH_SHORT).show();
                                }


                            }
                            else if(result.equals("ELIMINAZIONETOTALE")){
                                mDatabaseHelper.delete3("Outfit_Table",listData.get(position));
                                deleteEmptyOutfit();
                                populateOutfits();
                                Log.i("msg","Eliminazione completata!");
                            }

                        }
                    });

                    customDialogClass.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            mDatabaseHelper.GetNullOutfitName();
                            deleteEmptyOutfit();
                            populateOutfits();

                        }
                    });


                    break;

                }


        }
    }



    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                .addSwipeLeftBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorAccent))
                .addSwipeLeftActionIcon(R.drawable.ic_editimsto)
                .addSwipeRightBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorAccent))
                .addSwipeRightActionIcon(R.drawable.ic_editimsto)
                .create()
                .decorate();
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }
};



    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.floating_action_button:
                mDatabaseHelper = new DatabaseHelper(getContext());
                Cursor C = mDatabaseHelper.getData();
                int controlloiniziale=0;
                while (C.moveToNext()){
                    controlloiniziale++;
                }
                if(controlloiniziale==0){
                    new AlertDialog.Builder(getContext())
                            .setTitle("Whoops!")
                            .setMessage("You must first create a category")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();
                }
                else{
                ArrayList<Integer> cocco = new ArrayList<>();
                CustomDialogClass cdd = new CustomDialogClass(getActivity(),dro,0,cocco,0);
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
                       if (!result.equals("mattototale")){
                           Cursor c = mDatabaseHelper.getData2();
                           int control=0;
                           final ArrayList<String> UniquenessControl = new ArrayList<>();
                           while(c.moveToNext()){
                               UniquenessControl.add(c.getString(0));
                               Log.i("msg","ecco"+UniquenessControl);
                           }
                           for(int i=0;i<UniquenessControl.size();i++){
                               if(result.equals(UniquenessControl.get(i))){
                                   control++;
                               }
                           }
                           if(control==0) {
                               AddOutfit(result);

                               populateOutfits();
                           }
                           else{
                               Toast.makeText(getContext(),"Outfit with this name already exists!",Toast.LENGTH_SHORT).show();
                           }
                       }

                    }
                });
               }
                break;

            case R.id.gridbutton1:

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container,new OutfitGridFragment());
                transaction.commit();

break;
        }



    }}



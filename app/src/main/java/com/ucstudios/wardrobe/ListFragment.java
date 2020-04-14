package com.ucstudios.wardrobe;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
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
//RISOLVERE PROBLEMA POSITION SWAPS
import java.util.ArrayList;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment implements View.OnClickListener{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    DatabaseHelper mDatabaseHelper1;
    MainActivity mMainActivity;
    private RecyclerView mRecyclerView;
    public ArrayList<String> canecazzo = new ArrayList<>();
    FloatingActionButton floatingActionButton1;
    int tac;
    byte[] alien2o;
    static final int REQUEST_IMAGE_CAPTURE=1;
    int magianera;
    final ArrayList<byte[]> tech = new ArrayList<>();
    final ArrayList<byte[]> tecca = new ArrayList<>();
    Button gridbutton;
    ArrayList<String> ItemsInBasket = new ArrayList<>();
    int controllodivider;





    RecyclerAdapterItems recyclerAdapter;


    public ListFragment() {

    }


    public static ListFragment newInstance(String param1) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        floatingActionButton1 = view.findViewById(R.id.floating_action_button2);
        floatingActionButton1.setOnClickListener(this);

        mDatabaseHelper1 = new DatabaseHelper(getActivity());
        gridbutton = view.findViewById(R.id.buttongrid);
        gridbutton.setOnClickListener(this);
        mRecyclerView = view.findViewById(R.id.spezzaossa2);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
        mMainActivity = (MainActivity) getActivity();
        TextView mTextView = view.findViewById(R.id.textView);
        mTextView.setText(mMainActivity.Name);
        mTextView.setTypeface(mTextView.getTypeface(), Typeface.BOLD);
        mDatabaseHelper1.getData();
        controllodivider=0;

        populateItems();



        return view;
    }



    public void AddData1(String name, String size, String brand, Integer value, Integer currency, Integer icon, byte[] alien2o) {
        boolean insertData = mDatabaseHelper1.addData1(mMainActivity.Name, name,size,icon,brand,value,currency,alien2o);
        toastMessage("New Item Created!");

    }

    public void GetItemData(int position, String tablename){
        mDatabaseHelper1.GetItemData(position,tablename);
    }

    private void toastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

    }

    public  void Replace(String Table, String item,String size,String brand,Integer value,Integer currency,Integer icon,byte[] alien2o, int i){
        mDatabaseHelper1.ReplaceItem(Table, item, size, brand,value,currency,icon,alien2o,i);


    }

    public void Delete(String MainActName, String item){
        mDatabaseHelper1.delete2(MainActName, item);
    }


    private void populateItems() {

        final Cursor data = mDatabaseHelper1.getData1(mMainActivity.Name);
        final ArrayList<String> listData = new ArrayList<>();
        final ArrayList<Integer> position = new ArrayList<>();
        final ArrayList<Integer> icons = new ArrayList<>();

        while (data.moveToNext()) {
            listData.add(data.getString(1));
            canecazzo.add(data.getString(1));
            position.add(data.getInt(3));
            icons.add(data.getInt(2));
            tech.add(data.getBlob(8));
        }

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerAdapter = new RecyclerAdapterItems(getContext(),position,listData,icons,tech,mMainActivity.Name);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(recyclerAdapter);
        if(controllodivider==0){
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        controllodivider=1;
        }
        boolean risultato = longClickListener.onLongClick(mRecyclerView);
        ItemTouchHelper.Callback callback =
                new SimpleItemTouchHelperCallback(recyclerAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(mRecyclerView);
        recyclerAdapter.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = mRecyclerView.indexOfChild(v);
                final Cursor magiabianca = mDatabaseHelper1.GetItemData(pos+1,mMainActivity.Name);
                final ArrayList<String> itemdata2 = new ArrayList<>();
                final ArrayList<byte[]> negromatto = new ArrayList<>();
                while (magiabianca.moveToNext()){
                    itemdata2.add(magiabianca.getString(1));
                    itemdata2.add(magiabianca.getString(5));
                    itemdata2.add(String.valueOf(magiabianca.getInt(6)));
                    negromatto.add(magiabianca.getBlob(8));
                }
              final VisualDialogItemDialogTrue  dialogItemDialogTrue = new VisualDialogItemDialogTrue(getActivity(),itemdata2,negromatto);
                dialogItemDialogTrue.show();
                dialogItemDialogTrue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                Log.i("msg","Traffica"+itemdata2.get(1));
            }
        });
        //dragListener.onDrag(recyclerView, risultato);


    }





    View.OnLongClickListener longClickListener = new View.OnLongClickListener() {

        @Override
        public boolean onLongClick(View v) {


            ClipData merda = ClipData.newPlainText("", "");
            View.DragShadowBuilder myShadowBuilder = new View.DragShadowBuilder(v);
            v.startDrag(merda, myShadowBuilder, v, 0);
            v.setOnDragListener(new View.OnDragListener() {
                @Override
                public boolean onDrag(View v, DragEvent event) {
                    Log.i("msg","PICCHIASPORHI");
                    return false;
                }
            });
            return true;
        }
    };

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


    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }


        @SuppressLint("SetTextI18n")
        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            final int position = viewHolder.getAdapterPosition();
            tac = position;

            switch (direction) {
                case ItemTouchHelper.RIGHT:
                    mDatabaseHelper1.toBasket(mMainActivity.Name, position+1);
                    populateItems();
                    Snackbar.make(mRecyclerView, mMainActivity.Name, Snackbar.LENGTH_LONG).setAction("Rimetti item nel wardrobe", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mDatabaseHelper1.toWardrobemodif(mMainActivity.Name, position+1);
                                    populateItems();
                                }
                            }).show();
            break;




                case ItemTouchHelper.LEFT:
                    final Cursor C = mDatabaseHelper1.GetItemData(position+1,mMainActivity.Name);
                    final ArrayList<String> itemdata = new ArrayList<>();


                    while (C.moveToNext()){
                        itemdata.add(C.getString(1));
                        itemdata.add(C.getString(4));
                        itemdata.add(C.getString(5));
                        itemdata.add(String.valueOf(C.getInt(6)));
                        tecca.add(C.getBlob(8));

                    }
                    final ItemVisualDialog dialog = new ItemVisualDialog(getActivity(),1,itemdata,tecca,mMainActivity.Name);
                    dialog.show();
                    dialog.CameraActivation(new ItemVisualDialog.CameraActivation() {
                        @Override
                        public void activation(int a) {
                            if (a==2){

                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                if(intent.resolveActivity(getActivity().getPackageManager())!=null) {
                                    startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                                }
                            }
                        }
                    });
                    dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            populateItems();
                        }
                    });
                    dialog.ItemCreation(new ItemVisualDialog.ItemCreatedInterface() {
                        @Override
                        public void finish(String name, String size, String brand, Integer value, Integer currency, Integer icon, String olditemname) {
                            if(alien2o!=null){
                                Replace(mMainActivity.Name,name,size,brand,value,currency,icon,alien2o,position+1);}
                            else{
                                mDatabaseHelper1.ReplaceItemnoPic(mMainActivity.Name,name,size,brand,value,currency,icon,position+1);
                            }
                            mDatabaseHelper1.ReplaceIteminOutfitTable(name,mMainActivity.Name,olditemname);
                            dialog.dismiss();
                            populateItems();
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
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(getContext(),R.color.colorPrimary))
                    .addSwipeRightActionIcon(R.drawable.ic_basket24)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };













            @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.floating_action_button2:

                final ArrayList<String> crack = new ArrayList<>();
                final ArrayList<byte[]> technon = new ArrayList<>();
                final ItemVisualDialog dialog = new ItemVisualDialog(getActivity(),0,crack,technon,mMainActivity.Name);

                dialog.show();
                dialog.CameraActivation(new ItemVisualDialog.CameraActivation() {
                    @Override
                    public void activation(int a) {
                        if (a==1){

                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            if(intent.resolveActivity(getActivity().getPackageManager())!=null) {

                                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                            }
                        }
                    }
                });
                dialog.ItemCreation(new ItemVisualDialog.ItemCreatedInterface() {


                    @Override
                    public void finish(String name, String size, String brand, Integer value, Integer currency, Integer icon, String olditemname) {
                        if(magianera==1){
                            AddData1(name,size,brand,value,currency,icon,alien2o);
                            mDatabaseHelper1.AddPictureItem(mMainActivity.Name,alien2o,tac);
                            dialog.dismiss();
                            populateItems();}
                        else{
                            Toast.makeText(getContext(),"Pic missing!",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;




            case R.id.buttongrid:
                Log.i("msg","cliccato");

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container,new GridViewListFragment());
                transaction.commit();

                break;
        }


                    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode,resultCode,data);
            if(requestCode==REQUEST_IMAGE_CAPTURE && resultCode==-1) {
                Bundle extras = data.getExtras();
                Bitmap photo = (Bitmap) extras.get("data");
                alien2o = Utils.getBytes(photo);
                magianera = 1;

            }






            }




}







package com.ucstudios.wardrobe;


import android.app.AlertDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

//CESTO;

public class NotificationFragment extends Fragment implements View.OnClickListener {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    DatabaseHelper mDatabaseHelper2;
    private String mParam1;
    private String mParam2;
    MainActivity mMainActivity;
    ArrayList<String> TotalCategories = new ArrayList<>();
    ArrayList<String> ItemsInBasket = new ArrayList<>();

    public NotificationFragment() {
        // Required empty public constructor
    }

    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;




    public static NotificationFragment newInstance(String param1, String param2) {
        NotificationFragment fragment = new NotificationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setHasOptionsMenu(true);



    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
            FloatingActionButton floatingActionButton = view.findViewById(R.id.floatingActionButton8);
                floatingActionButton.setOnClickListener(this);
        recyclerView = view.findViewById(R.id.gThunbergView2);
        mDatabaseHelper2 = new DatabaseHelper(getActivity());
        mMainActivity = (MainActivity) getActivity();

        populateBasket();

                    return view;
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

    private void populateBasket(){

        final Cursor data1 = mDatabaseHelper2.getData();
        final ArrayList<String> categories = new ArrayList<>();


        while(data1.moveToNext()){
            categories.add(data1.getString(0));
            TotalCategories.add(data1.getString(0));
        }


        final ArrayList<String> listData = new ArrayList<>();

        for(int i=0; i<categories.size();i++) {
            Cursor data = mDatabaseHelper2.GetBasket(categories.get(i));
            while(data.moveToNext()){
                listData.add(data.getString(0));
               ItemsInBasket.add(data.getString(0));
            }
        }


        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        recyclerAdapter = new RecyclerAdapter(listData);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        boolean risultato = longClickListener.onLongClick(recyclerView);
        ItemTouchHelper.Callback callback =
                new SimpleItemTouchHelperCallback(recyclerAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);



    }

    View.OnDragListener dragListener = new View.OnDragListener() {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            int dragEvent = event.getAction();
            switch(dragEvent) {
                case DragEvent.ACTION_DRAG_ENTERED:
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DROP:
                    break;
            }
            return true;
        }
    };

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.floatingActionButton8:

                assert getFragmentManager() != null;
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container, new LaundryFragment());
                transaction.commit();

            break;
        }

    }


    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }


        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            final int position = viewHolder.getAdapterPosition();

            switch (direction) {
                case ItemTouchHelper.RIGHT:
                    ArrayList<String> dacestoalavatrice = new ArrayList<>();
                    for(int i=0;i<TotalCategories.size();i++){
                       Cursor c = mDatabaseHelper2.GetBasketSpecific(TotalCategories.get(i),ItemsInBasket.get(position));
                       while(c.moveToNext()){
                           dacestoalavatrice.add(c.getString(0));
                           Log.i("msg :","Swiped "+ dacestoalavatrice.get(0));
                       }
                    }

                    for(int is=0;is<TotalCategories.size();is++){
                        //get the table name
                        mDatabaseHelper2.toLaundry(TotalCategories.get(is),dacestoalavatrice.get(0));
                        Log.i("msg","Passaggio a Lavatrice completato per "+dacestoalavatrice.get(0));
                    }
                    populateBasket();

                    Snackbar.make(recyclerView, (CharSequence) mDatabaseHelper2, Snackbar.LENGTH_LONG).setAction("Rimetti item nel cesto", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ArrayList<String> dacestoalavatrices = new ArrayList<>();
                            for(int i=0;i<TotalCategories.size();i++){
                                Cursor c = mDatabaseHelper2.GetWMSpecific(TotalCategories.get(i),ItemsInBasket.get(position));
                                while(c.moveToNext()){
                                    dacestoalavatrices.add(c.getString(0));
                                    Log.i("msg :","Swiped "+ dacestoalavatrices.get(0));
                                }
                            }

                            for(int is=0;is<TotalCategories.size();is++){
                                //get the table name
                                mDatabaseHelper2.toBasketmodif(TotalCategories.get(is),dacestoalavatrices.get(0));
                                Log.i("msg","Passaggio a Lavatrice completato per "+dacestoalavatrices.get(0));
                            }
                            populateBasket();
                        }
                    }).show();
                    break;

                case ItemTouchHelper.LEFT:
                    ArrayList<String> dacestoalavatrices = new ArrayList<>();
                    for(int i=0;i<TotalCategories.size();i++){
                        Cursor c = mDatabaseHelper2.GetBasketSpecific(TotalCategories.get(i),ItemsInBasket.get(position));
                        while(c.moveToNext()){
                            dacestoalavatrices.add(c.getString(0));
                            Log.i("msg :","Swiped "+ dacestoalavatrices.get(0));
                        }
                    }

                    for(int is=0;is<TotalCategories.size();is++){
                        //get the table name
                        mDatabaseHelper2.toWardrobe(TotalCategories.get(is),dacestoalavatrices.get(0));
                        Log.i("msg","Passaggio a Lavatrice completato per "+dacestoalavatrices.get(0));
                    }
                    populateBasket();
                    Snackbar.make(recyclerView, (CharSequence) mDatabaseHelper2, Snackbar.LENGTH_LONG).setAction("Rimetti item nel cesto", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ArrayList<String> dacestoalavatrices = new ArrayList<>();
                            for(int i=0;i<TotalCategories.size();i++){
                                Cursor c = mDatabaseHelper2.GetWardrobeSpecific(TotalCategories.get(i),ItemsInBasket.get(position));
                                while(c.moveToNext()){
                                    dacestoalavatrices.add(c.getString(0));
                                    Log.i("msg :","Swiped "+ dacestoalavatrices.get(0));
                                }
                            }

                            for(int is=0;is<TotalCategories.size();is++){
                                //get the table name
                                mDatabaseHelper2.toBasketmodif(TotalCategories.get(is),dacestoalavatrices.get(0));
                                Log.i("msg","Passaggio a Lavatrice completato per "+dacestoalavatrices.get(0));
                            }
                            populateBasket();
                        }
                    }).show();
                    break;
            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(getContext(), R.color.bluetattico))
                    .addSwipeRightActionIcon(R.drawable.ic_wm24)
                    .create()
                    .decorate();
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(getContext(),R.color.verdetattico))
                    .addSwipeLeftActionIcon(R.drawable.ic_hanger)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

}
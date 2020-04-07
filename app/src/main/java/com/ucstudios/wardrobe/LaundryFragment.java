package com.ucstudios.wardrobe;


import android.app.TimePickerDialog;
import android.content.ClipData;
import android.database.Cursor;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;


//LAVATRICE

public class LaundryFragment extends Fragment implements TimePickerDialog.OnTimeSetListener, View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters

    private String mParam1;
    private String mParam2;
    DatabaseHelper mDatabaseHelper;
    ArrayList<String> TotalCategories = new ArrayList<>();
    ArrayList<String> ItemsInBasket = new ArrayList<>();
    public LaundryFragment() {
        // Required empty public constructor
    }

    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    List<String> itemsLaundry = new ArrayList<>();

    public static LaundryFragment newInstance(String param1, String param2) {
        LaundryFragment fragment = new LaundryFragment();
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
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lavatrice, container, false);
        FloatingActionButton floatingActionButton = view.findViewById(R.id.floatingActionButton8);
        floatingActionButton.setOnClickListener(this);
        recyclerView = view.findViewById(R.id.gThunbergView3);
        mDatabaseHelper = new DatabaseHelper(getActivity());
        populateWM();
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



    public void populateWM(){
        final Cursor data1 = mDatabaseHelper.getData();
        final ArrayList<String> categories = new ArrayList<>();


        while(data1.moveToNext()){
            categories.add(data1.getString(1));
            TotalCategories.add(data1.getString(1));

        }


        final ArrayList<String> listData = new ArrayList<>();

        for(int i=0; i<categories.size();i++) {
            Cursor data = mDatabaseHelper.GetWM(categories.get(i));
            while(data.moveToNext()){
                listData.add(data.getString(1));
                ItemsInBasket.add(data.getString(1));
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
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

    }

    @Override
    public void onClick(View v) {
        DialogFragment timePicker = new TimePickerFragment();
        timePicker.show(getFragmentManager(), "time picker");
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
                        Cursor c = mDatabaseHelper.GetWMSpecific(TotalCategories.get(i),ItemsInBasket.get(position));
                        while(c.moveToNext()){
                            dacestoalavatrice.add(c.getString(1));
                            Log.i("msg :","Swiped "+ dacestoalavatrice.get(0));
                        }
                    }

                    for(int is=0;is<TotalCategories.size();is++){
                        //get the table name
                        mDatabaseHelper.toWardrobe(TotalCategories.get(is),dacestoalavatrice.get(0));
                        Log.i("msg","Passaggio a Lavatrice completato per "+dacestoalavatrice.get(0));
                    }
                    populateWM();
                    break;
                case ItemTouchHelper.LEFT:
                    ArrayList<String> dacestoalavatrices = new ArrayList<>();
                    for(int i=0;i<TotalCategories.size();i++){
                        Cursor c = mDatabaseHelper.GetWMSpecific(TotalCategories.get(i),ItemsInBasket.get(position));
                        while(c.moveToNext()){
                            dacestoalavatrices.add(c.getString(1));
                            Log.i("msg :","Swiped "+ dacestoalavatrices.get(0));
                        }
                    }

                    for(int is=0;is<TotalCategories.size();is++){
                        //get the table name
                        mDatabaseHelper.toBasketmodif(TotalCategories.get(is),dacestoalavatrices.get(0));
                        Log.i("msg","Passaggio a Lavatrice completato per "+dacestoalavatrices.get(0));
                    }
                    populateWM();
                    break;
            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorAccent))
                    .addSwipeRightActionIcon(R.drawable.ic_chevron_right_black_24dp)
                    .create()
                    .decorate();
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(getContext(),R.color.colorPrimary))
                    .addSwipeLeftActionIcon(R.drawable.ic_chevron_right_black_24dp)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };


}


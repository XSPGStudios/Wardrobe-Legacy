package com.ucstudios.wardrobe;


import android.content.ClipData;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

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
        mDatabaseHelper = new DatabaseHelper(getActivity());
        mListview = view.findViewById(R.id.spezzaossa4);

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
        }
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        RecyclerAdapterOutfitList adapter = new RecyclerAdapterOutfitList(getContext(), listData);
        mListview.setLayoutManager(layoutManager);
        mListview.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
        mListview.addItemDecoration(dividerItemDecoration);
        boolean risultato = longClickListener.onLongClick(mListview);
        ItemTouchHelper.Callback callback =
                new SimpleItemTouchHelperCallback(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(mListview);
        adapter.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //VisualDialogOutfit dialog = new VisualDialogOutfit(getContext(),//pinzare immagine per nome outfit,//pinzare posizione click)

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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.floating_action_button:

                CustomDialogClass cdd = new CustomDialogClass(getActivity());
                cdd.show();
                cdd.setAdapterResult(new CustomDialogClass.OnMyAdapterResult() {
                    @Override
                    public void finish(String result) {
                        AddOutfit(result);
                        populateOutfits();
                    }
                });
                break;

        }


    }}



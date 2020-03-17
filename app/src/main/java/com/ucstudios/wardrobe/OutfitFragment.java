package com.ucstudios.wardrobe;


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
    ListView mListview;
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
        ListAdapter adapter = new ArrayAdapter<>(mListview.getContext(), android.R.layout.simple_list_item_1, listData);
        mListview.setAdapter(adapter);
    }

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



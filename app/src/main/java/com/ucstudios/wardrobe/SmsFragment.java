package com.ucstudios.wardrobe;

import android.app.AlertDialog;
import android.content.DialogInterface;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;


public class SmsFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ListView mRecyclerView;
    public String pennsylvania=null;

    DatabaseHelper mDatabaseHelper;
    MainActivity mMainActivity;

    private String mParam1;
    private String mParam2;


    public SmsFragment() {

    }


    public static SmsFragment newInstance(String param1, String param2) {
        SmsFragment fragment = new SmsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);


        fragment.setArguments(args);


        return fragment;


    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_sms, container, false);
        FloatingActionButton ActionButton = view.findViewById(R.id.floating_action_button);
        mRecyclerView = view.findViewById(R.id.spezzaossa4);
        ActionButton.setOnClickListener(this);
        mDatabaseHelper = new DatabaseHelper(getActivity());
        mMainActivity = (MainActivity) getActivity();
        Spinner spinner = view.findViewById(R.id.spinner_nav);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               if(position==1){ FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container, new OutfitFragment());
                transaction.commit();}
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getActivity(),
                R.array.options, android.R.layout.simple_spinner_item);

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter1);


        populateButtons();


        return view;


    }

    public void AddData(String newEntry) {
        boolean insertData = mDatabaseHelper.addData(newEntry);
        toastMessage("New Category Created!");
    }


    private void toastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

    }

    public void Replace(String table,String item, int i){
        mDatabaseHelper.ReplaceItem4(table, item, i);
    }

    public void Delete(String MainActName, String item){
        mDatabaseHelper.delete3(MainActName, item);
    }

    public void TableRemover(String table){
        mDatabaseHelper.TABLEDROP(table);
    }

    public void OutfitColumnRemover(String columnsremaining,String pietrone){
        mDatabaseHelper.OutfitColumnDrop(columnsremaining,pietrone);
    }
    public void OutfitColumnNameChaneger(String matto, String totale){
        mDatabaseHelper.OutfitChangeColumnName(matto, totale);
    }

    public void TableRenamer(String tablename,String coto){
        mDatabaseHelper.TableRenamer(tablename, coto);
    }



    private void populateButtons() {
        Cursor data = mDatabaseHelper.getData();
        final ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()) {
            listData.add(data.getString(1));
        }
        mMainActivity.Categories=listData;
        final AdapterCategories adapterCategories = new AdapterCategories(mRecyclerView.getContext(), R.layout.adapter_categories, listData);
        mRecyclerView.setAdapter(adapterCategories);
        mRecyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int pirla = (int) id;
                String lecca = listData.get(pirla);
                mMainActivity.Name = lecca;
                Log.v("message", "List Item " + id + " Click");
                Log.v("asd", mMainActivity.Name+" selected");

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container, new ListFragment());
                transaction.commit();
            }

        });
        mRecyclerView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                adapterCategories.setAdapterResult(new AdapterCategories.onMyAdapterResult1(){
                    @Override
                    public void finish(String result) {
                        String cocco = String.valueOf(result);
                        String ketamina ="ID INTEGER PRIMARY KEY AUTOINCREMENT, name  TEXT";
                        String sugone = "ID, name";
                        if (cocco.equals("CANE")) {
                            pennsylvania = listData.get(position);

                            for(int i=0;i<listData.size();i++) { //MAGIC FIRST FOR LOOP
                                String minipera = listData.get(i);
                                if (!minipera.equals(pennsylvania)) {

                                    ketamina = ketamina + "," + listData.get(i) + " TEXT";
                                    sugone = sugone + "," + listData.get(i) + "";

                                }
                            }

                                OutfitColumnRemover(ketamina, sugone);


                            Toast.makeText(mMainActivity, listData.get(position)+" Deleted!", Toast.LENGTH_SHORT).show();
                            Delete("categories_table", listData.get(position));
                            TableRemover(listData.get(position));
                            Log.i("msg", "Table "+ listData.get(position)+" removed");
                            populateButtons();

                        }
                        else if(cocco=="culocane"){
                            Log.i("msg","pic changed!");
                        }


                        else if (!cocco.equals("")&!cocco.equals("CANE")&!cocco.equals("culocane")){

                            String ketamina2="ID INTEGER PRIMARY KEY AUTOINCREMENT, name  TEXT";
                            String sugone2="ID ,name";
                            int peso = position;


                            for(int i=0;i<listData.size();i++) {

                                sugone2 = sugone2 + "," + listData.get(i) + "";
                                if (i!=peso) {

                                    ketamina2 = ketamina2 + "," + listData.get(i) + " TEXT";


                                }else{
                                    ketamina2=ketamina2+", "+result+" TEXT";

                                }
                            }


                            OutfitColumnNameChaneger(ketamina2,sugone2);
                            Replace("categories_table", result, position + 1);
                            TableRenamer(listData.get(position),result);
                            Log.i("msg", "Modified "+listData.get(position)+" to "+ result);

                        }
                    }

                });


                return true;
            }
        });


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.floating_action_button:


                final String[] m_Text = {""};
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Title");
                final EditText input = new EditText(getActivity());
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {


                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text[0] = input.getText().toString();
                        String duke = Arrays.toString(m_Text).replace("[", "").replace("]", "");
                        mDatabaseHelper.PINZA = duke;

                        AddData(mDatabaseHelper.PINZA);
                        populateButtons();


                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
                break;

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}





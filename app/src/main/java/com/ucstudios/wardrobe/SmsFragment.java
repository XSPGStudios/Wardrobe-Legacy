package com.ucstudios.wardrobe;

import android.app.AlertDialog;
import android.content.DialogInterface;

import android.database.Cursor;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SmsFragment extends Fragment implements View.OnClickListener{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ListView mRecyclerView;

    DatabaseHelper mDatabaseHelper;

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
        mRecyclerView = view.findViewById(R.id.spezzaossa);
        ActionButton.setOnClickListener(this);
        mDatabaseHelper = new DatabaseHelper(getActivity());
        populateButtons();


        return view;



    }

    public void AddData(String newEntry){
        boolean insertData = mDatabaseHelper.addData(newEntry);
        if (insertData){
            toastMessage("New Button Created!");
        } else {
            toastMessage("Something went wrong");
        }
        }


    private void toastMessage (String message){
        Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();

    }

    private void populateButtons(){
        Cursor data = mDatabaseHelper.getData();
        ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()){
            listData.add(data.getString(1));
        }

        ListAdapter adapter = new ArrayAdapter<>(mRecyclerView.getContext(), android.R.layout.simple_list_item_1, listData);
        mRecyclerView.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.floating_action_button:



                final String[] m_Text = {""};
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Title");
                final EditText input = new EditText(getActivity());
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
                builder.setView(input);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {


                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text[0] = input.getText().toString();
                        String duke = Arrays.toString(m_Text).replace("[", "").replace("]", "");
                        AddData(duke);
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

        }}
}





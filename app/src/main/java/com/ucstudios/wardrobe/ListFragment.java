package com.ucstudios.wardrobe;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    DatabaseHelper mDatabaseHelper1;
    MainActivity mMainActivity;
    Button deleteButton;
    private ListView mListView;

    public ListFragment() {

    }


    public static ListFragment newInstance(String param1, String param2) {
        ListFragment fragment = new ListFragment();
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
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        FloatingActionButton floatingActionButton1 = view.findViewById(R.id.floating_action_button2);
        deleteButton = view.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(this);
        floatingActionButton1.setOnClickListener(this);
        mDatabaseHelper1 = new DatabaseHelper(getActivity());
        mListView = view.findViewById(R.id.spezzaossa2);
        mMainActivity = (MainActivity) getActivity();
        TextView mTextView = view.findViewById(R.id.textView);
        mTextView.setText(mMainActivity.Name);
        mTextView.setTypeface(mTextView.getTypeface(), Typeface.BOLD);

        populateItems();


        return view;
    }



    public void AddData1(String newEntry) {
        boolean insertData = mDatabaseHelper1.addData1(mMainActivity.Name, newEntry);
        toastMessage("New Item Created!");

    }

    private void toastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

    }


    private void populateItems() {

        Cursor data = mDatabaseHelper1.getData1(mMainActivity.Name);
        final ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()) {
            listData.add(data.getString(1));
        }

        final ListAdapter adapter = new ArrayAdapter<>(mListView.getContext(), android.R.layout.simple_list_item_1, listData);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()

    {
        @Override
        public void onItemClick (AdapterView < ? > parent, View view,int position, final long id){

            final int sex = (int) id;
        final String na = listData.get(sex);
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
                Replace(mMainActivity.Name, Arrays.toString(m_Text).replace("[", "").replace("]", ""), sex);


            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();


        Log.v("message", "List Item " + id + " Click");
    }
    });

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mListView.setOnItemClickListener(null);
                deleteButton.setVisibility(View.VISIBLE);


                return false;

            }

        });


}



    public void Replace(String sex, String porn, int id){
        mDatabaseHelper1.ReplaceItem(sex,porn,id+1);
        populateItems();
    }



            @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.floating_action_button2:

                final String[] m_Text1 = {""};
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Item");
                final EditText input = new EditText(getActivity());
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
                builder.setView(input);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {


                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        m_Text1[0] = input.getText().toString();
                        String duke = Arrays.toString(m_Text1).replace("[", "").replace("]", "");
                        AddData1(duke);
                        populateItems();

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

            case R.id.deleteButton:
                toastMessage("Item deleted!");
                deleteButton.setVisibility(View.GONE);

        }


            }


}

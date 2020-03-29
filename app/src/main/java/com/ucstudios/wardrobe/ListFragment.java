package com.ucstudios.wardrobe;


import android.app.AlertDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

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
        FloatingActionButton floatingActionButton1 = view.findViewById(R.id.floating_action_button2);
        floatingActionButton1.setOnClickListener(this);
        mDatabaseHelper1 = new DatabaseHelper(getActivity());
        mRecyclerView = view.findViewById(R.id.spezzaossa2);

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

    public  void Replace(String Table, String item, int i){
        mDatabaseHelper1.ReplaceItem(Table, item, i);


    }

    public void Delete(String MainActName, String item){
        mDatabaseHelper1.delete2(MainActName, item);
    }


    private void populateItems() {

        Cursor data = mDatabaseHelper1.getData1(mMainActivity.Name);
        final ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()) {
            listData.add(data.getString(1));
        }

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerAdapter = new RecyclerAdapterItems(getContext(),listData);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(recyclerAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        boolean risultato = longClickListener.onLongClick(mRecyclerView);
        ItemTouchHelper.Callback callback =
                new SimpleItemTouchHelperCallback(recyclerAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(mRecyclerView);
        //dragListener.onDrag(recyclerView, risultato);


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

    View.OnDragListener dragListener = new View.OnDragListener() {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            int dragEvent = event.getAction(); //aspetta
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
      /*  mRecyclerView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                adapter.setAdapterResult(new AdapterListView.onMyAdapterResult() {
                    @Override
                    public void finish(String result) {
                        String cocco = String.valueOf(result);
                        if (cocco.equals("CANE")) {
                            Toast.makeText(mMainActivity, listData.get(position)+" Deleted", Toast.LENGTH_SHORT).show();
                            Delete(mMainActivity.Name, listData.get(position));
                            populateItems();

                        } else {
                            Replace(mMainActivity.Name, result, position + 1);
                        }
                    }

                });
                return true;
            }
        });*/











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

        }


            }


    }


package com.ucstudios.wardrobe;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class GridViewListFragment extends Fragment implements View.OnClickListener{

    Button buttonlist;
    FloatingActionButton floatingActionButton;
    GridView mGridView;
    MainActivity mMainActivity;
    DatabaseHelper mDatabaseHelper32;

    public GridViewListFragment(){

    }

    public GridViewListFragment newInstance(){
        GridViewListFragment fragment = new GridViewListFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_list_gridview,container,false);
        buttonlist = view.findViewById(R.id.buttonlist);
        mDatabaseHelper32 = new DatabaseHelper(getActivity());
        floatingActionButton = view.findViewById(R.id.floating_action_button_grid);
        mGridView = view.findViewById(R.id.gridView134);
        mMainActivity = (MainActivity) getActivity();
        TextView mTextView = view.findViewById(R.id.textViewGrid);
        mTextView.setText(mMainActivity.Name);
        mTextView.setTypeface(mTextView.getTypeface(), Typeface.BOLD);
        buttonlist.setOnClickListener(this);
        populateGridItems();
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Cursor magiabianca = mDatabaseHelper32.GetItemData(position+1,mMainActivity.Name);
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
                Log.i("msg","Traffica"+itemdata2.get(1));
            }
            });



        return view;
    }

    private void populateGridItems(){
        final Cursor C = mDatabaseHelper32.getData1(mMainActivity.Name);
        final ArrayList<byte[]> magic = new ArrayList<>();
        final ArrayList<String> tactic = new ArrayList<>();
        while(C.moveToNext()){
            magic.add(C.getBlob(7));
            tactic.add(C.getString(0));
        }
        AdapterGridList adapterGridList = new AdapterGridList(mGridView.getContext(),magic,tactic);
        mGridView.setAdapter(adapterGridList);
    }



    @Override
    public void onClick(View v) {
    switch(v.getId()){
        case R.id.buttonlist:
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.container,new ListFragment());
            transaction.commit();
            break;


        }
    }
}

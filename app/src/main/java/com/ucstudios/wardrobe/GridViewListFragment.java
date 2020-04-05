package com.ucstudios.wardrobe;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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



        return view;
    }

    private void populateGridItems(){
        final Cursor C = mDatabaseHelper32.getData1(mMainActivity.Name);
        final ArrayList<byte[]> magic = new ArrayList<>();
        final ArrayList<String> tactic = new ArrayList<>();
        while(C.moveToNext()){
            magic.add(C.getBlob(8));
            tactic.add(C.getString(1));
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

package com.ucstudios.wardrobe;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class GridViewListFragment extends Fragment implements View.OnClickListener{

    Button buttonlist;

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
        buttonlist.setOnClickListener(this);


        return view;
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

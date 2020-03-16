package com.ucstudios.wardrobe;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class NotificationFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    DatabaseHelper mDatabaseHelper2;
    private String mParam1;
    private String mParam2;
    private ListView listView;
    public NotificationFragment() {
        // Required empty public constructor
    }




    public static NotificationFragment newInstance(String param1, String param2) {
        NotificationFragment fragment = new NotificationFragment();
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
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        DatabaseHelper mDatabaseHelper2 = new DatabaseHelper(getActivity());
        FloatingActionButton floatingActionButton = view.findViewById(R.id.floatingActionButton8);
        floatingActionButton.setOnClickListener(this);
       listView = view.findViewById(R.id.spezzaossa2);
        populateButtons();
        return view;
    }
    private void populateButtons() {
        final ArrayList<String> listData = new ArrayList<String>();
            listData.add("porcodiscord");
                ListAdapter adapter = new ArrayAdapter<>(listView.getContext(), android.R.layout.simple_list_item_1, listData);
                    listView.setAdapter(adapter);
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.floatingActionButton8:

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container, new LaundryFragment());
                transaction.commit();

            break;
        }

    }
}
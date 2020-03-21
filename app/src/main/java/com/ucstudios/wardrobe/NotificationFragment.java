package com.ucstudios.wardrobe;


import android.content.ClipData;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


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

    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;


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

//capo forswe ho capito
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        DatabaseHelper mDatabaseHelper2 = new DatabaseHelper(getActivity());
        FloatingActionButton floatingActionButton = view.findViewById(R.id.floatingActionButton8);
        floatingActionButton.setOnClickListener(this);
       // listView = view.findViewById(R.id.spezzaossa2);
       // populateButtons();
        List<String> items = new ArrayList<>();
        items.add("porcodiscord");
        items.add("porcoskype");

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView = view.findViewById(R.id.gThunbergView2);
                recyclerAdapter = new RecyclerAdapter(items);
                recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(recyclerAdapter);
                        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
            recyclerView.addItemDecoration(dividerItemDecoration);

        return view;
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
            int dragEvent = event.getAction();
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
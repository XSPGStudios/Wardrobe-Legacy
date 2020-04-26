package com.ucstudios.wardrobe;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.VoiceInteractor;
import android.content.ClipData;
import android.content.DialogInterface;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.InputType;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;


public class SmsFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView mRecyclerView;
    public String pennsylvania=null;

    DatabaseHelper mDatabaseHelper;
    MainActivity mMainActivity;
    int controllodivider;
    private String mParam1;
    private String mParam2;
    ImageView imageViewempty;
    TextView textViewempty;
    RecyclerAdapterCategories recyclerAdapterCategories;


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



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_sms, container, false);
        FloatingActionButton ActionButton = view.findViewById(R.id.floating_action_button);
        mRecyclerView = view.findViewById(R.id.spezzaossa4);

        ActionButton.setOnClickListener(this);
        mDatabaseHelper = new DatabaseHelper(getActivity());
        mMainActivity = (MainActivity) getActivity();
        controllodivider=0;
        imageViewempty = view.findViewById(R.id.imageView6);
        textViewempty = view.findViewById(R.id.textView5);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_fragment);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item1 :

                        new AlertDialog.Builder(getContext())
                                .setTitle("Wardrobe")
                                .setMessage("With wardrobe you can manage your closet from distance, and have it all in your pocket" +
                                        " You can create categories to divide your items, and with the laundry function you can set a time for your clothes, and know when " +
                                        "they are ready for use, enjoy!" )
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .show();
                        break;
                    case R.id.item2 :

                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.addCategory(Intent.CATEGORY_BROWSABLE);
                        intent.setData(Uri.parse("http://www.github.com"));
                        startActivity(intent);
                        break;
                    case R.id.subitem1:
                        Toast.makeText(getContext(), "For now we are in beta, stay tuned for future updates!", Toast.LENGTH_SHORT).show();
                    case R.id.subitem2 :
                        Toast.makeText(getContext(), "For now we are in beta, stay tuned for future updates!", Toast.LENGTH_SHORT).show();
                    break;


                }
                return SmsFragment.super.onOptionsItemSelected(item);
            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
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
        setHasOptionsMenu(true);


        populateButtons();


        return view;


    }



    public void AddData(String newEntry, int icon) {
        boolean insertData = mDatabaseHelper.addData(newEntry,icon);
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
        int controlloperempty=0;
        Cursor data = mDatabaseHelper.getData();
        final ArrayList<String> listData = new ArrayList<>();
        final ArrayList<Integer> iconsdata = new ArrayList<>();
        while (data.moveToNext()) {
            listData.add(data.getString(0));
            iconsdata.add(data.getInt(1));
            controlloperempty++;
        }
        if(controlloperempty==0){
            imageViewempty.setVisibility(View.VISIBLE);
            textViewempty.setVisibility(View.VISIBLE);
        }
        else{
            imageViewempty.setVisibility(View.GONE);
            textViewempty.setVisibility(View.GONE);
        }
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerAdapterCategories= new RecyclerAdapterCategories(getContext(),listData,iconsdata);
        mMainActivity.Categories=listData;
       // final AdapterCategories adapterCategories = new AdapterCategories(mRecyclerView.getContext(), R.layout.adapter_categories, listData, iconsdata);
       // mRecyclerView.setAdapter(adapterCategories);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(recyclerAdapterCategories);
        if(controllodivider==0){
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
            mRecyclerView.addItemDecoration(dividerItemDecoration);
            controllodivider=1;
        }

        ItemTouchHelper.Callback callback =
                new SimpleItemTouchHelperCallback(recyclerAdapterCategories);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(mRecyclerView);
        boolean risultato = longClickListener.onLongClick(mRecyclerView);

        recyclerAdapterCategories.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            int pos = mRecyclerView.indexOfChild(v);
                mMainActivity.Name = listData.get(pos);
                Log.v("message", "List Item " + pos + " Click");
                Log.v("asd", mMainActivity.Name+" selected");

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container, new ListFragment()).addToBackStack("List");
                transaction.commit();
            }
        });

    }

    View.OnLongClickListener longClickListener = new View.OnLongClickListener() {

        @Override
        public boolean onLongClick(View v) {


            ClipData merda = ClipData.newPlainText("", "");
            View.DragShadowBuilder myShadowBuilder = new View.DragShadowBuilder(v);
            v.startDrag(merda, myShadowBuilder, v, 0);
            v.setOnDragListener(new View.OnDragListener() {
                @Override
                public boolean onDrag(View v, DragEvent event) {
                    Log.i("msg","PICCHIASPORHI");
                    return false;
                }
            });
            return true;
        }

    };

    View.OnDragListener dragListener = new View.OnDragListener() {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            int dragEvent = event.getAction();
            switch(dragEvent) {
                case DragEvent.ACTION_DRAG_ENTERED:
                    Log.i("msg","PICCHIASPORHI");
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    Log.i("msg","PICCHIASPORHI");
                    break;
                case DragEvent.ACTION_DROP:
                    Log.i("msg","PICCHIASPORHI");
                    break;
            }
            return true;
        }
    };

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(1, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

            return false;

        }


        @SuppressLint("SetTextI18n")
        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            final int position = viewHolder.getAdapterPosition();


            switch (direction) {





                case ItemTouchHelper.LEFT:
                {final ArrayList<String> listData = new ArrayList<>();
                    final Cursor c = mDatabaseHelper.getData();
                    while (c.moveToNext()){
                        listData.add(c.getString(0));
                    }

                    final EditCategoriesDialog dialog = new EditCategoriesDialog(getContext(),1);
                    dialog.show();
                    dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            populateButtons();
                        }
                    });
                    dialog.setDialogResult(new EditCategoriesDialog.OnMyDialogResult4() {
                        @Override
                        public void finish(String result1, int icon) {
                            Cursor c = mDatabaseHelper.getData();
                            ArrayList<String> UniquenessControl = new ArrayList<>();
                            int control = 0;
                            while (c.moveToNext()) {
                                UniquenessControl.add(c.getString(1));
                            }
                            for (int i = 0; i < UniquenessControl.size(); i++) {
                                if (result1.equals(UniquenessControl.get(i))) {
                                    control++;
                                }
                            }
                            if (control == 0) {
                                String cocco = String.valueOf(result1);
                                String ketamina = "name  TEXT";
                                String sugone = "name";
                                if (cocco.equals("CANE")) {
                                    pennsylvania = listData.get(position);

                                    for (int i = 0; i < listData.size(); i++) { //MAGIC FIRST FOR LOOP
                                        String minipera = listData.get(i);
                                        if (!minipera.equals(pennsylvania)) {

                                            ketamina = ketamina + "," + listData.get(i) + " TEXT";
                                            sugone = sugone + "," + listData.get(i) + "";

                                        }
                                    }

                                    OutfitColumnRemover(ketamina, sugone);


                                    Toast.makeText(mMainActivity, listData.get(position) + " Deleted!", Toast.LENGTH_SHORT).show();
                                    Delete("categories_table", listData.get(position));
                                    TableRemover(listData.get(position));
                                    Log.i("msg", "Table " + listData.get(position) + " removed");
                                    populateButtons();
                                    dialog.dismiss();

                                } else if (cocco.equals("culocane")) {
                                    mDatabaseHelper.ReplaceIcon(icon,position+1);
                                    dialog.dismiss();
                                    populateButtons();
                                    Log.i("msg", "pic changed!");
                                } else if (!cocco.equals("") & !cocco.equals("CANE") & !cocco.equals("culocane")) {

                                    String ketamina2 = "name  TEXT";
                                    String sugone2 = "name";
                                    int peso = position;


                                    for (int i = 0; i < listData.size(); i++) {

                                        sugone2 = sugone2 + "," + listData.get(i) + "";
                                        if (i != peso) {

                                            ketamina2 = ketamina2 + "," + listData.get(i) + " TEXT";


                                        } else {
                                            ketamina2 = ketamina2 + ", " + result1 + " TEXT";

                                        }
                                    }


                                    OutfitColumnNameChaneger(ketamina2, sugone2);
                                    Replace("categories_table", result1, position + 1);
                                    TableRenamer(listData.get(position), result1);
                                    Log.i("msg", "Modified " + listData.get(position) + " to " + result1);
                                    dialog.dismiss();
                                    populateButtons();

                                }
                            } else {
                                Toast.makeText(getContext(), "A Category with that name already exists!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    break;}

                    case ItemTouchHelper.RIGHT:
                        final ArrayList<String> listData = new ArrayList<>();
                        final Cursor c = mDatabaseHelper.getData();
                        while (c.moveToNext()){
                            listData.add(c.getString(0));
                        }

                        final EditCategoriesDialog dialog = new EditCategoriesDialog(getContext(),1);
                        dialog.show();
                        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                populateButtons();
                            }
                        });
                        dialog.setDialogResult(new EditCategoriesDialog.OnMyDialogResult4() {
                            @Override
                            public void finish(String result1, int icon) {
                                Cursor c = mDatabaseHelper.getData();
                                ArrayList<String> UniquenessControl = new ArrayList<>();
                                int control = 0;
                                while (c.moveToNext()) {
                                    UniquenessControl.add(c.getString(1));
                                }
                                for (int i = 0; i < UniquenessControl.size(); i++) {
                                    if (result1.equals(UniquenessControl.get(i))) {
                                        control++;
                                    }
                                }
                                if (control == 0) {
                                    String cocco = String.valueOf(result1);
                                    String ketamina = "name  TEXT";
                                    String sugone = "name";
                                    if (cocco.equals("CANE")) {
                                        pennsylvania = listData.get(position);

                                        for (int i = 0; i < listData.size(); i++) { //MAGIC FIRST FOR LOOP
                                            String minipera = listData.get(i);
                                            if (!minipera.equals(pennsylvania)) {

                                                ketamina = ketamina + "," + listData.get(i) + " TEXT";
                                                sugone = sugone + "," + listData.get(i) + "";

                                            }
                                        }

                                        OutfitColumnRemover(ketamina, sugone);


                                        Toast.makeText(mMainActivity, listData.get(position) + " Deleted!", Toast.LENGTH_SHORT).show();
                                        Delete("categories_table", listData.get(position));
                                        TableRemover(listData.get(position));
                                        Log.i("msg", "Table " + listData.get(position) + " removed");
                                        populateButtons();
                                        dialog.dismiss();

                                    } else if (cocco.equals("culocane")) {
                                        mDatabaseHelper.ReplaceIcon(icon,position+1);
                                        dialog.dismiss();
                                        populateButtons();
                                        Log.i("msg", "pic changed!");
                                    } else if (!cocco.equals("") & !cocco.equals("CANE") & !cocco.equals("culocane")) {

                                        String ketamina2 = "name  TEXT";
                                        String sugone2 = "name";
                                        int peso = position;


                                        for (int i = 0; i < listData.size(); i++) {

                                            sugone2 = sugone2 + "," + listData.get(i) + "";
                                            if (i != peso) {

                                                ketamina2 = ketamina2 + "," + listData.get(i) + " TEXT";


                                            } else {
                                                ketamina2 = ketamina2 + ", " + result1 + " TEXT";

                                            }
                                        }


                                        OutfitColumnNameChaneger(ketamina2, sugone2);
                                        Replace("categories_table", result1, position + 1);
                                        TableRenamer(listData.get(position), result1);
                                        Log.i("msg", "Modified " + listData.get(position) + " to " + result1);
                                        dialog.dismiss();
                                        populateButtons();

                                    }
                                } else {
                                    Toast.makeText(getContext(), "A Category with that name already exists!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        break;






            }
        }




        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(getContext(), R.color.buttontrue))
                    .addSwipeLeftActionIcon(R.drawable.ic_editimsto)
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(getContext(),R.color.buttontrue))
                    .addSwipeRightActionIcon(R.drawable.ic_editimsto)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };



    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.floating_action_button:

                final EditCategoriesDialog editCategoriesDialog = new EditCategoriesDialog(getContext(),0);
                editCategoriesDialog.show();
                editCategoriesDialog.setDialogResult(new EditCategoriesDialog.OnMyDialogResult4() {
                    @Override
                    public void finish(String result1, int icon) {
                        boolean space=false;
                        for(int i=0;i<result1.length();i++){
                            if (result1.charAt(i)==' '){
                                   space=true;
                            }
                        }
                        if(!space){
                        Cursor c = mDatabaseHelper.getData();
                        final ArrayList<String> UniquenessControl = new ArrayList<>();
                        int control=0;
                        while(c.moveToNext()) {
                            UniquenessControl.add(c.getString(0));
                        }
                        for(int i=0;i<UniquenessControl.size();i++){
                            if(result1.equals(UniquenessControl.get(i))){
                                control++;
                            }
                        }
                        if(control==0){
                        mDatabaseHelper.PINZA = result1;
                        AddData(mDatabaseHelper.PINZA,icon);
                        populateButtons();
                        editCategoriesDialog.dismiss();
                    }
                        else {
                            Toast.makeText(getContext(), "A category with this name already exists!", Toast.LENGTH_SHORT).show();
                        }
                    }

                        else{
                            Toast.makeText(getContext(),"Space is not allowed in category name!",Toast.LENGTH_SHORT).show();
                        }


                    }
                });

                break;

                /*final String[] m_Text = {""};
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
                break;*/

        }
    }



    }









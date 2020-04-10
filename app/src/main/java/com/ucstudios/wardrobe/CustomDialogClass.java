package com.ucstudios.wardrobe;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class CustomDialogClass extends Dialog implements View.OnClickListener {


    public Dialog d;
    public Button button;
    public Button buttoncancel;
    DatabaseHelper mDatabaseHelper;
    OnMyAdapterResult mAdapterResult;
    MainActivity mainActivity;
    OutfitFragment mOutfit;
    EditText editText;
    ArrayList<String> switchStatus;
    int stato;

    private ListView mRecyclerView;
    private ArrayList<Integer> SpinnerValue;
    String itemdelcaso;



    public CustomDialogClass(Activity a,ArrayList<String> ColumnsAction,int stato,ArrayList<Integer>SpinnerValue) {
        super(a);
        this.switchStatus=ColumnsAction;
        this.stato=stato;
        this.SpinnerValue=SpinnerValue;
        this.itemdelcaso=itemdelcaso;



    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);
        button = findViewById(R.id.button);
        button.setOnClickListener(this);
        buttoncancel = findViewById(R.id.button2);
        buttoncancel.setOnClickListener(this);

        mDatabaseHelper = new DatabaseHelper(getContext());
        editText = findViewById(R.id.editText);
        mRecyclerView = findViewById(R.id.spezzaossa4);
        populateButtons();

        if(stato==1){
            buttoncancel.setVisibility(View.VISIBLE);
        }


    }
    private void populateButtons() {
        Cursor data = mDatabaseHelper.getData();
        final ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()) {
            listData.add(data.getString(1));
        }

        CustomListView adapter = new CustomListView(mRecyclerView.getContext(), R.layout.adapter_view_layout, listData,switchStatus,stato,SpinnerValue);
        mRecyclerView.setAdapter(adapter);
    }

    private void toastMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();

    }

    public void AddData(String newEntry) {
        boolean insertData = mDatabaseHelper.addData2(newEntry);
        toastMessage("New Category Created!");
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button:

                Log.i("msg",editText.getText().toString());


                    if(String.valueOf(editText.getText()).equals("")){
                        Toast.makeText(getContext(),"POLIZIA NOME!",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        mAdapterResult.finish(String.valueOf(editText.getText()));
                        dismiss();
                    }
                    break;
            case R.id.button2:
               mAdapterResult.finish("ELIMINAZIONETOTALE");

                dismiss();
                break;


        }


    }


    public void setAdapterResult(OnMyAdapterResult adapterResult){

        mAdapterResult = adapterResult;
    }



    public interface OnMyAdapterResult{
        void finish(String result);
    }
}
package com.ucstudios.wardrobe;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


public class CalendarAddEvent extends Dialog {
    DatabaseHelper mDatabaseHelper;
    ArrayList<Integer> DateSelected;
    public CalendarAddEvent(Context context,ArrayList<Integer> DateSelected){
        super(context);
        this.DateSelected=DateSelected;



    }




    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.calendaraddevent);

        String Day = DateSelected.get(0).toString();
        String Month = DateSelected.get(1).toString();
        String Year = DateSelected.get(2).toString();
        ArrayList<String> patetico2 = new ArrayList<>();




        if(Month.length()==1){
            List<String> patetico = new ArrayList<>();
            patetico.add("0");
            patetico.add(Month);

            Month= TextUtils.join("",patetico);
        }


        patetico2.add(Day);
        patetico2.add(Month);
        patetico2.add(Year);
        final String totalcode = TextUtils.join("",patetico2);
        final String total = TextUtils.join(" ",patetico2);
        final Integer Datecode = Integer.parseInt(totalcode);


        Button button = findViewById(R.id.buttonInsertEvent);
        final Spinner mSpinner = findViewById(R.id.OutfitPickerSpinner);
        TextView textView = findViewById(R.id.CurrentDateSelected);
        textView.setText(total);
        mDatabaseHelper = new DatabaseHelper(getContext());
        Cursor data = mDatabaseHelper.getData2();
        final ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()) {
            listData.add(data.getString(0));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getContext(), android.R.layout.simple_spinner_item, listData);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabaseHelper.addEvent(Datecode,mSpinner.getSelectedItem().toString());
                Toast.makeText(getContext(),"Event created on "+ total,Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });


    }





}

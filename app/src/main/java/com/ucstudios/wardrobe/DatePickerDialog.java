package com.ucstudios.wardrobe;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.applandeo.materialcalendarview.EventDay;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DatePickerDialog extends Dialog {
    DatabaseHelper mDatabaseHelper;
    String outfit;
    private DatePicker datePicker;
    String total;
    String totaletto;
    public DatePickerDialog (Context context,String Outfit){
        super(context);
        this.outfit=Outfit;
    }



    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.datepicker_dialog);
        mDatabaseHelper = new DatabaseHelper(getContext());
        Button button = findViewById(R.id.buttonaddeventfromswipe);
        datePicker = findViewById(R.id.datePicker1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean Eventonthisday = false;


                for(int i=0;i<populateControlEvents().size();i++){

                    if(datePicker.getDayOfMonth()==populateControlEvents().get(i).getCalendar().get(Calendar.DAY_OF_MONTH)&&datePicker.getMonth()==populateControlEvents().get(i).getCalendar().get(Calendar.MONTH)&&datePicker.getYear()==populateControlEvents().get(i).getCalendar().get(Calendar.YEAR)){
                        Eventonthisday=true;
                    }
                }


                Trafficadata();
                if(!Eventonthisday){
                mDatabaseHelper.addEvent(Integer.parseInt(total),outfit);
                //check if already event on that day
                Toast.makeText(getContext(),"Event created on "+ totaletto,Toast.LENGTH_SHORT).show();
                dismiss();}
                else{
                    Toast.makeText(getContext(),"There is already an event on this day",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void Trafficadata(){
        String Day = String.valueOf(datePicker.getDayOfMonth());
        String Month = String.valueOf(datePicker.getMonth()+1);
        String Year = String.valueOf(datePicker.getYear());
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

        total = TextUtils.join("",patetico2);
        totaletto = TextUtils.join(" ",patetico2);


    }

    private List<EventDay> populateControlEvents(){
        List<EventDay> eventDays = new ArrayList<>();
        List<Integer> events = new ArrayList<>();
        Cursor Data = mDatabaseHelper.getEvents();
        while(Data.moveToNext()){
            events.add(Data.getInt(0));
        }
        List<Integer>Years=new ArrayList<>();
        List<Integer>Months=new ArrayList<>();
        List<Integer>Days=new ArrayList<>();

        for(int i=0;i<events.size();i++){
            int lenght=String.valueOf(events.get(i)).length();
            int event = events.get(i);
            if(lenght==7){

                Days.add(Integer.parseInt(Integer.toString(event).substring(0, 1)));





                Months.add(Integer.parseInt(Integer.toString(event).substring(1, 3)));



                Years.add(Integer.parseInt(Integer.toString(event).substring(3, 7)));



            }
            else if(lenght==8){
                Days.add(Integer.parseInt(Integer.toString(event).substring(0, 2)));





                Months.add(Integer.parseInt(Integer.toString(event).substring(2, 4)));



                Years.add(Integer.parseInt(Integer.toString(event).substring(4, 8)));

            }

            Calendar calendar = Calendar.getInstance();
            calendar.set(Years.get(i),Months.get(i)-1,Days.get(i));

            eventDays.add(new EventDay(calendar, R.drawable.ic_outfit));
        }





        return eventDays;


    }

}

package com.ucstudios.wardrobe;


import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

import static com.ucstudios.wardrobe.App.CHANNEL_1_ID;


//LAVATRICE

public class LaundryFragment extends Fragment implements TimePickerDialog.OnTimeSetListener, View.OnClickListener {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    int controllodivider;
    private int pickedHour = 0;
    private int pickedMin = 0;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mStartTimeInMillis;
    private long mTimeLeftInMillis;
    private long mEndTime;
    private NotificationManagerCompat notificationManager;
    private boolean flagLaundry = false;
    private static boolean flagCancel = false;
    private boolean flagLaundryElementi = true;
    FloatingActionButton floatingActionButtonCancelButton;
    private int positionGif = 0;

    ImageView imageViewempty;
    TextView textViewempty;

    DatabaseHelper mDatabaseHelper;
    ArrayList<String> TotalCategories = new ArrayList<>();
    ArrayList<String> ItemsInBasket = new ArrayList<>();


    public LaundryFragment() {
        // Required empty public constructor
    }

    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    List<String> itemsLaundry = new ArrayList<>();

    public static LaundryFragment newInstance(String param1, String param2) {
        LaundryFragment fragment = new LaundryFragment();
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
        View view = inflater.inflate(R.layout.fragment_lavatrice, container, false);
        FloatingActionButton floatingActionButton = view.findViewById(R.id.floatingActionButton8);
        floatingActionButtonCancelButton = view.findViewById(R.id.floatingActionButtonCancel);
        floatingActionButtonCancelButton.setVisibility(View.INVISIBLE);
        floatingActionButtonCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {

                    }
                });
                builder.setTitle("Are you sure?");
                final TextView sex = new TextView(getActivity());
                sex.setText("Cancelling the process, are you sure?");
                sex.setGravity(Gravity.CENTER);

                builder.setView(sex);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        flagCancel = true;


                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();



            }
        });

        floatingActionButton.setOnClickListener(this);
        recyclerView = view.findViewById(R.id.gThunbergView3);
        mDatabaseHelper = new DatabaseHelper(getActivity());
        notificationManager = NotificationManagerCompat.from(getContext());
        imageViewempty = view.findViewById(R.id.imageViewlavatricevuota);
        textViewempty = view.findViewById(R.id.textViewlavavuota);
        controllodivider=0;
        populateWM();



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

    public void populateWM(){



        final Cursor data1 = mDatabaseHelper.getData();
        final ArrayList<String> categories = new ArrayList<>();

        int controlloempty =0 ;
        while(data1.moveToNext()){
            categories.add(data1.getString(0));
            TotalCategories.add(data1.getString(0));
        }



        final ArrayList<String> listData = new ArrayList<>();
        final  ArrayList<Integer> icondata = new ArrayList<>();


        for(int i=0; i<categories.size();i++) {
            Cursor data = mDatabaseHelper.GetWM(categories.get(i));
            while(data.moveToNext()){
                listData.add(data.getString(0));
                ItemsInBasket.add(data.getString(0));
                icondata.add(data.getInt(1));
                controlloempty++;
            }
            Cursor dataGif = mDatabaseHelper.GetWashed(categories.get(i));
            while(dataGif.moveToNext()){
                listData.add(dataGif.getString(0));
                icondata.add(dataGif.getInt(1));

                controlloempty++;
            }
        }

        if(controlloempty==0){
            imageViewempty.setVisibility(View.VISIBLE);
            textViewempty.setVisibility(View.VISIBLE);
            flagLaundryElementi = false;
        }

        else{
            imageViewempty.setVisibility(View.GONE);
            textViewempty.setVisibility(View.GONE);
            flagLaundryElementi = true;
        }

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
    recyclerAdapter = new RecyclerAdapter(listData,icondata);
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setAdapter(recyclerAdapter);
    if(controllodivider==0){
    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
    controllodivider=1;

    }
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        boolean risultato = longClickListener.onLongClick(recyclerView);
        ItemTouchHelper.Callback callback =
                new SimpleItemTouchHelperCallback(recyclerAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);

    }
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
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

    }

    TimePickerDialog.OnTimeSetListener mTimeSetListener =
            new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(android.widget.TimePicker view,
                                      final int hourOfDay, final int minute) {
                    final Calendar c = Calendar.getInstance();

                    if ((hourOfDay == c.get(Calendar.HOUR_OF_DAY))) {
                        if (minute <= c.get(Calendar.MINUTE)) {
                            Toast.makeText(getContext(), "Incorrect time! ", Toast.LENGTH_SHORT).show();
                        }
                    }

                    if (minute <= c.get(Calendar.MINUTE)) {
                        if (hourOfDay <= c.get(Calendar.HOUR_OF_DAY)) {
                            if (c.get(Calendar.HOUR_OF_DAY) <= 22) {
                                Toast.makeText(getContext(), "Incorrect time! ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    if (flagLaundryElementi){
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {

                            }
                        });
                        builder.setTitle("Are you sure?");
                        final TextView sex = new TextView(getActivity());
                        sex.setText("Starting laundry, are you sure?");
                        sex.setGravity(Gravity.CENTER);

                        builder.setView(sex);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                ArrayList<String> dacestoalavatrice = new ArrayList<>();
                                for(int i=0;i<TotalCategories.size();i++) {
                                    Cursor c = mDatabaseHelper.GetWM(TotalCategories.get(i));
                                    while(c.moveToNext()){
                                        dacestoalavatrice.add(c.getString(0));
                                        Log.i("msg :","Swiped "+ dacestoalavatrice.get(i));
                                    }

                                }

                                for(int is=0;is<TotalCategories.size();is++) {
                                    mDatabaseHelper.WashingMachineActivated(TotalCategories.get(is));
                                }
                                populateWM();

                                floatingActionButtonCancelButton.setVisibility(View.VISIBLE);
                                c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                c.set(Calendar.MINUTE, minute);
                                c.set(Calendar.SECOND, 0);
                                Calendar calendar = Calendar.getInstance();
                                int oraAttuale = calendar.get(Calendar.HOUR_OF_DAY);
                                int minutiAttuali = calendar.get(Calendar.MINUTE);



                                if (!flagLaundry) {
                                    //mando notifica con progresso, e cambio stato flag
                                    updateTimeText(c);
                                    flagLaundry = true;
                                    String message = "Lavaggio interrotto";

                                    //progress bar
                                    Intent resultIntent = new Intent(getContext(), MainActivity.class);
                                        PendingIntent resultPendingIntent = PendingIntent.getActivity(getContext(), 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                                    int oreDiff = (hourOfDay - oraAttuale);
                                    int minutesDiff = (minute - minutiAttuali);
                                    int secondsInMillis = (oreDiff * 3600) + (minutesDiff * 60);

                                    final int progressMax =  secondsInMillis;
                                    final NotificationCompat.Builder notification = new NotificationCompat.Builder(Objects.requireNonNull(getContext()), CHANNEL_1_ID)
                                            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                                            .setSmallIcon(R.drawable.ic_wm24)
                                            .setContentTitle("Laundry status")
                                            .setContentText("Washing your clothes...")
                                            .setColor(Color.BLACK)
                                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                                            .setOngoing(true)
                                            .setOnlyAlertOnce(true)
                                            .setProgress(progressMax, 0, false)
                                            .setAutoCancel(true)
                                            .setContentIntent(resultPendingIntent);
                                    notificationManager.notify(2, notification.build());
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                                    { String channelId = "Your_channel_id";
                                        NotificationChannel channel = new NotificationChannel(
                                                channelId,
                                                "Channel human readable title",
                                                NotificationManager.IMPORTANCE_HIGH);
                                        notificationManager.createNotificationChannel(channel);
                                        notification.setChannelId(channelId);
                                    }


                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            SystemClock.sleep(2000);

                                            for (int progress = 0; progress <= progressMax && !flagCancel; progress += 1) {
                                                    notification.setProgress(progressMax, progress, false);
                                                    notificationManager.notify(2, notification.build());
                                                    SystemClock.sleep(1000);
                                                }
                                                if (flagCancel) {
                                                    notification.setContentText("Process cancelled")
                                                            .setProgress(0, 0, false)
                                                            .setOngoing(false);
                                                    notificationManager.notify(2, notification.build());
                                                    floatingActionButtonCancelButton.setVisibility(View.INVISIBLE);

                                                    ArrayList<String> Finitodilavare = new ArrayList<>();
                                                    for(int i=0;i<TotalCategories.size();i++) {
                                                        Cursor c = mDatabaseHelper.GetWashed(TotalCategories.get(i));
                                                        while(c.moveToNext()) {
                                                            Finitodilavare.add(c.getString(0));
                                                        }
                                                    }
                                                    for(int is=0;is<TotalCategories.size();is++) {
                                                        for(int check=0;check<Finitodilavare.size();check++){
                                                            //get the table name
                                                            mDatabaseHelper.toLaundry(TotalCategories.get(is),Finitodilavare.get(check));
                                                        }
                                                    }
                                                }

                                                else {
                                                    notification.setContentText("Lavatrice pronta!")
                                                            .setProgress(0, 0, false)
                                                            .setOngoing(false);
                                                    notificationManager.notify(2, notification.build());
                                                    floatingActionButtonCancelButton.setVisibility(View.INVISIBLE);
                                                    ArrayList<String> Finitodilavare = new ArrayList<>();
                                                    for(int i=0;i<TotalCategories.size();i++){
                                                        Cursor c = mDatabaseHelper.GetWashed(TotalCategories.get(i));
                                                        while(c.moveToNext()) {
                                                            Finitodilavare.add(c.getString(0));

                                                        }
                                                    }
                                                    for(int is=0;is<TotalCategories.size();is++){

                                                        for(int check=0;check<Finitodilavare.size();check++){
                                                        //get the table name
                                                        mDatabaseHelper.toWardrobe(TotalCategories.get(is),Finitodilavare.get(check));
                                                        }
                                                    }
                                                }
                                            flagLaundry = false;
                                                flagCancel = false;
                                            }
                                    }).start();
                                    dialog.dismiss();
                                }
                                else {
                                    Toast.makeText(getContext(), "A laundry has already been set", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        builder.show();
                    }

                    else {
                        Toast.makeText(getContext(), "The laundry is empty! Try to add some clothes", Toast.LENGTH_SHORT).show();
                    }
                }
            };
//push failed
    private void updateTimeText (Calendar c) {
        Toast.makeText(getContext(), "Laundry started, check the notification for the progress!", Toast.LENGTH_SHORT).show();
    }





    @Override
    public void onClick(View v) {
        DialogFragment timePicker = new TimePickerFragment(mTimeSetListener);
            timePicker.show(getFragmentManager(), "time picker");
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }


        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            final int position = viewHolder.getAdapterPosition();
            positionGif = position;
            switch (direction) {
                case ItemTouchHelper.RIGHT:
                   if (!flagLaundry) {
                       AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                       builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                           @Override
                           public void onDismiss(DialogInterface dialog) {

                           }
                       });
                       builder.setTitle("Are you sure?");
                       final TextView sex = new TextView(getActivity());
                       sex.setText("Are you sure you want to put this in the closet?");
                       sex.setGravity(Gravity.CENTER);

                       builder.setView(sex);
                       builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                       ArrayList<String> dacestoalavatrice = new ArrayList<>();
                       for (int i = 0; i < TotalCategories.size(); i++) {
                           Cursor c = mDatabaseHelper.GetWMSpecific(TotalCategories.get(i), ItemsInBasket.get(position));
                           while (c.moveToNext()) {
                               dacestoalavatrice.add(c.getString(0));
                               Log.i("msg :", "Swiped " + dacestoalavatrice.get(0));
                           }
                       }
                       for (int is = 0; is < TotalCategories.size(); is++) {
                           //get the table name
                           mDatabaseHelper.toWardrobe(TotalCategories.get(is), dacestoalavatrice.get(0));
                           Log.i("msg", "Passaggio a Lavatrice completato per " + dacestoalavatrice.get(0));
                       }
                       populateWM();
                           }
                       });
                       builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               dialog.cancel();
                               populateWM();
                           }
                       });
                       builder.show();
                   }
                   else {
                       AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                       builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                           @Override
                           public void onDismiss(DialogInterface dialog) {

                           }
                       });
                       builder.setTitle("Error");
                       final TextView sex = new TextView(getActivity());
                       sex.setText("You can't change items positions while the laundry is running!");
                       sex.setGravity(Gravity.CENTER);

                       builder.setView(sex);
                       builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               dialog.cancel();
                               populateWM();
                           }
                       });
                       builder.show();
                   }
                    break;
                case ItemTouchHelper.LEFT:
                    if (!flagLaundry) {
                        ArrayList<String> dacestoalavatrices = new ArrayList<>();
                        for (int i = 0; i < TotalCategories.size(); i++) {
                            Cursor c = mDatabaseHelper.GetWMSpecific(TotalCategories.get(i), ItemsInBasket.get(position));
                            while (c.moveToNext()) {
                                dacestoalavatrices.add(c.getString(0));
                                Log.i("msg :", "Swiped " + dacestoalavatrices.get(0));
                            }
                        }
                        for (int is = 0; is < TotalCategories.size(); is++) {
                            //get the table name
                            mDatabaseHelper.toBasketmodif(TotalCategories.get(is), dacestoalavatrices.get(0));
                            Log.i("msg", "Passaggio a Lavatrice completato per " + dacestoalavatrices.get(0));
                        }
                        populateWM();
                        break;
                        }
                    else {

                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {

                            }
                        });
                        builder.setTitle("Error");
                        final TextView sex = new TextView(getActivity());
                        sex.setText("You can't change items positions while the laundry is running!");
                        sex.setGravity(Gravity.CENTER);
                        builder.setView(sex);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                populateWM();
                            }
                        });
                        builder.show();
                        }
                    }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(getContext(), R.color.verdetattico))
                    .addSwipeRightActionIcon(R.drawable.ic_hanger)
                    .create()
                    .decorate();
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(getContext(),R.color.bluetattico))
                    .addSwipeLeftActionIcon(R.drawable.ic_basket24)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };
}


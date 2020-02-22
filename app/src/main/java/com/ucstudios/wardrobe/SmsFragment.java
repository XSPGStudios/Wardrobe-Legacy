package com.ucstudios.wardrobe;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceControl;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SmsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SmsFragment extends Fragment implements View.OnClickListener{



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public SmsFragment() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
    //// * @return A new instance of fragment SmsFragment.
     */
    // TODO: Rename and change types and number of parameters
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
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sms, container, false);
        //Button topButton = view.findViewById(R.id.topButton);
        //topButton.setOnClickListener(this);

        FloatingActionButton ActionButton = view.findViewById(R.id.floating_action_button);
        ActionButton.setOnClickListener(this);





        return view;



    }

    @Override


    public void onClick(View v) {

        switch (v.getId())
        {
            /*case R.id.topButton :
        FragmentTransaction replace = getFragmentManager().beginTransaction().replace(R.id.container, ListFragment.newInstance("", ""));
        replace.addToBackStack(null);
        replace.commit();
        break;*/


            case R.id.floating_action_button:

                final TableLayout tableTOTTO = getActivity().findViewById(R.id.Table);

                final String[] m_Text = {""};
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Title");


                final EditText input = new EditText(getActivity());

                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
                builder.setView(input);


                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text[0] = input.getText().toString();
                        TableRow row = new TableRow(getActivity());
                        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                        row.setLayoutParams(lp);
                        Button novelty = new Button(getActivity());
                        ViewGroup.LayoutParams layoutParams = row.getLayoutParams();
                        layoutParams.height=400;
                        layoutParams.width=1000;
                        novelty.setLayoutParams(layoutParams);
                        novelty.setText(m_Text[0]);

                        row.addView(novelty);
                        tableTOTTO.addView(row);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();


                break;


        }}






        }




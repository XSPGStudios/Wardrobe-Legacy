package com.ucstudios.wardrobe;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

public class EditCategoriesDialog extends Dialog implements View.OnClickListener{


    public Button button;
    public TextView buttonCancel;
    public EditText editText;
    OnMyDialogResult4 mDialogResult;
    public DatabaseHelper mDatabasehelper1;
    MainActivity mainActivity;
    private Integer[] Icons = {
            R.drawable.ic_sweater,
            R.drawable.ic_jeans,
            R.drawable.ic_hoodie,


    };
    CustomIconPickerDialog dialog = new CustomIconPickerDialog(getContext());

    public EditCategoriesDialog(Context context){
        super(context);


    }



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_edit_dialog);
        button = findViewById(R.id.button);
        buttonCancel = findViewById(R.id.textView3);
        ImageButton iconset = findViewById(R.id.imageButton);
        iconset.setOnClickListener(this);
        editText = findViewById(R.id.editText);
        button.setOnClickListener(this);
        buttonCancel.setOnClickListener(this);

        mDatabasehelper1 = new DatabaseHelper(getContext());



    }








    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button:
                mDialogResult.finish(String.valueOf(editText.getText()));
                editText.setText("");
                break;
            case R.id.textView3:
                mDialogResult.finish("CANE");
                break;
            case R.id.imageButton:
                mDialogResult.finish("culocane");
                dialog.show();


                break;


        }
    }

    public void setDialogResult(OnMyDialogResult4 dialogResult){

        mDialogResult = dialogResult;
    }



    public interface OnMyDialogResult4{

        void finish(String result1);
    }
}
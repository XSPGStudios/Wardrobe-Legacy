package com.ucstudios.wardrobe;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class EditCategoriesDialog extends Dialog implements View.OnClickListener{


    public Button button;
    public TextView buttonCancel;
    public EditText editText;
    OnMyDialogResult4 mDialogResult;
    public DatabaseHelper mDatabasehelper1;
    int stato;
    Integer iconazza;
    ImageButton iconset;
    private Integer[] Icons = {
            R.drawable.ic_sweater,
            R.drawable.ic_jeans,
            R.drawable.ic_hoodie,
            R.drawable.ic_shoes,
            R.drawable.ic_backpack,
            R.drawable.ic_denim,
            R.drawable.ic_shirt,
            R.drawable.ic_watch,


    };
    ImageView imageView;


    CustomIconPickerDialog dialog = new CustomIconPickerDialog(getContext());

    public EditCategoriesDialog(Context context,int stato){
        super(context);
        this.stato=stato;


    }



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_edit_dialog);
        Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));

        button = findViewById(R.id.button);
        buttonCancel = findViewById(R.id.textView3);
        iconset = findViewById(R.id.imageButton);
        iconset.setOnClickListener(this);
        editText = findViewById(R.id.editText);
        button.setOnClickListener(this);
        buttonCancel.setOnClickListener(this);
        imageView = findViewById(R.id.imageViewalien);
        if(stato==0){
            buttonCancel.setVisibility(View.INVISIBLE);
        }
        mDatabasehelper1 = new DatabaseHelper(getContext());
        if(stato==1){

        }

    }








    @Override
    public void onClick(View v) {
        if(stato==1){

        switch(v.getId()) {
            case R.id.button:
                mDialogResult.finish(String.valueOf(editText.getText()), 0);
                editText.setText("");
                break;
            case R.id.textView3:
                mDialogResult.finish("CANE", 0);
                break;
            case R.id.imageButton:
                dialog.setIconResult(new CustomIconPickerDialog.OnIconSelected() {
                    @Override
                    public void finish(int icon) {
                        mDialogResult.finish("culocane", icon);
                    }
                });
                dialog.show();


                break;
        }

        }
        if(stato==0){

           switch (v.getId()){case R.id.button:
               if(iconazza!=null){
                   String cane = String.valueOf(editText.getText());
                   if(!cane.equals("")){
                       mDialogResult.finish(String.valueOf(editText.getText()),iconazza);
                dismiss();}}
               else if(iconazza==null)
                {

                   Toast.makeText(getContext(),"Choose an Icon!",Toast.LENGTH_SHORT).show();
               }


               break;
               case R.id.imageButton:
                   dialog.setIconResult(new CustomIconPickerDialog.OnIconSelected() {
                       @Override
                       public void finish(int icon) {
                           iconazza=icon;
                           imageView.setImageResource(Icons[icon]);
                       }
                   });
                   dialog.show();
                   break;
           }
        }
    }

    public void setDialogResult(OnMyDialogResult4 dialogResult){

        mDialogResult = dialogResult;
    }



    public interface OnMyDialogResult4{

        void finish(String result1,int icon);
    }
}
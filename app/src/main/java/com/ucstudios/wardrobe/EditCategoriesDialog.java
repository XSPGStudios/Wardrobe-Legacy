package com.ucstudios.wardrobe;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Gravity;
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
            R.drawable.ic_basketballjersey,
            R.drawable.ic_bathrobe,
            R.drawable.ic_belt,
            R.drawable.ic_blouse,
            R.drawable.ic_boot,
            R.drawable.ic_boot2,
            R.drawable.ic_bowtie,
            R.drawable.ic_bra,
            R.drawable.ic_cap,
            R.drawable.ic_coat,
            R.drawable.ic_dress,
            R.drawable.ic_glasses,
            R.drawable.ic_gloves,
            R.drawable.ic_bag,
            R.drawable.ic_hat,
            R.drawable.ic_heels,
            R.drawable.ic_jacket,
            R.drawable.ic_pimuno,
            R.drawable.ic_jacket2,
            R.drawable.ic_necklace,
            R.drawable.ic_salopette,
            R.drawable.ic_mutandefemmina,
            R.drawable.ic_cargo,
            R.drawable.ic_polo,
            R.drawable.ic_24h,
            R.drawable.ic_purse,
            R.drawable.ic_scarf,
            R.drawable.ic_tee,
            R.drawable.ic_top,
            R.drawable.ic_mocasso,
            R.drawable.ic_shorts,
            R.drawable.ic_papere,
            R.drawable.ic_skirt,
            R.drawable.ic_slippers,
            R.drawable.ic_socks,
            R.drawable.ic_tie,
            R.drawable.ic_trench,
            R.drawable.ic_underwear,
            R.drawable.ic_vest,
            R.drawable.ic_wallet,
            R.drawable.ic_winterhat,
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
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {

                    }
                });
                builder.setTitle("Are you sure?");
                final TextView sex = new TextView(getContext());
                sex.setText("Sure about deleting this?");
                sex.setGravity(Gravity.CENTER);


                builder.setView(sex);
                builder.setPositiveButton("OK", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mDialogResult.finish("CANE", 0);
                    }
                });
                builder.show();

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
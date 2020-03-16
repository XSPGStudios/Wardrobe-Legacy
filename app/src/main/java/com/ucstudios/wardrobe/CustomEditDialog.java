package com.ucstudios.wardrobe;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

public class CustomEditDialog extends Dialog implements View.OnClickListener{


    public Button button;
    public Button buttonCancel;
    public EditText editText;
    OnMyDialogResult mDialogResult;

    public CustomEditDialog(Context context){
        super(context);


    }



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_edit_dialog);
        button = findViewById(R.id.button);
        buttonCancel = findViewById(R.id.button2);
        editText = findViewById(R.id.editText);
        button.setOnClickListener(this);


    }



    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button:
                if(mDialogResult!=null){

                    mDialogResult.finish(String.valueOf(editText.getText()));
                }

                CustomEditDialog.this.dismiss();
                break;
            case R.id.button2:
                dismiss();
                break;


        }
    }

    public void setDialogResult(OnMyDialogResult dialogResult){

        mDialogResult = dialogResult;
    }



    public interface OnMyDialogResult{

        void finish(String result);
    }
}

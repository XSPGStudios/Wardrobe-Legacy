package com.ucstudios.wardrobe;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;


public class CustomIconPickerDialogItems extends Dialog {

    public GridView duca;
    int labels;
    OnIconSelected mIconSelected;

    public CustomIconPickerDialogItems(Context context){
        super (context);

    }



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.custom_icon_picker_dialog);
        duca = findViewById(R.id.gridView);
        duca.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mIconSelected.finish(position);
                dismiss();

            }
        });
        labels = R.array.icons;
        populateImages();


    }

    private void populateImages(){
        CustomIconAdapterItems adapter = new CustomIconAdapterItems(duca.getContext(), labels);
        duca.setAdapter(adapter);
    }

    public void setIconResult(OnIconSelected onIconSelected){
        mIconSelected = onIconSelected;
    }



    public interface OnIconSelected{

        void finish(int icon);
    }






}
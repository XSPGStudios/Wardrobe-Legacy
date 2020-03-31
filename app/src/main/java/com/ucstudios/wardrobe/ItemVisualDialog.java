package com.ucstudios.wardrobe;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

public class ItemVisualDialog extends Dialog {

    public ItemVisualDialog(Context context){
        super(context);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.item_visual_dialog);

    }


}

package com.ucstudios.wardrobe;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;

import androidx.annotation.NonNull;

import java.util.Objects;

public class FirstOpenDialog extends Dialog {


    public FirstOpenDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.first_open_dialog);
        Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));


    }
}

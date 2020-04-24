package com.ucstudios.wardrobe;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Objects;

import static com.ucstudios.wardrobe.R.id.dialogtoolbar;

public class VisualDialogItemDialogTrue extends Dialog implements View.OnClickListener {

    TextView name;
    TextView brand;
    TextView value;

    ImageView image;
    String[] itemdatas;
    ArrayList<byte[]> itemimage = new ArrayList<>();


    public VisualDialogItemDialogTrue(Context context, ArrayList<String> itemdata,ArrayList<byte[]> image){
        super(context);
        this.itemdatas=itemdata.toArray(new String[0]);
        this.itemimage=image;

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_visual_item_true);
        Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));

        name = findViewById(R.id.name);
        brand = findViewById(R.id.Brand);
        value = findViewById(R.id.value);
        image = findViewById(R.id.imageViewsas);
        image.setClipToOutline(true);




        name.setText(itemdatas[0]);
        brand.setText(itemdatas[1]);
        value.setText("$ "+itemdatas[2]);
        image.setImageBitmap(Utils.getImage(itemimage.get(0)));







    }
    @Override
    public void onClick(View v) {

    }
}

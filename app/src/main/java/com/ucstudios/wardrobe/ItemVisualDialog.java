package com.ucstudios.wardrobe;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ItemVisualDialog extends Dialog implements View.OnClickListener {

    private Integer[] Icons = {
            R.drawable.ic_nike,
            R.drawable.ic_adidas,
            R.drawable.ic_alexmc,


    };

    public EditText name;
    public EditText size;
    public EditText brand;
    public EditText value;
    public Spinner spinner;
    public Button okbutton;
    public ImageView icons;
    ItemCreatedInterface mItemCreation;
    CustomIconPickerDialogItems dialogItems;
    Integer iconvalue;


    public ItemVisualDialog(Context context){
        super(context);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.item_visual_dialog);
        name = findViewById(R.id.name);
        size = findViewById(R.id.size);
        brand = findViewById(R.id.brand);
        value = findViewById(R.id.value);
        spinner = findViewById(R.id.currencyspinner);
        okbutton = findViewById(R.id.button3);
        icons = findViewById(R.id.icon);
        dialogItems = new CustomIconPickerDialogItems(getContext());
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getContext(),R.array.currencies,android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter1);
        okbutton.setOnClickListener(this);
        icons.setOnClickListener(this);
        iconvalue=0;






}

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.icon:
                dialogItems.show();
                dialogItems.setIconResult(new CustomIconPickerDialogItems.OnIconSelected() {
                    @Override
                    public void finish(int icon) {
                        iconvalue = icon;
                        icons.setImageResource(Icons[icon]);
                    }
                });
                break;
            case R.id.button3:
                if(String.valueOf(name.getText()).equals("")||String.valueOf(size.getText()).equals("")||String.valueOf(brand.getText()).equals("")||String.valueOf(value.getText()).equals("")){
                    Toast.makeText(getContext(),"Something is still empty  :( ", Toast.LENGTH_SHORT).show();
                }

                else {
                    mItemCreation.finish(String.valueOf(name.getText()), String.valueOf(size.getText()), String.valueOf(brand.getText()), Integer.parseInt(String.valueOf(value.getText())), spinner.getSelectedItemPosition(), iconvalue);
                    Toast.makeText(getContext(), "Item inserted!", Toast.LENGTH_SHORT).show();
                }
                break;

        }





    }

    public void ItemCreation(ItemCreatedInterface itemcreated){

        mItemCreation = itemcreated;
    }

    public interface ItemCreatedInterface{

        void finish(String name,String size,String brand,Integer value,Integer currency,Integer icon);
    }





}

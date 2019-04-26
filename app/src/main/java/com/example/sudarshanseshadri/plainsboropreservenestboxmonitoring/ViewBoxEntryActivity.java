package com.example.sudarshanseshadri.plainsboropreservenestboxmonitoring;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.sudarshanseshadri.plainsboropreservenestboxmonitoring.MyObjects.Bird;
import com.example.sudarshanseshadri.plainsboropreservenestboxmonitoring.MyObjects.BirdList;

import java.util.ArrayList;

public class ViewBoxEntryActivity extends AppCompatActivity {
    EditText species;

    EditText boxNumber, activity, nest, eggs, nestlings;

    ImageView birdImage;

    int pos=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_box_entry);
        setTitle("New Box Entry");


        species=findViewById(R.id.id_editText_species);
        boxNumber=findViewById(R.id.id_editText_boxNumber);
        activity=findViewById(R.id.id_editText_activity);
        nest=findViewById(R.id.id_editText_nest);
        eggs=findViewById(R.id.id_editText_eggs);
        nestlings=findViewById(R.id.id_editText_nestlings);

        species.setInputType(InputType.TYPE_NULL);
        boxNumber.setInputType(InputType.TYPE_NULL);
        activity.setInputType(InputType.TYPE_NULL);
        nest.setInputType(InputType.TYPE_NULL);
        eggs.setInputType(InputType.TYPE_NULL);
        nestlings.setInputType(InputType.TYPE_NULL);


        birdImage=findViewById(R.id.id_imageView_birdPicCreate);

        //populate the EditText if we are editing
        Bundle boxInfo=getIntent().getExtras();

        if(boxInfo.get("BOXNUM")!=null)
        {
            try {
                boxNumber.setText(  boxInfo.getInt("BOXNUM") + ""  );
            }
            catch (Exception e)
            {

            }
        }

        species.setText(boxInfo.get("SPECIES").toString());
        activity.setText(boxInfo.get("ACTIVITY").toString());
        nest.setText(boxInfo.get("NEST").toString());
        eggs.setText(boxInfo.get("EGGS").toString());
        nestlings.setText(boxInfo.get("NESTLINGS").toString());
        pos=boxInfo.getInt("POS");


        setBirdImage(species.getText().toString());

        species.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setBirdImage(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        final MenuItem menuItem = menu.add(Menu.NONE, 1000, Menu.NONE, R.string.done);
        MenuItemCompat.setShowAsAction(menuItem, MenuItem.SHOW_AS_ACTION_IF_ROOM);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        finish();
        return true;
    }

    public void setBirdImage(String birdName)
    {
        BirdList birdList=new BirdList();
        ArrayList<Bird> birds=birdList.getBirdArrayList();
        int imageSearch=R.drawable.generic_bird;
        for (Bird bird : birds)
        {
            if (bird.getName().equals(birdName))
            {
                imageSearch=bird.getImageId();
            }
        }

        birdImage.setImageResource(imageSearch);
    }




}

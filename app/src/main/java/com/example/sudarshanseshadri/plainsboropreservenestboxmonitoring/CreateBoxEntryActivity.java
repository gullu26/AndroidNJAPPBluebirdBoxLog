package com.example.sudarshanseshadri.plainsboropreservenestboxmonitoring;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sudarshanseshadri.plainsboropreservenestboxmonitoring.MyObjects.Bird;
import com.example.sudarshanseshadri.plainsboropreservenestboxmonitoring.MyObjects.BirdList;

import java.util.ArrayList;

public class CreateBoxEntryActivity extends AppCompatActivity {
    EditText species;

    EditText boxNumber, nest, eggs, nestlings;
    Spinner activity;

    ImageView birdImage, searchIcon;

    int pos=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_box_entry);


        species = findViewById(R.id.id_editText_species);
        boxNumber = findViewById(R.id.id_editText_boxNumber);
        activity = findViewById(R.id.id_spinner_activity);
        nest = findViewById(R.id.id_editText_nest);
        eggs = findViewById(R.id.id_editText_eggs);
        nestlings=findViewById(R.id.id_editText_nestlings);


        birdImage = findViewById(R.id.id_imageView_birdPicCreate);
        searchIcon = findViewById(R.id.id_imageView_searchBar);

        setUpActivitySpinner();

        //populate the EditText if we are editing
        Bundle boxInfo = getIntent().getExtras();

        if (boxInfo.get("BOXNUM") != null) {
            try {
                boxNumber.setText(boxInfo.getInt("BOXNUM") + "");
            } catch (Exception e) {

            }
        }

        species.setText(boxInfo.get("SPECIES").toString());

        for (int i = 0 ; i<activity.getAdapter().getCount() ; i++)
        {
            if (activity.getItemAtPosition(i).toString().equals(boxInfo.get("ACTIVITY").toString()))
            {
                activity.setSelection(i);
                break;
            }
        }





        nest.setText(boxInfo.get("NEST").toString());
        eggs.setText(boxInfo.get("EGGS").toString());
        nestlings.setText(boxInfo.get("NESTLINGS").toString());
        pos = boxInfo.getInt("POS");


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


        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(CreateBoxEntryActivity.this, BirdSearchActivity.class);
//                startActivity(intent);


                //start for result of a string of the birds name

                Intent intent = new Intent(CreateBoxEntryActivity.this, BirdSearchActivity.class);

                startActivityForResult(intent, 2);

            }
        });

    }

    public void setUpActivitySpinner() {
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.activities, R.layout.support_simple_spinner_dropdown_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner

        activity.setAdapter(adapter);

        activity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        activity.setSelection(0);

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (2) : {
                if (resultCode == Activity.RESULT_OK) {
                    String species = data.getStringExtra("SPECIES");
                    this.species.setText(species, TextView.BufferType.EDITABLE);
                }
                break;
            }
        }
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


        if(boxNumber.getText().toString().equals(""))
        {
            Toast noBlanks = Toast.makeText(CreateBoxEntryActivity.this, "Enter a Box Number", Toast.LENGTH_SHORT);
            noBlanks.show();

        }

        else
        {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("BOXNUM", boxNumber.getText().toString());
            resultIntent.putExtra("POS", pos);

            if(species.getText().toString().equals(""))
            {
                resultIntent.putExtra("SPECIES", "None");
            }
            else
            {
                resultIntent.putExtra("SPECIES", species.getText().toString());
            }

            if(activity.getSelectedItem().toString().equals(""))
            {
                resultIntent.putExtra("ACTIVITY", "None");
            }
            else
            {
                resultIntent.putExtra("ACTIVITY", activity.getSelectedItem().toString());
            }

            if(nest.getText().toString().equals(""))
            {
                resultIntent.putExtra("NEST", "None");
            }
            else
            {
                resultIntent.putExtra("NEST", nest.getText().toString());
            }

            if(eggs.getText().equals(""))
            {
                resultIntent.putExtra("EGGS", "0");
            }
            else
            {
                resultIntent.putExtra("EGGS", eggs.getText().toString());
            }

            if(nestlings.getText().equals(""))
            {
                resultIntent.putExtra("NESTLINGS", "0");
            }
            else
            {
                resultIntent.putExtra("NESTLINGS", nestlings.getText().toString());
            }





            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        }

        return true;
    }


}

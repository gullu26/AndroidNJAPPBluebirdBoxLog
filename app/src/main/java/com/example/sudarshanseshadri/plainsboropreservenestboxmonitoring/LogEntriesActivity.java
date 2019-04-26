package com.example.sudarshanseshadri.plainsboropreservenestboxmonitoring;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.sudarshanseshadri.plainsboropreservenestboxmonitoring.MyObjects.AllUserLogData;
import com.example.sudarshanseshadri.plainsboropreservenestboxmonitoring.MyObjects.CompleteLogEntry;
import com.example.sudarshanseshadri.plainsboropreservenestboxmonitoring.MyObjects.LogBoxEntry;
import com.example.sudarshanseshadri.plainsboropreservenestboxmonitoring.RecyclerViewAdapters.RecyclerViewAdapterBoxEntries;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class LogEntriesActivity extends AppCompatActivity {

    CompleteLogEntry completeLogEntry;
    RecyclerViewAdapterBoxEntries adapter;
    private static final String TAG = "LogEntriesActivity";
    SharedPreferences mPrefs;
    private DocumentReference documentReference;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_entries);

        setLogEntries();
        initRecyclerView();



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.id_fab_addBoxEntry);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle extras = new Bundle();
                Intent intent = new Intent(LogEntriesActivity.this, CreateBoxEntryActivity.class);

                extras.putString("SPECIES", "");
                extras.putString("ACTIVITY", "");
                extras.putString("NEST", "");
                extras.putString("EGGS", "");
                extras.putString("NESTLINGS", "");
                extras.putInt("POS", 1);




                //create and initialize an intent
                Intent newEntryIntent = new Intent(LogEntriesActivity.this, CreateBoxEntryActivity.class);

                //attach the bundle to the Intent object
                newEntryIntent.putExtras(extras);

                startActivityForResult(newEntryIntent, 1);
            }
        });


        mPrefs=getSharedPreferences("PlainsboroPrefs", Context.MODE_PRIVATE);
        String userID = mPrefs.getString("userID", "");
        String path="users/" + userID;

        documentReference= FirebaseFirestore.getInstance().document(path);


        documentReference.collection("/1").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        completeLoad=true;
                        if (task.isSuccessful()) {
                            count = task.getResult().size()+1;


                        } else {
                        }
                    }
                });



    }

    int count=-1;
    boolean completeLoad=false;
    public void saveNewLogEntryToFirestore(){

        //get the user ID
        mPrefs=getSharedPreferences("PlainsboroPrefs", Context.MODE_PRIVATE);
        String userID = mPrefs.getString("userID", "");


        String path="users/" + userID;

        documentReference= FirebaseFirestore.getInstance().document(path);





        path="users/" + userID + "/1/" + count;

        documentReference= FirebaseFirestore.getInstance().document(path);

        //figure this part out later
        Map<String, Object> dataToSave = new HashMap<String, Object>();
        dataToSave.put("Date", completeLogEntry.getDate());
        dataToSave.put("Sun", completeLogEntry.getSunConditions());
        dataToSave.put("Wind", completeLogEntry.getWind());
        dataToSave.put("Temperature", completeLogEntry.getTempInFahrenheit());

        documentReference.set(dataToSave).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
            }
        });


        //set each box
        for (int i=0; i<completeLogEntry.getBoxEntries().size();i++) {
            LogBoxEntry currentEntry = completeLogEntry.getBoxEntries().get(i);
            path = "users/" + userID + "/1/" + count + "/Entries/" + (i+1);

            documentReference = FirebaseFirestore.getInstance().document(path);

            //figure this part out later
            dataToSave = new HashMap<String, Object>();
            dataToSave.put("Activity", currentEntry.getActivity());
            dataToSave.put("Box Number", currentEntry.getBoxNumber());
            dataToSave.put("Eggs", currentEntry.getEggs());
            dataToSave.put("Nestlings", currentEntry.getNestlings());
            dataToSave.put("Nest", currentEntry.getNest());
            dataToSave.put("Species", currentEntry.getSpecies());

            documentReference.set(dataToSave).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                }
            });
        }

    }

    private void setLogEntries(){
        Bundle loggingSessionData=getIntent().getExtras();
        completeLogEntry=
                new CompleteLogEntry(   loggingSessionData.get("DATE").toString(),    Integer.parseInt(loggingSessionData.get("TEMP").toString()),    loggingSessionData.get("SUN").toString(),    loggingSessionData.get("WIND").toString());



    }

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.id_recyclerView_boxEntries);
        adapter = new RecyclerViewAdapterBoxEntries(completeLogEntry.getBoxEntries(), LogEntriesActivity.this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(LogEntriesActivity.this));


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            //case 1 is for a completely new entry
            case (1) : {
                if (resultCode == Activity.RESULT_OK) {
                    String species = data.getStringExtra("SPECIES");
                    String activity = data.getStringExtra("ACTIVITY");
                    String nest = data.getStringExtra("NEST");
                    int eggs = 0;
                    int nestlings = 0;
                    if (!data.getStringExtra("EGGS").equals(""))
                    {
                         eggs = Integer.parseInt(data.getStringExtra("EGGS"));
                    }

                    if (!data.getStringExtra("NESTLINGS").equals(""))
                    {
                        nestlings = Integer.parseInt(data.getStringExtra("NESTLINGS"));
                    }

                    int boxNum = Integer.parseInt(data.getStringExtra("BOXNUM"));
                    addBoxEntry(boxNum, species, activity, nest, eggs, nestlings);

                }
                break;
            }
            case (2) : {
                if (resultCode == Activity.RESULT_OK) {
                    String species = data.getStringExtra("SPECIES");
                    String activity = data.getStringExtra("ACTIVITY");
                    String nest = data.getStringExtra("NEST");
                    int eggs = Integer.parseInt(data.getStringExtra("EGGS"));
                    int nestlings = Integer.parseInt(data.getStringExtra("NESTLINGS"));
                    int boxNum = Integer.parseInt(data.getStringExtra("BOXNUM"));
                    int pos=0;

                    pos = data.getIntExtra("POS", 0);

                    changeBoxEntry(pos, boxNum, species, activity, nest, eggs, nestlings);

                }
                break;
            }
        }
    }

    public void addBoxEntry(int boxNumber, String species, String activity, String nest, int eggs, int nestlings)
    {
        completeLogEntry.addBoxEntry(new LogBoxEntry(boxNumber, species, activity, nest, eggs, nestlings));
        adapter.notifyDataSetChanged();

    }

    public void changeBoxEntry(int i, int boxNumber, String species, String activity, String nest, int eggs, int nestlings)
    {
        completeLogEntry.changeBoxEntry(i, new LogBoxEntry(boxNumber, species, activity, nest, eggs, nestlings));
        adapter.notifyDataSetChanged();

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
        //first, ask if you really want to save everything
        if (completeLogEntry.getBoxEntries().size()==0)
        {
            Toast.makeText(this, "Add at least one box entry", Toast.LENGTH_SHORT).show();
        }
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Save entry")
                    .setMessage("Once you save, you cannot edit this entry.")
                    .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //save the CompleteLogEntry to Shared Preferences
                            //first get the current ArrayList of CompleteLogEntries, then add one to it, store it back.

                            //get the current list
                            //getCurrentList();

                            saveBirdsToSharedPrefs(getApplicationContext());
                            finish();
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing


                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();


            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Delete entry")
                .setMessage("Are you sure you want to leave without saving this entry?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing



                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }



    public AllUserLogData getCurrentData()
    {
        Gson gson = new Gson();
        String json = mPrefs.getString("allUserLogData", "");
        AllUserLogData allUserLogDataFromSharedPrefs= gson.fromJson(json, AllUserLogData.class);

        if(allUserLogDataFromSharedPrefs==null)
        {
            return (  new AllUserLogData()    );
        }
        return allUserLogDataFromSharedPrefs;
    }

    public void saveBirdsToSharedPrefs(Context context) {

        mPrefs=getSharedPreferences("PlainsboroPrefs", Context.MODE_PRIVATE);

        AllUserLogData allCurrentUserLogData = getCurrentData();


        allCurrentUserLogData.addCompleteLogEntry(completeLogEntry);


        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(allCurrentUserLogData, AllUserLogData.class);
        prefsEditor.putString("allUserLogData", json);
        prefsEditor.commit();

        String jsonGet = mPrefs.getString("allUserLogData", "");
        AllUserLogData allUserLogDataFromSharedPrefs= gson.fromJson(jsonGet, AllUserLogData.class);

        saveNewLogEntryToFirestore();

    }



}

package com.example.sudarshanseshadri.plainsboropreservenestboxmonitoring;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.sudarshanseshadri.plainsboropreservenestboxmonitoring.MyObjects.AllUserLogData;
import com.example.sudarshanseshadri.plainsboropreservenestboxmonitoring.MyObjects.CompleteLogEntry;
import com.example.sudarshanseshadri.plainsboropreservenestboxmonitoring.RecyclerViewAdapters.RecyclerViewAdapterBoxEntriesNoEdit;
import com.google.gson.Gson;

public class LogEntriesActivityNoEdit extends AppCompatActivity {

    CompleteLogEntry completeLogEntry;
    RecyclerViewAdapterBoxEntriesNoEdit adapter;
    private static final String TAG = "LogEntriesActivity";
    SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_entries_no_edit);

        mPrefs=getSharedPreferences("PlainsboroPrefs", Context.MODE_PRIVATE);

        Bundle loggingSessionData=getIntent().getExtras();
        int ln = Integer.parseInt(loggingSessionData.get("LOGNUM").toString());

        completeLogEntry = getCurrentData().getCompleteLogEntryArrayList().get(ln);
        initRecyclerView();




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



    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.id_recyclerView_boxEntries_no_edit);

        adapter = new RecyclerViewAdapterBoxEntriesNoEdit(completeLogEntry.getBoxEntries(),LogEntriesActivityNoEdit.this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(LogEntriesActivityNoEdit.this));


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









}

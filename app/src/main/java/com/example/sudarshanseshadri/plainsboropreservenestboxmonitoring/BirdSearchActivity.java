package com.example.sudarshanseshadri.plainsboropreservenestboxmonitoring;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.sudarshanseshadri.plainsboropreservenestboxmonitoring.RecyclerViewAdapters.RecyclerViewAdapterBirdSpecies;


public class BirdSearchActivity extends AppCompatActivity {

    private static final String TAG = "BirdSearch";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bird_search);

        initRecyclerView();
    }

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.id_recyclerView_birdSearch);
        RecyclerViewAdapterBirdSpecies adapter = new RecyclerViewAdapterBirdSpecies(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}

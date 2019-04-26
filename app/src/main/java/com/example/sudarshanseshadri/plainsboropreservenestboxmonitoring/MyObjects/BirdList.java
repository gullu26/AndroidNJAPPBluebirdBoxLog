package com.example.sudarshanseshadri.plainsboropreservenestboxmonitoring.MyObjects;

import com.example.sudarshanseshadri.plainsboropreservenestboxmonitoring.R;

import java.util.ArrayList;

public class BirdList {


    ArrayList<Bird> birdArrayList;

    public BirdList() {
        birdArrayList=new ArrayList<>();

        //eastern blue bird, chickadee, tree swallow, House Wren, house sparrow

        birdArrayList.add(new Bird("None", "No bird was in the box", R.drawable.generic_bird));
        birdArrayList.add(new Bird("Eastern Bluebird", "Orange belly & bright blue back", R.drawable.bluebird));
        birdArrayList.add(new Bird("Chickadee", "Black head and throat", R.drawable.chickadee));
        birdArrayList.add(new Bird("Tree Swallow", "White belly & green/blue back", R.drawable.tree_swallow));
        birdArrayList.add(new Bird("House Wren", "Small size & brown striping", R.drawable.house_wren));
        birdArrayList.add(new Bird("House Sparrow", "Male: Black throat, grey belly, brown back. \n Female: solid grey-brown", R.drawable.house_sparrow));

    }

    public ArrayList<Bird> getBirdArrayList() {
        return birdArrayList;
    }
}

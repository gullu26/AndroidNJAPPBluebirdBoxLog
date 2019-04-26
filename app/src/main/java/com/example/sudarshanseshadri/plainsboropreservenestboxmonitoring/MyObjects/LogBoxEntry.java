package com.example.sudarshanseshadri.plainsboropreservenestboxmonitoring.MyObjects;

public class LogBoxEntry {
    //used to represent a single box entry. Only one box. Not the whole day's entries, but just the single box, like what was inside the box.

    private int boxNumber;
    private String species;
    private String activity;
    private String nest;
    //nest material

    private int eggs;
    private int nestlings;
    //number of eggs/nestlings

    public LogBoxEntry() {
    }

    public LogBoxEntry(int boxNumber) {
        this.boxNumber = boxNumber;
        this.species = "None";
        this.activity = "None";
        this.nest = "None";
        this.eggs = 0;
        this.nestlings = 0;

    }

    public LogBoxEntry(int boxNumber, String species, String activity, String nest, int eggs, int nestlings) {
        this.boxNumber = boxNumber;
        this.species = species;
        this.activity = activity;
        this.nest = nest;
        this.eggs = eggs;
        this.nestlings = nestlings;
    }

    public int getBoxNumber() {
        return boxNumber;
    }

    public String getSpecies() {
        return species;
    }

    public String getActivity() {
        return activity;
    }

    public String getNest() {
        return nest;
    }


    public int getEggs() {
        return eggs;
    }

    public int getNestlings() {
        return nestlings;
    }

    public void setBoxNumber(int boxNumber) {
        this.boxNumber = boxNumber;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public void setNest(String nest) {
        this.nest = nest;
    }

    public void setEggs(int eggs) {
        this.eggs = eggs;
    }

    public void setNestlings(int nestlings) {
        this.nestlings = nestlings;
    }
}

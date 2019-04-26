package com.example.sudarshanseshadri.plainsboropreservenestboxmonitoring.MyObjects;

import java.util.ArrayList;

public class CompleteLogEntry {
    //used to represent a whole day of logging! Not just one box, all the boxes in a session. Contains a bunch of single box entries.

    private ArrayList<LogBoxEntry> boxEntries = new ArrayList<>();
    private String date;
    private int tempInFahrenheit;
    private String sunConditions;
    private String wind;


    public CompleteLogEntry(ArrayList<LogBoxEntry> boxEntries, String date, int tempInFahrenheit, String sunConditions, String wind) {
        this.boxEntries = boxEntries;
        this.date = date;
        this.tempInFahrenheit = tempInFahrenheit;
        this.sunConditions = sunConditions;
        this.wind = wind;
    }

    public CompleteLogEntry(String date, int tempInFahrenheit, String sunConditions, String wind) {
        this.date = date;
        this.tempInFahrenheit = tempInFahrenheit;
        this.sunConditions = sunConditions;
        this.wind = wind;
    }

    public CompleteLogEntry() {

    }

    public ArrayList<LogBoxEntry> getBoxEntries() {
        return boxEntries;
    }

    public String getDate() {
        return date;
    }

    public int getTempInFahrenheit() {
        return tempInFahrenheit;
    }

    public String getSunConditions() {
        return sunConditions;
    }

    public String getWind() {
        return wind;
    }

    public void setBoxEntries(ArrayList<LogBoxEntry> boxEntries) {
        this.boxEntries = boxEntries;
    }

    public void addBoxEntry(LogBoxEntry boxEntry) {
        boxEntries.add(boxEntry);
    }

    public void addBoxEntry(int i, LogBoxEntry boxEntry) {
        boxEntries.add(i, boxEntry);
    }

    public void changeBoxEntry(int i, LogBoxEntry boxEntry) {
        boxEntries.set(i, boxEntry);
    }
}

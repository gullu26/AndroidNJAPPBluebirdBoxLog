package com.example.sudarshanseshadri.plainsboropreservenestboxmonitoring.MyObjects;

import java.util.ArrayList;

public class AllUserLogData {
    ArrayList<CompleteLogEntry> completeLogEntryArrayList;

    public AllUserLogData(ArrayList<CompleteLogEntry> completeLogEntryArrayList) {
        this.completeLogEntryArrayList = completeLogEntryArrayList;
    }

    public AllUserLogData() {
        completeLogEntryArrayList=new ArrayList<CompleteLogEntry>();
    }

    public ArrayList<CompleteLogEntry> getCompleteLogEntryArrayList() {
        // Arraylist for storing reversed elements
        ArrayList<CompleteLogEntry> revArrayList = new ArrayList<>();
        for (int i = completeLogEntryArrayList.size() - 1; i >= 0; i--) {

            // Append the elements in reverse order
            revArrayList.add(completeLogEntryArrayList.get(i));
        }

        // Return the reversed arraylist
        return revArrayList;

        //return completeLogEntryArrayList;
    }


    public void addCompleteLogEntry(CompleteLogEntry c)
    {
        completeLogEntryArrayList.add(c);
    }
}

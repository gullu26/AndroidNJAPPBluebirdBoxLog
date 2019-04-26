package com.example.sudarshanseshadri.plainsboropreservenestboxmonitoring.MyObjects;

public class Bird {
    String name;
    String description;
    int imageId;

    public Bird(String name, String description, int imageId) {
        this.name = name;
        this.description = description;
        this.imageId = imageId;
    }




    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getImageId() {
        return imageId;
    }
}

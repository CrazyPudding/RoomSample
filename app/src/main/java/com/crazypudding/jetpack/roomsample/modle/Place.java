package com.crazypudding.jetpack.roomsample.modle;

public class Place {
    private String description;

    public String getDescription() {
        return description == null ? "" : description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

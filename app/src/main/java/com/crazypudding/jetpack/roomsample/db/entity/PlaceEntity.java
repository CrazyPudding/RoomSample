package com.crazypudding.jetpack.roomsample.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "place")
public class PlaceEntity {
    @PrimaryKey
    private long id;
    private String description;

    public PlaceEntity() {
    }

    @Ignore
    public PlaceEntity(long id, String description) {
        this.id = id;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description == null ? "" : description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

package com.crazypudding.jetpack.roomsample.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.crazypudding.jetpack.roomsample.db.converter.DateConverter;

import java.util.Date;

@Entity(tableName = "products",
        foreignKeys = {@ForeignKey(entity = PlaceEntity.class,
                parentColumns = "id",
                childColumns = "place_id")})
@TypeConverters(DateConverter.class)
public class ProductEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    @ColumnInfo(name = "purchase_date")
    private Date purchaseDate;
    @ColumnInfo(name = "place_id")
    private int placeId;
    private long price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public int getPlaceId() {
        return placeId;
    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }
}

package com.crazypudding.jetpack.roomsample.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
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
    private double price;
    @ColumnInfo(name = "purchase_date")
    private Date purchaseDate;
    @ColumnInfo(name = "place_id")
    private long placeId;

    public ProductEntity() {
    }

    @Ignore
    public ProductEntity(String name, double price, Date purchaseDate, long placeId) {
        this.name = name;
        this.purchaseDate = purchaseDate;
        this.placeId = placeId;
        this.price = price;
    }

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

    public long getPlaceId() {
        return placeId;
    }

    public void setPlaceId(long placeId) {
        this.placeId = placeId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

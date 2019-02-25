package com.crazypudding.jetpack.roomsample.modle;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.TypeConverters;

import com.crazypudding.jetpack.roomsample.db.converter.DateConverter;

import java.util.Date;

@TypeConverters(DateConverter.class)
public class Product {
    @ColumnInfo(name = "name")
    private String productName;
    @ColumnInfo(name = "price")
    private double productPrice;
    @ColumnInfo(name = "purchase_date")
    private Date purchaseDate;

    public String getProductName() {
        return productName == null ? "" : productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
}

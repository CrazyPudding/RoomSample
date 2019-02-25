package com.crazypudding.jetpack.roomsample.modle;

import android.arch.persistence.room.Embedded;

public class ProductWithPlace {
    @Embedded
    private Product product;

    @Embedded
    private Place place;

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public Product getProduct() {
        return product;
    }

    public Place getPlace() {
        return place;
    }
}

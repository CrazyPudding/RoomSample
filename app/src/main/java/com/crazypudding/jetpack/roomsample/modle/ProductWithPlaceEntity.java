package com.crazypudding.jetpack.roomsample.modle;

import android.arch.persistence.room.Embedded;

import com.crazypudding.jetpack.roomsample.db.entity.ProductEntity;

public class ProductWithPlaceEntity {
    @Embedded
    private Place place;

    @Embedded
    private ProductEntity product;

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }
}

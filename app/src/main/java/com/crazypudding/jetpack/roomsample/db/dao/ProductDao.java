package com.crazypudding.jetpack.roomsample.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.crazypudding.jetpack.roomsample.db.entity.ProductEntity;
import com.crazypudding.jetpack.roomsample.modle.ProductWithPlace;

import java.util.List;

@Dao
public interface ProductDao extends BaseDao<ProductEntity> {

    @Query("select name, price, purchase_date, description from products join place on products.place_id = place.id")
    public List<ProductWithPlace> getProductsWithPlace();
}

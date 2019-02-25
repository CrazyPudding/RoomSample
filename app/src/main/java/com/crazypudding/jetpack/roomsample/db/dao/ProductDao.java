package com.crazypudding.jetpack.roomsample.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.crazypudding.jetpack.roomsample.db.entity.ProductEntity;
import com.crazypudding.jetpack.roomsample.modle.ProductWithPlace;
import com.crazypudding.jetpack.roomsample.modle.ProductWithPlaceEntity;

import java.util.List;

@Dao
public interface ProductDao extends BaseDao<ProductEntity> {

    @Query("select products.id, name, price, purchase_date, description from products join place on products.place_id = place.id")
    public List<ProductWithPlace> getProductsWithPlace();

    @Query("select products.id, name, price, purchase_date, place_id, description from products join place on products.place_id = place.id where products.id = :productId")
    public ProductWithPlaceEntity getProductWithPlaceById(int productId);

    @Update
    public int updateProduct(ProductEntity productEntity);
}

package com.crazypudding.jetpack.roomsample.db.dao;

import android.arch.persistence.room.Dao;

import com.crazypudding.jetpack.roomsample.db.entity.ProductEntity;

@Dao
public interface ProductDao extends BaseDao<ProductEntity> {

}

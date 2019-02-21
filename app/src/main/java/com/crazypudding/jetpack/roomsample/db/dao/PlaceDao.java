package com.crazypudding.jetpack.roomsample.db.dao;

import android.arch.persistence.room.Dao;

import com.crazypudding.jetpack.roomsample.db.entity.PlaceEntity;

@Dao
public interface PlaceDao extends BaseDao<PlaceEntity> {
}

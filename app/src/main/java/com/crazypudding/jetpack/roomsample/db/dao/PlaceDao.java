package com.crazypudding.jetpack.roomsample.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Update;

import com.crazypudding.jetpack.roomsample.db.entity.PlaceEntity;

@Dao
public interface PlaceDao extends BaseDao<PlaceEntity> {
    @Update
    public int updatePlace(PlaceEntity place);
}

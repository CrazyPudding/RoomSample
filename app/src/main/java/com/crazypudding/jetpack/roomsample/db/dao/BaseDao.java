package com.crazypudding.jetpack.roomsample.db.dao;

import android.arch.persistence.room.Insert;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

public interface BaseDao<T> {
    @Insert(onConflict = REPLACE)
    public void insertRecord(T record);
}

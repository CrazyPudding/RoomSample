package com.crazypudding.jetpack.roomsample.db.dao;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

public interface BaseDao<T> {
    @Insert(onConflict = REPLACE)
    public long insertRecord(T record);

    @Delete
    public int deleteRecord(T record);
}

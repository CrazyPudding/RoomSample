package com.crazypudding.jetpack.roomsample.db.dao;

import android.arch.persistence.room.Insert;

public interface BaseDao<T> {
    @Insert
    public void insertRecord(T record);
}

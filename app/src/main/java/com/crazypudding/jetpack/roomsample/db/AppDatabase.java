package com.crazypudding.jetpack.roomsample.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.crazypudding.jetpack.roomsample.db.dao.PlaceDao;
import com.crazypudding.jetpack.roomsample.db.dao.ProductDao;
import com.crazypudding.jetpack.roomsample.db.entity.PlaceEntity;
import com.crazypudding.jetpack.roomsample.db.entity.ProductEntity;

@Database(entities = {ProductEntity.class, PlaceEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase sInstance;

    public abstract ProductDao productDao();

    public abstract PlaceDao placeDao();

    public static AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = buildDatabase(context);
                }
            }
        }
        return sInstance;
    }

    private static AppDatabase buildDatabase(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, "res.db")
                .build();
    }
}

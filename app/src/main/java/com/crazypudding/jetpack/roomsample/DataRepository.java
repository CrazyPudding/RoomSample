package com.crazypudding.jetpack.roomsample;

import android.arch.persistence.room.Transaction;
import android.content.Context;

import com.crazypudding.jetpack.roomsample.db.AppDatabase;
import com.crazypudding.jetpack.roomsample.db.entity.PlaceEntity;
import com.crazypudding.jetpack.roomsample.db.entity.ProductEntity;

/**
 * 统一处理数据
 */
public class DataRepository {

    private static DataRepository sInstance;
    private AppDatabase mDb;
    private AppExecutors appExecutors;

    private DataRepository(Context context) {
        mDb = AppDatabase.getInstance(context);
        appExecutors = new AppExecutors();
    }

    public static DataRepository getInstance(Context context) {
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    sInstance = new DataRepository(context);
                }
            }
        }
        return sInstance;
    }

    @Transaction
    public void saveProductAndPlace(final PlaceEntity place, final ProductEntity product) {
        savePlace(place);
        saveProduct(product);
    }

    public void saveProduct(final ProductEntity product) {
        appExecutors.getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDb.productDao().insertRecord(product);
            }
        });
    }

    public void savePlace(final PlaceEntity place) {
        appExecutors.getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDb.placeDao().insertRecord(place);
            }
        });
    }
}

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
    public void saveProductAndPlace(final PlaceEntity place, final ProductEntity product, ActionCallback<Long> callback) {
        savePlace(place);
        saveProduct(product, callback);
    }

    public void saveProduct(final ProductEntity product, final ActionCallback<Long> callback) {
        appExecutors.getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                final long rowId = mDb.productDao().insertRecord(product);
                appExecutors.getMainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onActionDone(rowId);
                    }
                });
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

    public void deleteProduct(final ProductEntity product) {

    }
}

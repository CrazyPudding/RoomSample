package com.crazypudding.jetpack.roomsample;

import android.arch.persistence.room.Transaction;
import android.content.Context;

import com.crazypudding.jetpack.roomsample.db.AppDatabase;
import com.crazypudding.jetpack.roomsample.db.entity.PlaceEntity;
import com.crazypudding.jetpack.roomsample.db.entity.ProductEntity;
import com.crazypudding.jetpack.roomsample.modle.ProductWithPlace;
import com.crazypudding.jetpack.roomsample.modle.ProductWithPlaceEntity;

import java.util.List;

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
        appExecutors.getDiskIO().execute(() -> {
            final long rowId = mDb.productDao().insertRecord(product);
            appExecutors.getMainThread().execute(() -> {
                if (callback != null) {
                    callback.onActionDone(rowId);
                }
            });
        });
    }

    public void savePlace(final PlaceEntity place) {
        appExecutors.getDiskIO().execute(() -> mDb.placeDao().insertRecord(place));
    }

    public void getProductsWithPlace(final GetDatasCallback<List<ProductWithPlace>> callback) {
        appExecutors.getDiskIO().execute(() -> {
            final List<ProductWithPlace> datas = mDb.productDao().getProductsWithPlace();
            appExecutors.getMainThread().execute(() -> {
                if (callback == null) {
                    return;
                }
                if (datas != null && datas.size() > 0) {
                    callback.onDataLoaded(datas);
                } else {
                    callback.onDataNotAvailable();
                }
            });
        });
    }

    public void getProductWithPlaceById(final int productId, final GetDatasCallback<ProductWithPlaceEntity> callback) {
        appExecutors.getDiskIO().execute(() -> {
            final ProductWithPlaceEntity data = mDb.productDao().getProductWithPlaceById(productId);
            appExecutors.getMainThread().execute(() -> {
                if (callback == null) {
                    return;
                }
                if (data != null) {
                    callback.onDataLoaded(data);
                } else {
                    callback.onDataNotAvailable();
                }
            });
        });
    }

    public void updateProduct(final ProductEntity product, final ActionCallback<Integer> callback) {
        appExecutors.getDiskIO().execute(() -> {
            final int updatedId = mDb.productDao().updateProduct(product);
            if (callback != null) {
                callback.onActionDone(updatedId);
            }
        });
    }

    public void updatePlace(final PlaceEntity place, final ActionCallback<Integer> callback) {
        appExecutors.getDiskIO().execute(() -> {
            final int updateId = mDb.placeDao().updatePlace(place);
            if (callback != null) {
                callback.onActionDone(updateId);
            }
        });
    }

    public void deleteProduct(final ProductEntity product, final ActionCallback<Integer> callback) {
        appExecutors.getDiskIO().execute(() -> {
            final int delId = mDb.productDao().deleteRecord(product);
            appExecutors.getMainThread().execute(() -> {
                if (callback != null) {
                    callback.onActionDone(delId);
                }
            });
        });
    }
}

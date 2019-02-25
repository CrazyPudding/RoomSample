package com.crazypudding.jetpack.roomsample;

public interface GetDatasCallback<T> {
    void onDataLoaded(T datas);

    void onDataNotAvailable();
}

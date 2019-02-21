package com.crazypudding.jetpack.roomsample.db.converter;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

public class DateConverter {

    @TypeConverter
    public Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}

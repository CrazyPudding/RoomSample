package com.crazypudding.jetpack.roomsample.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 时间工具类，用于转换时间格式
 */
public class DateUtil {

    /**
     * 将 Date 转换为 "yyyy-MM-dd" 格式
     * @param date 目标 Date
     * @return 转换结果
     */
    public static String toString(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return df.format(date);
    }
}

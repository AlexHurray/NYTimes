package com.example.ermolaenkoalex.NYTimes.utils;

import android.content.Context;
import android.text.format.DateUtils;

import java.util.Date;

import androidx.annotation.NonNull;

import static android.text.format.DateUtils.FORMAT_ABBREV_MONTH;
import static android.text.format.DateUtils.FORMAT_ABBREV_RELATIVE;
import static android.text.format.DateUtils.FORMAT_ABBREV_TIME;
import static android.text.format.DateUtils.FORMAT_SHOW_DATE;
import static android.text.format.DateUtils.MINUTE_IN_MILLIS;
import static android.text.format.DateUtils.WEEK_IN_MILLIS;

public class StringUtils {
    public static String formatDate(@NonNull Context context, @NonNull Date date) {
        int flags = FORMAT_ABBREV_RELATIVE |
                FORMAT_SHOW_DATE | FORMAT_ABBREV_TIME | FORMAT_ABBREV_MONTH;

        return DateUtils.getRelativeDateTimeString(context, date.getTime(),
                MINUTE_IN_MILLIS, WEEK_IN_MILLIS, flags).toString();
    }
}

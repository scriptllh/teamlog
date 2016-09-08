package com.wiseach.teamlog.utils;

import com.wiseach.teamlog.Constants;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

/**
 * User: Arlen Tan
 * 12-8-15 上午11:34
 */
public class DateUtils {
    public static final DateTimeFormatter datetimeFormatter = DateTimeFormat.forPattern(Constants.DATETIME_FORMAT);
    public static final DateTimeFormatter dateFormatter = DateTimeFormat.forPattern(Constants.DATE_FORMAT);

    public static Long getDurationToMidNight() {
        DateTime end = new DateTime().plusDays(1);

        return new Duration(new DateTime(),end.toDateMidnight().toDateTime()).getStandardSeconds();
    }

    public static Date parseDate(String date) {
        return DateTime.parse(date, datetimeFormatter).toDate();
    }

    public static String formatDateTime(Date date) {
        return new DateTime(date).toString(datetimeFormatter);
    }

    public static String formatDate(Date date) {
        return new DateTime(date).toString(dateFormatter);
    }
}

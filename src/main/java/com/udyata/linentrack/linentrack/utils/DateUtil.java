package com.udyata.linentrack.linentrack.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    /**
     * Sets the time of the given date to the end of the day (23:59:59.999).
     * @param date the date to adjust
     * @return a new Date object set to the end of the day of the original date
     */
    public static Date toEndOfDay(Date date) {
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }

    /**
     * Formats a given date to a string with the pattern "dd-MM-yyyy".
     *
     * @param date The date to be formatted.
     * @return A string representation of the date in "dd-MM-yyyy" format, or null if the date is null.
     */
    public static String formatDateToString(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(date);
    }

    /**
     * Returns a date that is 30 days before the current date.
     *
     * @return Date representing 30 days ago.
     */
    public static Date getDate30DaysAgo() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -30);
        return calendar.getTime();
    }
}

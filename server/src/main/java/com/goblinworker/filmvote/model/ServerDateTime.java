package com.goblinworker.filmvote.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Object that handles date / time conversions.
 */
public class ServerDateTime {

    /**
     * Date format used by the server.
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    /**
     * Time format used by the server.
     */
    public static final String TIME_FORMAT = "HH:mm:ss";

    /**
     * Time zone used by the server.
     */
    public static final TimeZone TIME_ZONE = TimeZone.getTimeZone("GMT");

    /**
     * Locale used by the server.
     */
    public static final Locale LOCALE = Locale.US;

    /**
     * Get format to parse date strings.
     *
     * @return DateFormat
     */
    public static DateFormat getDateFormat() {

        DateFormat format = new SimpleDateFormat(DATE_FORMAT, LOCALE);
        format.setTimeZone(TIME_ZONE);

        return format;
    }

    /**
     * Get format to parse time strings.
     *
     * @return DateFormat
     */
    public static DateFormat getTimeFormat() {

        DateFormat format = new SimpleDateFormat(TIME_FORMAT, LOCALE);
        format.setTimeZone(TIME_ZONE);

        return format;
    }

    /**
     * Get format to parse timestamps.
     *
     * @return DateFormat
     */
    public static DateFormat getDateTimeFormat() {

        String pattern = DATE_FORMAT + "'T'" + TIME_FORMAT + "X";

        DateFormat format = new SimpleDateFormat(pattern, LOCALE);
        format.setTimeZone(TIME_ZONE);

        return format;
    }

}

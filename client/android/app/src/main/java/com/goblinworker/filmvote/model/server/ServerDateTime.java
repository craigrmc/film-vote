package com.goblinworker.filmvote.model.server;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Object that handles date / time understood by server.
 */
public class ServerDateTime {

    /**
     * Date Format used by the server.
     */
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    /**
     * Time Format used by the server.
     */
    private static final String TIME_FORMAT = "HH:mm:ss";

    /**
     * Time Zone used by the server.
     */
    private static final TimeZone TIME_ZONE = TimeZone.getTimeZone("GMT");

    /**
     * Locale used by the server.
     */
    private static final Locale LOCALE = Locale.US;

    /**
     * Get date format used by the server.
     *
     * @return DateFormat
     */
    public static DateFormat getDateFormat() {

        DateFormat serverFormat = new SimpleDateFormat(DATE_FORMAT, LOCALE);
        serverFormat.setTimeZone(TIME_ZONE);

        return serverFormat;
    }

    /**
     * Get time format used by the server.
     *
     * @return DateFormat
     */
    public static DateFormat getTimeFormat() {

        DateFormat serverFormat = new SimpleDateFormat(TIME_FORMAT, LOCALE);
        serverFormat.setTimeZone(TIME_ZONE);

        return serverFormat;
    }

    /**
     * Get timestamp format used by the server.
     *
     * @return DateFormat
     */
    public static DateFormat getDateTimeFormat() {

        String pattern = DATE_FORMAT + "'T'" + TIME_FORMAT + "X";

        DateFormat serverFormat = new SimpleDateFormat(pattern, LOCALE);
        serverFormat.setTimeZone(TIME_ZONE);

        return serverFormat;
    }

    private final String serverDate;
    private final String serverTime;

    /**
     * Constructor to create server date time at current system time.
     */
    public ServerDateTime() {
        this(System.currentTimeMillis());
    }

    /**
     * Constructor to create server date time at given timestamp.
     *
     * @param timestamp Long milliseconds from epoch
     */
    public ServerDateTime(Long timestamp) {

        Date myDate = new Date(timestamp);

        this.serverDate = getDateFormat().format(myDate);
        this.serverTime = getTimeFormat().format(myDate);
    }

    /**
     * Constructor to create server date time at given time.
     *
     * @param date String Server Date yyyy-MM-dd
     * @param time String Server Time HH:mm:ss
     */
    public ServerDateTime(String date, String time) {
        this.serverDate = date;
        this.serverTime = time;
    }

    /**
     * Get the timestamp in standard ISO 8601 format.
     * <p>
     * Example: 2018-10-18T12:15:00+00:00
     *
     * @return String
     */
    public String getTimeStamp() {

        if (serverDate == null || serverTime == null) {
            return null;
        }

        return serverDate + "T" + serverTime + "+00:00";
    }

    /**
     * Get the date in app timezone / locale.
     * <p>
     * Example: 2018-10-17
     *
     * @return String
     */
    public String getClientDate() {

        Date serverDate;
        try {
            serverDate = getJavaDate();
        } catch (Exception e) {
            serverDate = null;
        }

        if (serverDate == null) {
            return null;
        }

        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());

        // TODO: remove time hack?
        if (serverTime == null) {
            dateFormat.setTimeZone(TIME_ZONE);
        } else {
            dateFormat.setTimeZone(TimeZone.getDefault());
        }

        return dateFormat.format(serverDate);
    }

    /**
     * Get the time in app timezone / locale.
     * <p>
     * Example: 19:15:00
     *
     * @return String
     */
    public String getClientTime() {

        if (serverTime == null) {
            return null;
        }

        Date serverDate;
        try {
            serverDate = getJavaDate();
        } catch (Exception e) {
            serverDate = null;
        }

        if (serverDate == null) {
            return null;
        }

        DateFormat timeFormat = new SimpleDateFormat(TIME_FORMAT, Locale.getDefault());
        timeFormat.setTimeZone(TimeZone.getDefault());

        return timeFormat.format(serverDate);
    }

    /**
     * Get the day of week in app timezone / locale.
     * <p>
     * Example: Wednesday
     *
     * @return String
     */
    public String getDisplayDate() {

        Date serverDate;
        try {
            serverDate = getJavaDate();
        } catch (Exception e) {
            serverDate = null;
        }

        if (serverDate == null) {
            return null;
        }

        DateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.getDefault());

        // TODO: remove time hack?
        if (serverTime == null) {
            dayFormat.setTimeZone(TIME_ZONE);
        } else {
            dayFormat.setTimeZone(TimeZone.getDefault());
        }

        return dayFormat.format(serverDate);
    }

    /**
     * Get the friendly time in app timezone / locale.
     * <p>
     * Example: 7:15 PM
     *
     * @return String
     */
    public String getDisplayTime() {

        if (serverTime == null) {
            return null;
        }

        Date serverDate;
        try {
            serverDate = getJavaDate();
        } catch (Exception e) {
            serverDate = null;
        }

        if (serverDate == null) {
            return null;
        }

        DateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        timeFormat.setTimeZone(TimeZone.getDefault());

        return timeFormat.format(serverDate);
    }

    /**
     * Get Date object from string date / time.
     * <p>
     * Date must be set correctly.
     * Time will be ignored if null.
     *
     * @return Date
     * @throws NullPointerException when date string is null
     * @throws ParseException       when date / time strings not formatted correctly
     */
    Date getJavaDate() throws NullPointerException, ParseException {

        String timeStamp = getTimeStamp();

        if (timeStamp == null || timeStamp.isEmpty()) {
            return getDateFormat().parse(serverDate);
        }

        return getDateTimeFormat().parse(timeStamp);
    }

    // Getter / Setter

    /**
     * Get the date in server timezone / locale.
     * <p>
     * Example: 2018-10-18
     *
     * @return String
     */
    public String getServerDate() {
        return serverDate;
    }

    /**
     * Get the time in server timezone / locale.
     * <p>
     * Example: 00:15:00
     *
     * @return String
     */
    public String getServerTime() {
        return serverTime;
    }

}

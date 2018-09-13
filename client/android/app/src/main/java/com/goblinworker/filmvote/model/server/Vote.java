package com.goblinworker.filmvote.model.server;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Object that holds a vote for when and where to watch a film.
 */
public class Vote {

    /**
     * Date Format from server.
     */
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    /**
     * Time Format from server.
     */
    private static final String TIME_FORMAT = "HH:mm:ss";

    /**
     * Time Zone from server.
     */
    private static final String TIME_ZONE = "GMT";

    private Integer tally;
    private String date;
    private String time;
    private String film;
    private String theater;

    /**
     * Constructor used to create a vote.
     */
    public Vote() {
        this.tally = 1;
    }

    /**
     * Get the day of week using new android API.
     *
     * @return String Day (example Monday)
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getDay() {

        if (date == null) {
            return null;
        }

        DateTimeFormatter serverDateFormat = DateTimeFormatter
                .ofPattern(DATE_FORMAT, Locale.US)
                .withZone(ZoneId.of(TIME_ZONE));

        LocalDate localDate = LocalDate.parse(date, serverDateFormat);

        DateTimeFormatter clientDateFormat = DateTimeFormatter
                .ofPattern("EEEE", Locale.getDefault())
                .withZone(ZoneId.systemDefault());

        return clientDateFormat.format(localDate);
    }

    /**
     * Get the day of week using old android API.
     *
     * @return String Day (example Monday)
     */
    public String getDayLegacy() {

        if (date == null) {
            return null;
        }

        DateFormat serverDateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.US);
        serverDateFormat.setTimeZone(TimeZone.getTimeZone(TIME_ZONE));

        Date serverDate;
        try {
            serverDate = serverDateFormat.parse(date);
        } catch (ParseException e) {
            serverDate = null;
        }

        DateFormat clientDateFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
        clientDateFormat.setTimeZone(TimeZone.getDefault());

        return clientDateFormat.format(serverDate);
    }

    /**
     * Get the time using new android API.
     *
     * @return String
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getLocalTime() {

        if (time == null) {
            return null;
        }

        DateTimeFormatter serverTimeFormat = DateTimeFormatter
                .ofPattern(TIME_FORMAT, Locale.US)
                .withZone(ZoneId.of(TIME_ZONE));

        LocalTime localTime = LocalTime.parse(time, serverTimeFormat);

        DateTimeFormatter clientTimeFormat = DateTimeFormatter
                .ofPattern("HH:mm", Locale.getDefault())
                .withZone(ZoneId.systemDefault());

        return clientTimeFormat.format(localTime);
    }

    /**
     * Get the time using old android API.
     *
     * @return String
     */
    public String getLocalTimeLegacy() {

        if (time == null) {
            return null;
        }

        DateFormat serverTimeFormat = new SimpleDateFormat(TIME_FORMAT, Locale.US);
        serverTimeFormat.setTimeZone(TimeZone.getTimeZone(TIME_ZONE));

        Date serverDate;
        try {
            serverDate = serverTimeFormat.parse(time);
        } catch (ParseException e) {
            serverDate = null;
        }

        DateFormat clientTimeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        clientTimeFormat.setTimeZone(TimeZone.getDefault());

        return clientTimeFormat.format(serverDate);
    }

    // Getter / Setter

    public Integer getTally() {
        return tally;
    }

    public void setTally(Integer tally) {
        this.tally = tally;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFilm() {
        return film;
    }

    public void setFilm(String film) {
        this.film = film;
    }

    public String getTheater() {
        return theater;
    }

    public void setTheater(String theater) {
        this.theater = theater;
    }

}

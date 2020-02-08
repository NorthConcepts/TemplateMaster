package com.northconcepts.templatemaster.timezone;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;

public class FormattedTimeZone {

    private static final long hour = TimeUnit.HOURS.toMillis(1);
    private static final long minute = TimeUnit.MINUTES.toMillis(1);

    public static final FormattedTimeZone[] get() {
        return get(new Date());
    }

    public static final FormattedTimeZone[] get(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM d, yyyy h:mm a");

        String[] ids = TimeZone.getAvailableIDs();
        TimeZone[] zones = new TimeZone[ids.length];

        for (int i = 0; i < ids.length; i++) {
            zones[i] = TimeZone.getTimeZone(ids[i]);
        }

        Arrays.sort(zones, new TimeZoneComparator(date));

        FormattedTimeZone[] formattedTimeZones = new FormattedTimeZone[ids.length];

        for (int i = 0; i < zones.length; i++) {
            TimeZone zone = zones[i];
            int offset = zone.getOffset(date.getTime());
            boolean daylightSavingsTime = zone.inDaylightTime(date);

            String longName = zone.getDisplayName(daylightSavingsTime, TimeZone.LONG);
            String shortName = zone.getDisplayName(daylightSavingsTime, TimeZone.SHORT);

            dateFormat.setTimeZone(zone);
            String dateString = dateFormat.format(date);
            long hours = offset / hour;
            long minutes = offset % hour / minute;
            String offsetString = (offset >= 0 ? "+" : "") + hours + ":" + StringUtils.rightPad(String.valueOf(Math.abs(minutes)), 2, '0');

            String formattedTimezone = dateString + " - " + longName + " (" + shortName + ") - " + zone.getID() + " (UTC" + offsetString + ") ";
            formattedTimeZones[i] = new FormattedTimeZone(zone, formattedTimezone);
        }

        return formattedTimeZones;
    }

    private final TimeZone timeZone;
    private final String displayString;

    private FormattedTimeZone(TimeZone timeZone, String displayString) {
        this.timeZone = timeZone;
        this.displayString = displayString;
    }

    public TimeZone getTimeZone() {
        return timeZone;
    }

    public String getDisplayString() {
        return displayString;
    }

    private static class TimeZoneComparator implements Comparator<TimeZone> {

        private final Date date;

        public TimeZoneComparator(Date date) {
            this.date = date;
        }

        @Override
        public int compare(TimeZone o1, TimeZone o2) {
            if (o1 == o2) {
                return 0;
            } else if (o1 == null) {
                return -1;
            } else if (o2 == null) {
                return 1;
            } else {
                int offset1 = o1.getOffset(date.getTime());
                int offset2 = o2.getOffset(date.getTime());

                if (offset1 < offset2) {
                    return -1;
                } else if (offset1 > offset2) {
                    return 1;
                }

                return o1.getID().compareTo(o2.getID());
            }
        }

    }

}

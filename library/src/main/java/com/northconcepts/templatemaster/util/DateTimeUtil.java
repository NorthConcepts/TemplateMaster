package com.northconcepts.templatemaster.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.northconcepts.templatemaster.content.TemplateMasterException;
import com.northconcepts.templatemaster.content.Util;

public final class DateTimeUtil {

    public static final DateTimeFormatter DATE_FORMAT_MM_DD_YYYY_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    public static final DateTimeFormatter DATE_FORMAT_YYYY_MM_DD_HH_MM_SS_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter DATE_FORMAT_YYYY_MM_DD_HH_MM_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static final DateTimeFormatter DATE_FORMAT_YYYY_MM_DD_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter DATE_FORMAT_YYYY_MM_DD_T_HH_MM_SS_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    public static final DateTimeFormatter DATE_FORMAT_YYYY_MM_DD_T_HH_MM_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    public static final DateTimeFormatter DATE_FORMAT_EEE_MMM_D_YYYY = DateTimeFormatter.ofPattern("EEE MMM d, yyyy");
    public static final DateTimeFormatter TIME_FORMAT_H_MM_A = DateTimeFormatter.ofPattern("h:mm a");
    public static final DateTimeFormatter DATE_FORMAT_EEE_MMM_D_YYYY_H_MM_A = DateTimeFormatter.ofPattern("EEE MMM d, yyyy h:mm a");

    private final static Logger log = LogManager.getLogger(DateTimeUtil.class);

    private DateTimeUtil() {

    }

    public static LocalDateTime getDate(DateTimeFormatter dateTimeFormatter, String date) {
        return LocalDateTime.parse(date, dateTimeFormatter);
    }

    public static Date toDate(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date toDate(Instant instant) {
        if (instant == null) {
            return null;
        }
        return Date.from(instant);
    }

    public static Timestamp newTimestamp() {
        return newTimestamp(System.currentTimeMillis());
    }

    public static Timestamp newTimestamp(long milliseconds) {
        return new Timestamp(milliseconds / 1000L * 1000L);
    }

    public static enum DateFormat {

        MMDDYYYY("MM/dd/yyyy"), YYYYMMDDHHMMSS("yyyy-MM-dd HH:mm:ss"), YYYYMMDDHHMM("yyyy-MM-dd HH:mm"), YYYYMMDD("yyyy-MM-dd");

        // TODO: use ThreadLocal instead of synchronization or java.time thread-safe parser/formatter?
        private final SimpleDateFormat dateFormat;
        private final String pattern;

        private DateFormat(String pattern) {
            this.pattern = pattern;
            dateFormat = new SimpleDateFormat(pattern);
        }

        public Date parse(String date) {
            try {
                if (Util.isEmpty(date)) {
                    return null;
                }
                synchronized (dateFormat) {
                    return dateFormat.parse(date);
                }
            } catch (Throwable e) {
                TemplateMasterException templateMasterException = TemplateMasterException.wrap(e).set("date", date).set("pattern", pattern);
                log.error(templateMasterException, templateMasterException);
                throw templateMasterException;
            }
        }

        public String getPattern() {
            return pattern;
        }

        public String format(Date date) {
            try {
                if (date == null) {
                    return null;
                }
                synchronized (dateFormat) {
                    return dateFormat.format(date);
                }
            } catch (Throwable e) {
                TemplateMasterException templateMasterException = TemplateMasterException.wrap(e).set("date", date);
                log.error(templateMasterException, templateMasterException);
                throw templateMasterException;
            }
        }

        public static Date parseDateOrDateTime(String date) {
            if (Util.isEmpty(date)) {
                return null;
            }
            if (date.contains(":")) {
                return YYYYMMDDHHMM.parse(date);
            } else {
                return YYYYMMDD.parse(date);
            }
        }
    }

    public static int getTimeZoneOffset(Date date) {
        TimeZone timeZone = TimeZone.getDefault();
        if (timeZone.inDaylightTime(date)) {
            return timeZone.getRawOffset() + timeZone.getDSTSavings();
        }
        return timeZone.getRawOffset();
    }

    public static ZonedDateTime getZonedDateTime(Instant instant, String timeZoneId) {
        return instant.atZone(TimeZone.getTimeZone(timeZoneId).toZoneId());
    }

    public static Instant convertLocalDateTimeToUtcInstant(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.of("UTC")).toInstant();
    }

}

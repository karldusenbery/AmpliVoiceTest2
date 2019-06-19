package com.dusenbery.amplivoicetest1.utilities;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.text.SimpleDateFormat;

public class ConvertEpoch {
    public String epochToIso8601(long time) {
        String format = "yyyy-MM-dd HH:mm:ss z";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(new Date(time * 1000));
    }

    public static void main(String[] args) {
        ConvertEpoch convert = new ConvertEpoch();
        long epoch = 1510272477; // Example
        String iso8601 = convert.epochToIso8601(epoch);
        System.out.println(epoch + " -> " + iso8601);
    }
}
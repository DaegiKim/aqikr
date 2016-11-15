package utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateTimeUtil {
    public static DateTime parseFromDataGoKrFormat(String str) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd H:m");

        DateTime dt;

        if(str.endsWith("24:00")) {
            str = str.replace("24:00", "23:59");
            dt = DateTime.parse(str, dateTimeFormatter);
            dt = dt.plusMinutes(1);
        } else {
            dt = DateTime.parse(str, dateTimeFormatter);
        }

        return dt;
    }
}

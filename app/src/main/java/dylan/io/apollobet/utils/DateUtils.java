package dylan.io.apollobet.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateUtils {

    public static Date toDate(String pattern, String strDate) throws ParseException {
        return new SimpleDateFormat(pattern).parse(strDate);
    }


    public static String toString(String pattern, Date date) {
        return new SimpleDateFormat(pattern).format(date);
    }
}

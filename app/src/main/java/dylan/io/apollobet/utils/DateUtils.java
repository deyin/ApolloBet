package dylan.io.apollobet.utils;

import android.support.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class DateUtils {

    public static Date toDate(String pattern, String strDate) throws ParseException {
        return new SimpleDateFormat(pattern).parse(strDate);
    }


    public static String toString(String pattern, Date date) {
        return new SimpleDateFormat(pattern).format(date);
    }

    public static Long getDays(@NonNull Date date) {
        return date.getTime() / 24 * 60 * 60 * 1000;
    }

    public static boolean isSameDay(@NonNull Date date1, @NonNull Date date2) {
        return getDays(date1).equals(getDays(date2));
    }

    public static Date getDateOfBeforeDays(@NonNull final Date date, int beforeDays) {
        return new Date(date.getTime() - 24 * 60 * 60 * 1000 * beforeDays);
    }

    public static int getDayOfWeek(@NonNull Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek;
    }

    public static int getNumberDayOfWeek(String dayOfWeek) {
        switch (dayOfWeek) {
            case "一":
                return 1;
            case "二":
                return 2;
            case "三":
                return 3;
            case "四":
                return 4;
            case "五":
                return 5;
            case "六":
                return 6;
            case "日":
                return 7;
            default:
                return -1;
        }
    }
}

package dylan.io.apollobet.persist;

import android.arch.persistence.room.TypeConverter;
import android.support.annotation.NonNull;
import android.util.Log;

import java.text.ParseException;
import java.util.Date;

import dylan.io.apollobet.utils.DateUtils;

public class DateConverters {
    @TypeConverter
    public Date stringToDate(@NonNull String value) {
        try {
            return DateUtils.toDate("yyyy-MM-ddHH:mm:ss", value);
        } catch (ParseException e) {
            Log.e("stringToDate", e.getMessage());

        }
        return null;
    }

    @TypeConverter
    public String dateToString(Date date) {
        return DateUtils.toString("yyyy-MM-ddHH:mm:ss", date);
    }
}
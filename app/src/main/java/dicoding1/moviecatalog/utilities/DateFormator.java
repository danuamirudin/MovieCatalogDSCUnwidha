package dicoding1.moviecatalog.utilities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static dicoding1.moviecatalog.utilities.Static.DATE_FORMAT;
import static dicoding1.moviecatalog.utilities.Static.DATE_FORMAT_DAY;

public class DateFormator {

    private static String format(String date, String format) {
        String result = "";

        DateFormat old = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Date oldDate = old.parse(date);
            DateFormat newFormat = new SimpleDateFormat(format, Locale.getDefault());
            result = newFormat.format(oldDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static String getDate(String date) {
        return format(date, DATE_FORMAT);
    }

    public static String getDateDay(String date) {
        return format(date, DATE_FORMAT_DAY);
    }
}

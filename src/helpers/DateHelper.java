package helpers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

/**
 * Created by Giota on 16/1/2017.
 */
public class DateHelper {
    public static String dateFormat(Date date) {
        if (date != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/YYYY");
            return simpleDateFormat.format(date);
        }
        return "-";
    }

    public static String timeFormat(Date date) {
        if (date != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
            return simpleDateFormat.format(date);
        }
        return "-";
    }

    public static Date stringToDate(String dateStr, String fomrat) throws ParseException {
        DateFormat format = new SimpleDateFormat(fomrat);
        return format.parse(dateStr);
    }

    private static Date parseDate(LocalDate futureDate) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd").parse(futureDate.toString());
    }

    public static Date addMonths(int months) throws ParseException {
        LocalDate futureDate = LocalDate.now().plusMonths(months);
        return parseDate(futureDate);
    }
}

package uk.co.tobymellor.compress.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

abstract public class Model {
    public final static String MYSQL_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Model.MYSQL_DATE_FORMAT, Locale.UK);

    public Date getDateFromMySQLFormat(String dateString) {
        Date date = null;

        try {
            date = simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }
}

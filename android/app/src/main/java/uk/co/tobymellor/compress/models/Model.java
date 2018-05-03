package uk.co.tobymellor.compress.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

abstract public class Model {
    private final static String MYSQL_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Model.MYSQL_DATE_FORMAT, Locale.UK);

    public Date getDateFromMySQLFormat(String dateString) {
        Date date = null;

        try {
            date = simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }

        return date;
    }
}

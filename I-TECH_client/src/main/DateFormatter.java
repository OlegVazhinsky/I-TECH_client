package main;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class for making date format more readable
 * @author vazhinsky_ot
 * @version 1.0
 */
public class DateFormatter {

    private final SimpleDateFormat dateAndTime;
    private final SimpleDateFormat date;
    private final SimpleDateFormat time;

    /**
     * Class constructor
     */
    public DateFormatter() {
        dateAndTime = new SimpleDateFormat("dd.MM.yyyy-hh:mm:ss : ");
        date = new SimpleDateFormat("dd.MM.yyyy : ");
        time = new SimpleDateFormat("hh:mm:ss : ");
    }

    /**
     * Method that returns date and time in format "dd.MM.yyyy-hh:mm:ss : "
     * @return date and time
     */
    public String getDateAndTime() {
        return dateAndTime.format(new Date());
    }

    /**
     * Method that returns date and time in format "dd.MM.yyyy : "
     * @return date
     */
    public String getDate() {
        return date.format(new Date());
    }

    /**
     * Method that returns date and time in format "hh:mm:ss : "
     * @return time
     */
    public String getTime() {
        return time.format(new Date());
    }
}

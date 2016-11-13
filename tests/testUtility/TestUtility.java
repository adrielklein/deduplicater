package testUtility;

import record.Record;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestUtility {
    public static Date createDate(String date) {
        Date result;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd'T'hh:mm:ss");
            result = sdf.parse(date);
        } catch (ParseException e) {
            result = null;
        }
        return result;
    }

    public static Record createRecord(int number, String id, String email, String date) {
        return new Record(number, id, email, "firstName", "lastName", "address", createDate(date));
    }

    public static Record createRecord(int number, String id, String email) {
        return new Record(number, id, email, "firstName", "lastName", "address", createDate("2014-05-07T17:30:20"));
    }
}

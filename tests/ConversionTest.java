import conversion.Converter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;
import record.Record;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static testUtility.TestUtility.createDate;

public class ConversionTest {
    public void assertDate(int year, int month, int day, int hours, int minutes, int seconds, Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        assertEquals(year, cal.get(Calendar.YEAR));
        assertEquals(month - 1, cal.get(Calendar.MONTH));
        assertEquals(day, cal.get(Calendar.DAY_OF_MONTH));
        assertEquals(hours, cal.get(Calendar.HOUR_OF_DAY));
        assertEquals(minutes, cal.get(Calendar.MINUTE));
        assertEquals(seconds, cal.get(Calendar.SECOND));
    }


    private ArrayList<Record> createRecords(int size) {
        ArrayList<Record> records = new ArrayList<Record>();
        for (int i = 0; i < size; i++) {
            Date date = createDate("2014-05-07T17:30:20+00:00");
            Record record = new Record(i, "123", "foo@bar.com", "John", "Smith", "123 Street St", date);
            records.add(record);
        }
        return records;
    }

    @Test
    public void ConvertJSONToRecords() throws FileNotFoundException {
        String content = new Scanner(new File("tests/test_leads.json")).useDelimiter("\\Z").next();
        ArrayList<Record> records = Converter.convertToRecords(content);
        Record record = records.get(0);
        assertEquals(0, record.number);
        assertEquals("123", record.id);
        assertEquals("foo@bar.com", record.email);
        assertEquals("John", record.firstName);
        assertEquals("Smith", record.lastName);
        assertEquals("123 Street St", record.address);
        assertDate(2014, 5, 7, 17, 30, 20, record.date);
    }

    @Test
    public void ConvertsRecordsToJSON() throws FileNotFoundException {
        JSONObject result = Converter.convertToJSON(createRecords(1));
        JSONArray records = (JSONArray) result.get("leads");
        JSONObject record = (JSONObject) records.get(0);
        assertEquals("123", record.get("_id"));
        assertEquals("foo@bar.com", record.get("email"));
        assertEquals("John", record.get("firstName"));
        assertEquals("Smith", record.get("lastName"));
        assertEquals("123 Street St", record.get("address"));
        assertEquals("2014-05-07T17:30:20+00:00", record.get("entryDate"));
    }


    @Test
    public void ConvertsRecordsToJSONWithMultipleRecords() throws FileNotFoundException {
        ArrayList<Record> records = createRecords(2);
        assertEquals(0, records.get(0).number);
        assertEquals(1, records.get(1).number);
        JSONObject result = Converter.convertToJSON(records);
        JSONArray recordsJSON = (JSONArray) result.get("leads");
        assertEquals(2, recordsJSON.size());
    }


}
import graph.Record;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;



public class RecordTest {

    private Date createDate(String date) {
        Date result;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd'T'hh:mm:ss");
            result = sdf.parse(date);
        } catch (ParseException e) {
            result = null;
        }
        return result;
    }

    private Record createRecord(int number, String id, String email, String firstName,
            String lastName, String address, String date) {
        return new Record(number, id, email, firstName,  lastName, address, createDate(date));
    }
    @Test
    public void RecordsWithSameIdsAreDuplicates() {
        Record first = createRecord(0, "0", "a@gmail.com", "Jo", "Smith", "123 Main St", "2014-05-07T17:30:20");
        Record second = createRecord(1, "0", "b@gmail.com", "Jo", "Smith", "123 Main St", "2014-05-07T17:30:20");
        assertTrue(first.isDuplicate(second));
    }

    @Test
    public void RecordsWithDifferentIdsAreNotDuplicates() {
        Record first = createRecord(0, "0", "a@gmail.com", "Jo", "Smith", "123 Main St", "2014-05-07T17:30:20");
        Record second = createRecord(1, "1", "b@gmail.com", "Jo", "Smith", "123 Main St", "2014-05-07T17:30:20");
        assertFalse(first.isDuplicate(second));
    }

    @Test
    public void RecordsWithSameEmailsAreDuplicates() {
        Record first = createRecord(0, "0", "a@gmail.com", "Jo", "Smith", "123 Main St", "2014-05-07T17:30:20");
        Record second = createRecord(1, "1", "a@gmail.com", "Jo", "Smith", "123 Main St", "2014-05-07T17:30:20");
        assertTrue(first.isDuplicate(second));
    }

    @Test
    public void WhenRecordsAreNotDuplicatesThenCompareByRecordNumber() {
        Record first = createRecord(0, "0", "a@gmail.com", "Jo", "Smith", "123 Main St", "2014-05-07T17:30:20");
        Record second = createRecord(1, "1", "b@gmail.com", "Jo", "Smith", "123 Main St", "2014-05-07T17:30:20");
        assertEquals( -1, first.compareTo(second));
        assertEquals( 1, second.compareTo(first));
    }

    @Test
    public void WhenRecordsAreDuplicatesThenCompareByDate() {
        Record first = createRecord(0, "0", "a@gmail.com", "Jo", "Smith", "123 Main St", "2014-05-07T17:30:20");
        Record second = createRecord(1, "0", "b@gmail.com", "Jo", "Smith", "123 Main St", "2014-05-08T17:30:20");
        assertEquals( -1, first.compareTo(second));
        assertEquals( 1, second.compareTo(first));
    }

    @Test
    public void WhenDuplicateRecordsHaveSameDateThenCompareByNumber() {
        Record first = createRecord(0, "0", "a@gmail.com", "Jo", "Smith", "123 Main St", "2014-05-07T17:30:20");
        Record second = createRecord(1, "0", "b@gmail.com", "Jo", "Smith", "123 Main St", "2014-05-07T17:30:20");
        assertEquals( -1, first.compareTo(second));
        assertEquals( 1, second.compareTo(first));
    }

    @Test
    public void RecordsCanBeSorted() {
        Record first = createRecord(0, "0", "a@gmail.com", "Jo", "Smith", "123 Main St", "2014-05-08T17:30:20");
        Record second = createRecord(1, "0", "b@gmail.com", "Jo", "Smith", "123 Main St", "2014-05-07T17:30:20");
        ArrayList<Record> records = new ArrayList<Record>();
        records.add(first);
        records.add(second);
        Collections.sort(records);
        assertEquals(1, records.get(0).number);
    }
}

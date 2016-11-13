import org.junit.Test;
import record.Record;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.*;
import static testUtility.TestUtility.createRecord;


public class RecordTest {


    @Test
    public void RecordsWithSameIdsAreDuplicates() {
        Record first = createRecord(0, "0", "a@gmail.com");
        Record second = createRecord(1, "0", "b@gmail.com");
        assertTrue(first.isDuplicate(second));
    }

    @Test
    public void RecordsWithDifferentIdsAreNotDuplicates() {
        Record first = createRecord(0, "0", "a@gmail.com");
        Record second = createRecord(1, "1", "b@gmail.com");
        assertFalse(first.isDuplicate(second));
    }

    @Test
    public void RecordsWithSameEmailsAreDuplicates() {
        Record first = createRecord(0, "0", "a@gmail.com");
        Record second = createRecord(1, "1", "a@gmail.com");
        assertTrue(first.isDuplicate(second));
    }

    @Test
    public void WhenRecordsAreNotDuplicatesThenCompareByRecordNumber() {
        Record first = createRecord(0, "0", "a@gmail.com");
        Record second = createRecord(1, "1", "b@gmail.com");
        assertEquals(-1, first.compareTo(second));
        assertEquals(1, second.compareTo(first));
    }

    @Test
    public void WhenRecordsAreDuplicatesThenCompareByDate() {
        Record first = createRecord(0, "0", "a@gmail.com", "2014-05-08T17:30:20");
        Record second = createRecord(1, "0", "b@gmail.com", "2014-05-07T17:30:20");
        assertEquals(1, first.compareTo(second));
        assertEquals(-1, second.compareTo(first));
    }

    @Test
    public void WhenDuplicateRecordsHaveSameDateThenCompareByNumber() {
        Record first = createRecord(0, "0", "a@gmail.com");
        Record second = createRecord(1, "0", "b@gmail.com");
        assertEquals(-1, first.compareTo(second));
        assertEquals(1, second.compareTo(first));
    }

    @Test
    public void RecordsCanBeSorted() {
        Record first = createRecord(0, "0", "a@gmail.com", "2014-05-08T17:30:20");
        Record second = createRecord(1, "0", "b@gmail.com", "2014-05-07T17:30:20");
        ArrayList<Record> records = new ArrayList<Record>();
        records.add(first);
        records.add(second);
        Collections.sort(records);
        assertEquals(1, records.get(0).number);
    }
}

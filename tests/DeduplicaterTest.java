import change.Change;
import deduplicater.Deduplicater;
import org.junit.Test;
import record.Record;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static testUtility.TestUtility.createDate;
import static testUtility.TestUtility.createRecord;

public class DeduplicaterTest {


    private void assertRecord(String id, String email, Record record) {
        assertEquals(id, record.id);
        assertEquals(email, record.email);
    }

    @Test
    public void RecordsWithSameIdsAreDuplicates() {
        ArrayList<Record> records = new ArrayList<Record>();
        records.add(createRecord(0, "0", "a@gmail.com", "2014-05-07T17:30:20"));
        records.add(createRecord(1, "0", "b@gmail.com", "2014-05-07T17:30:20"));
        records.add(createRecord(2, "1", "b@gmail.com", "2014-05-07T17:30:20"));
        ArrayList<Record> result = Deduplicater.getDeduplicationResult(records).records;
        assertEquals(2, result.size());
        assertRecord("0", "a@gmail.com", result.get(0));
        assertRecord("1", "b@gmail.com", result.get(1));
    }

    @Test
    public void RecordsWithLaterDatesArePeferred() {
        ArrayList<Record> records = new ArrayList<Record>();
        records.add(createRecord(0, "0", "a@gmail.com", "2014-05-08T17:30:20"));
        records.add(createRecord(1, "0", "b@gmail.com", "2014-05-07T17:30:20"));
        ArrayList<Record> result = Deduplicater.getDeduplicationResult(records).records;
        assertRecord("0", "a@gmail.com", result.get(0));
    }

    @Test
    public void CanDisplayASingleChange() {
        ArrayList<Record> records = new ArrayList<Record>();
        records.add(createRecord(0, "0", "a@gmail.com", "2014-05-07T17:30:20"));
        records.add(createRecord(1, "0", "b@gmail.com", "2014-05-07T17:30:20"));
        ArrayList<Change> changes = Deduplicater.getDeduplicationResult(records).changes;
        assertEquals(1, changes.size());
        Change change = changes.get(0);
        assertEquals("a@gmail.com", change.fromRecord.email);
        assertEquals("b@gmail.com", change.toRecord.email);
        assertEquals("email: a@gmail.com -> b@gmail.com", change.getFieldChanges().get(0));
    }

    @Test
    public void CanDisplayMultipleChanges() {
        ArrayList<Record> records = new ArrayList<Record>();
        records.add(new Record(0, "a", "a@gmail.com", "adam", "smith", "1", createDate("2014-05-07T17:30:20")));
        records.add(new Record(0, "a", "b@gmail.com", "ben", "hanks", "2", createDate("2014-05-08T17:30:20")));
        ArrayList<Change> changes = Deduplicater.getDeduplicationResult(records).changes;
        assertEquals(1, changes.size());
        Change change = changes.get(0);
        ArrayList<String> fieldChanges = change.getFieldChanges();
        assertEquals(5, fieldChanges.size());
        assertEquals("email: a@gmail.com -> b@gmail.com", fieldChanges.get(0));
        assertEquals("firstName: adam -> ben", fieldChanges.get(1));
        assertEquals("lastName: smith -> hanks", fieldChanges.get(2));
        assertEquals("address: 1 -> 2", fieldChanges.get(3));
        assertEquals("date: 2014-05-07T17:30:20+00:00 -> 2014-05-08T17:30:20+00:00", fieldChanges.get(4));
    }

    @Test
    public void CanDisplayIdChange() {
        ArrayList<Record> records = new ArrayList<Record>();
        records.add(createRecord(0, "0", "a@gmail.com", "2014-05-07T17:30:20"));
        records.add(createRecord(0, "1", "a@gmail.com", "2014-05-07T17:30:20"));
        Change change = Deduplicater.getDeduplicationResult(records).changes.get(0);
        ArrayList<String> fieldChanges = change.getFieldChanges();
        assertEquals(1, fieldChanges.size());
        assertEquals("id: 0 -> 1", fieldChanges.get(0));
    }


}

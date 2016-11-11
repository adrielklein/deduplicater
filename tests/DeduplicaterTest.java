import deduplicater.Deduplicater;
import record.Record;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class DeduplicaterTest {

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
    private Record createRecord(int number, String id, String email, String date) {
        return new Record(number, id, email, "firstName", "lastName", "address", createDate(date));
    }

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
        Deduplicater deduplicater = new Deduplicater(records);
        ArrayList<Record> result = deduplicater.getUniqueRecords();
        assertEquals(2, result.size());
        assertRecord("0", "a@gmail.com", result.get(0));
        assertRecord("1", "b@gmail.com", result.get(1));

    }
}

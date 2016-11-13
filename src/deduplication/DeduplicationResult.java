package deduplication;

import java.util.ArrayList;

public class DeduplicationResult {
    public ArrayList<Record> records;
    public ArrayList<Change> changes;

    public DeduplicationResult(ArrayList<Record> newRecords, ArrayList<Change> newChanges) {
        records = newRecords;
        changes = newChanges;
    }

}

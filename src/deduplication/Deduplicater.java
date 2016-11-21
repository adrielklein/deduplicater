package deduplication;

import java.util.*;


public class Deduplicater {


    public static DeduplicationResult getDeduplicationResult(ArrayList<Record> records) {
        Collections.sort(records);

        ArrayList<Record> uniqueRecords = new ArrayList<Record>();
        ArrayList<Change> changes = new ArrayList<Change>();
        findUniqueRecordsAndChanges(records, uniqueRecords, changes);
        Collections.sort(uniqueRecords);

        return new DeduplicationResult(uniqueRecords, changes);
    }

    private static void findUniqueRecordsAndChanges(ArrayList<Record> records, ArrayList<Record> uniqueRecords, ArrayList<Change> changes) {
        HashMap[] maps = getMaps(records);
        HashMap<String, ArrayList<Record>> idToRecords = maps[0];
        HashMap<String, ArrayList<Record>> emailToRecords = maps[1];
        for (int index = records.size() - 1; index >= 0; index--) {
            Record record = records.get(index);
            if (!record.isVisited) {
                record.isVisited = true;
                uniqueRecords.add(record);
                visitDuplicates(changes, idToRecords, emailToRecords, record);
            }
        }
    }

    private static void visitDuplicates(ArrayList<Change> changes, HashMap<String, ArrayList<Record>> idToRecords, HashMap<String, ArrayList<Record>> emailToRecords, Record record) {
        ArrayList<Record> duplicates = idToRecords.get(record.id);
        duplicates.addAll(emailToRecords.get(record.email));
        for (Record duplicate : duplicates) {
            if (!duplicate.isVisited) {
                duplicate.isVisited = true;
                changes.add(new Change(duplicate, record));
            }
        }
    }


    private static HashMap[] getMaps(ArrayList<Record> records) {
        HashMap<String, ArrayList<Record>> idToIndexes = new HashMap<String, ArrayList<Record>>();
        HashMap<String, ArrayList<Record>> emailToIndexes = new HashMap<String, ArrayList<Record>>();
        for (Record record : records) {
            addToMap(record.id, record, idToIndexes);
            addToMap(record.email, record, emailToIndexes);
        }
        return new HashMap[]{idToIndexes, emailToIndexes};

    }

    private static void addToMap(String key, Record record, HashMap<String, ArrayList<Record>> map) {
        if (map.containsKey(key)) {
            map.get(key).add(record);
        } else {
            ArrayList<Record> records = new ArrayList<Record>();
            records.add(record);
            map.put(key, records);
        }
    }
}
package deduplication;

import java.util.*;


public class Deduplicater {


    public static DeduplicationResult getDeduplicationResult(ArrayList<Record> records) {
        Collections.sort(records);

        ArrayList<Record> uniqueRecords = new ArrayList<Record>();
        ArrayList<Change> changes = new ArrayList<Change>();
        findUniqueRecords(records, uniqueRecords, changes);
        Collections.sort(uniqueRecords);

        return new DeduplicationResult(uniqueRecords, changes);
    }

    private static void findUniqueRecords(ArrayList<Record> records, ArrayList<Record> uniqueRecords, ArrayList<Change> changes) {
        HashMap[] maps = getMaps(records);
        HashMap<String, ArrayList<Integer>> idToIndexes = maps[0];
        HashMap<String, ArrayList<Integer>> emailToIndexes = maps[1];
        boolean[] isVisited = new boolean[records.size()];
        for (int index = records.size() - 1; index >= 0; index--) {
            if (isVisited[index])
                continue;
            isVisited[index] = true;

            Record record = records.get(index);
            uniqueRecords.add(record);
            visitDuplicates(records, changes, idToIndexes, isVisited, index, record.id);
            visitDuplicates(records, changes, emailToIndexes, isVisited, index, record.email);
        }

    }

    private static void visitDuplicates(ArrayList<Record> records, ArrayList changes, HashMap<String, ArrayList<Integer>> map, boolean[] isVisited, int index, String key) {
        for (Integer duplicateIndex : map.get(key)) {
            if (!duplicateIndex.equals(index) && !isVisited[duplicateIndex]) {
                isVisited[duplicateIndex] = true;
                changes.add(new Change(records.get(duplicateIndex), records.get(index)));
            }
        }
        map.remove(key);
    }


    private static HashMap[] getMaps(ArrayList<Record> records) {
        HashMap<String, ArrayList<Integer>> idToIndexes = new HashMap<String, ArrayList<Integer>>();
        HashMap<String, ArrayList<Integer>> emailToIndexes = new HashMap<String, ArrayList<Integer>>();
        for (int index = 0; index < records.size(); index++) {
            Record record = records.get(index);
            addToMap(record.id, index, idToIndexes);
            addToMap(record.email, index, emailToIndexes);
        }
        return new HashMap[]{idToIndexes, emailToIndexes};

    }

    private static void addToMap(String key, int index, HashMap<String, ArrayList<Integer>> map) {
        if (map.containsKey(key)) {
            map.get(key).add(index);
        } else {
            ArrayList<Integer> indexes = new ArrayList<Integer>();
            indexes.add(index);
            map.put(key, indexes);
        }
    }
}
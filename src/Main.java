import deduplication.Change;
import deduplication.Converter;
import deduplication.Deduplicater;
import deduplication.DeduplicationResult;
import deduplication.Record;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String args[]) throws FileNotFoundException {
        if (args.length != 1) {
            System.out.println("Please provide exactly one command line argument specifying the location of the JSON file");
            return;
        }
        String content = new Scanner(new File(args[0])).useDelimiter("\\Z").next();
        ArrayList<Record> records = Converter.convertToRecords(content);
        DeduplicationResult result = Deduplicater.getDeduplicationResult(records);

        printInputAndOutput(records, result.records);
        printDuplicates(result.changes);

    }

    private static void printInputAndOutput(ArrayList<Record> records, ArrayList<Record> uniqueRecords) {
        System.out.println();
        System.out.println(getMessage(records, uniqueRecords));
        System.out.println();
        System.out.println(String.format("Input (%d):", records.size()));
        System.out.println(Converter.convertToJSON(records).toString());
        System.out.println();
        System.out.println(String.format("Output (%d):", uniqueRecords.size()));
        System.out.println(Converter.convertToJSON(uniqueRecords).toString());
        System.out.println();
    }


    private static void printDuplicates(ArrayList<Change> changes) {
        for (int i = 0; i < changes.size(); i++) {
            Change change = changes.get(i);
            System.out.println(String.format("\nDuplicate #%d:\n", i + 1));
            System.out.println(String.format("Original Record:\n %s", change.fromRecord.toString()));
            System.out.println(String.format("New Record:\n %s", change.toRecord.toString()));
            printFieldChanges(change.getFieldChanges());
        }
    }

    private static void printFieldChanges(ArrayList<String> fieldChanges) {
        if (fieldChanges.size() == 0) {
            System.out.println("Records were identical. No field changes.");
            return;
        }
        System.out.println("Field Changes:");
        for (String fieldChange : fieldChanges)
            System.out.println(String.format("    %s", fieldChange));
    }


    private static String getMessage(ArrayList<Record> records, ArrayList<Record> uniqueRecords) {
        int duplicatesRemoved = records.size() - uniqueRecords.size();
        String message;
        if (duplicatesRemoved == 1) {
            message = "Removed 1 duplicate!";
        } else {
            message = String.format("Removed %d duplicates!", duplicatesRemoved);
        }
        return String.format("Deduplicater finished processing. %s", message);
    }
}

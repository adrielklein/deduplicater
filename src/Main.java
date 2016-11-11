import change.Change;
import conversion.Converter;
import deduplicater.Deduplicater;
import deduplicater.DeduplicationResult;
import record.Record;

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
        ArrayList<Record> records = Converter.convertToLeads(content);
        Deduplicater deduplicater = new Deduplicater(records);
        DeduplicationResult result = deduplicater.getDeduplicationResult();

        printInputAndOutput(records, result.records);
        printDuplicates(result.changes);

    }

    private static void printDuplicates(ArrayList<Change> changes) {
        for (int i = 0; i < changes.size(); i++) {
            Change change = changes.get(i);
            System.out.println(String.format("\nDuplicate #%d:\n", i + 1));
            System.out.println(String.format("Original Record:\n %s", change.fromRecord.toString()));
            System.out.println(String.format("New Record:\n %s", change.toRecord.toString()));
            System.out.println("Field Changes:");
            for (String fieldChange : change.getFieldChanges())
                System.out.println(String.format("    %s", fieldChange));
        }
    }

    private static void printInputAndOutput(ArrayList<Record> records, ArrayList<Record> uniqueRecords) {
        System.out.println();
        System.out.println("Duplicates have been removed!");
        System.out.println();
        System.out.println("Input:");
        System.out.println(Converter.convertToJSON(records).toString());
        System.out.println();
        System.out.println("Output:");
        System.out.println(Converter.convertToJSON(uniqueRecords).toString());
        System.out.println();
        System.out.println(String.format("Number of duplicate records removed: %d", records.size() - uniqueRecords.size()));
    }
}

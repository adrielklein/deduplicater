import conversion.Converter;
import deduplicater.Deduplicater;
import record.Record;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String args[]) throws FileNotFoundException{
        if (args.length != 1){
            System.out.println("Please provide exactly one command line argument specifying the location of the JSON file");
            return;
        }
        String location = args[0];
        String content = new Scanner(new File(location)).useDelimiter("\\Z").next();
        ArrayList<Record> records = Converter.convertToLeads(content);
        Deduplicater deduplicater = new Deduplicater(records);
        ArrayList<Record> uniqueRecords = deduplicater.getUniqueRecords();
        String result = Converter.convertToJSON(uniqueRecords).toString();
        System.out.println(result);
    }
}

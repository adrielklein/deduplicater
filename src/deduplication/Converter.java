package deduplication;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Converter {

    public static ArrayList<Record> convertToRecords(String input) {
        ArrayList<Record> result = new ArrayList<Record>();

        JSONObject obj = (JSONObject) JSONValue.parse(input);
        JSONArray records = (JSONArray) obj.get("leads");
        for (int i = 0; i < records.size(); i++) {
            JSONObject record = (JSONObject) records.get(i);
            result.add(getRecordObject(i, record));
        }
        return result;
    }

    public static JSONObject convertToJSON(ArrayList<Record> records) {
        JSONArray jsonRecords = new JSONArray();
        for (Record record : records) {
            jsonRecords.add(getJsonRecord(record));
        }
        JSONObject result = new JSONObject();
        result.put("leads", jsonRecords);
        return result;
    }

    public static String convertToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss");
        String result = sdf.format(date);
        return result + "+00:00";
    }

    private static Date convertToDate(String date) {
        Date result;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd'T'hh:mm:ss");
            result = sdf.parse(date);
        } catch (ParseException e) {
            result = null;
        }
        return result;
    }

    private static Record getRecordObject(int number, JSONObject record) {
        String id = (String) record.get("_id");
        String email = (String) record.get("email");
        String firstName = (String) record.get("firstName");
        String lastName = (String) record.get("lastName");
        String address = (String) record.get("address");
        Date date = convertToDate((String) record.get("entryDate"));
        return new Record(number, id, email, firstName, lastName, address, date);
    }

    private static JSONObject getJsonRecord(Record record) {
        JSONObject jsonRecord = new JSONObject();
        jsonRecord.put("_id", record.id);
        jsonRecord.put("email", record.email);
        jsonRecord.put("firstName", record.firstName);
        jsonRecord.put("lastName", record.lastName);
        jsonRecord.put("address", record.address);
        jsonRecord.put("entryDate", convertToString(record.date));
        return jsonRecord;
    }
}

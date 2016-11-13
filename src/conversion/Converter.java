package conversion;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import record.Record;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Converter {
    private static Date convertToDate(String date) {
        Date result;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd'T'hh:mm:ss");
            result = sdf.parse(date);
        } catch (ParseException e){
            result = null;
        }
        return result;
    }

    public static String convertToString(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss");
        String result = sdf.format(date);
        return result + "+00:00";
    }

    public static ArrayList<Record> convertToRecords(String input) {
        ArrayList<Record> result = new ArrayList<Record>();

        JSONObject obj = (JSONObject) JSONValue.parse(input);
        JSONArray records = (JSONArray) obj.get("leads");
        for (int i = 0; i < records.size(); i++) {
            JSONObject record = (JSONObject) records.get(i);
            String id = (String) record.get("_id");
            String email = (String) record.get("email");
            String firstName = (String) record.get("firstName");
            String lastName = (String) record.get("lastName");
            String address = (String) record.get("address");
            Date date = convertToDate((String) record.get("entryDate"));
            result.add(new Record(i, id, email, firstName, lastName, address, date));
        }
        return result;
    }

    public static JSONObject convertToJSON(ArrayList<Record> records) {
        JSONArray jsonRecords = new JSONArray();

        for (int i =0; i < records.size(); i++) {
            Record record = records.get(i);
            JSONObject jo = new JSONObject();
            jo.put("_id", record.id);
            jo.put("email", record.email);
            jo.put("firstName", record.firstName);
            jo.put("lastName", record.lastName);
            jo.put("address", record.address);
            jo.put("entryDate", convertToString(record.date));
            jsonRecords.add(jo);
        }

        JSONObject result = new JSONObject();
        result.put("leads", jsonRecords);
        return result;
    }
}

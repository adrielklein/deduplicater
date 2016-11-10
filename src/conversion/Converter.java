package conversion;

import graph.Person;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by adrielklein on 11/10/16.
 */
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

    public static ArrayList<Person> convertToPeople(String input) {
        ArrayList<Person> result = new ArrayList<Person>();

        JSONObject obj = (JSONObject) JSONValue.parse(input);
        JSONArray leads = (JSONArray) obj.get("leads");
        for (int i = 0; i < leads.size(); i++) {
            JSONObject lead = (JSONObject) leads.get(i);
            String id = (String) lead.get("_id");
            String email = (String) lead.get("email");
            String firstName = (String) lead.get("firstName");
            String lastName = (String) lead.get("lastName");
            String address = (String) lead.get("address");
            Date date = convertToDate((String) lead.get("entryDate"));
            result.add(new Person(id, email, firstName, lastName, address, date));
        }
        return result;
    }
}

package deduplication;

import org.json.simple.JSONObject;

import java.util.Date;

import static deduplication.Converter.convertToString;

public class Record implements Comparable<Record> {
    public int number;
    public String id;
    public String email;
    public String firstName;
    public String lastName;
    public String address;
    public Date date;
    public boolean isVisited;

    public Record(int startNumber, String startId, String startEmail, String startFirstName,
                  String startLastName, String startAddress, Date startDate) {
        number = startNumber;
        id = startId;
        email = startEmail;
        firstName = startFirstName;
        lastName = startLastName;
        address = startAddress;
        date = startDate;
        isVisited = false;
    }

    public boolean isDuplicate(Record record) {
        return (record.id.equals(id) || record.email.equals(email));
    }

    public int compareTo(Record record) {
        int dateComparrison = date.compareTo(record.date);
        if (dateComparrison == 0)
            return compareByRecordNumber(record);
        return dateComparrison;

    }

    private int compareByRecordNumber(Record record) {
        Integer x = number;
        Integer y = record.number;
        return x.compareTo(y);
    }


    public String toString() {
        JSONObject result = new JSONObject();
        result.put("_id", this.id);
        result.put("email", this.email);
        result.put("firstName", this.firstName);
        result.put("lastName", this.lastName);
        result.put("address", this.address);
        result.put("entryDate", convertToString(this.date));
        return result.toString();
    }
}

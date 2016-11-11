package record;

import java.util.Date;

public class Record implements Comparable<Record> {
    public int number;
    public String id;
    public String email;
    public String firstName;
    public String lastName;
    public String address;
    public Date date;

    public Record(int startNumber, String startId, String startEmail, String startFirstName,
                  String startLastName, String startAddress, Date startDate) {
        number = startNumber;
        id = startId;
        email = startEmail;
        firstName = startFirstName;
        lastName = startLastName;
        address = startAddress;
        date = startDate;
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

    public Record clone() {

        return new Record(number, id, email, firstName, lastName, address, date);
    }
}

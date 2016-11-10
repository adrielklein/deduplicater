package graph;

import java.util.Date;

public class Record {
    public String id;
    public String email;
    public String firstName;
    public String lastName;
    public String address;
    public Date date;

    public Record(String startId, String startEmail, String startFirstName,
                  String startLastName, String startAddress, Date startDate) {
        id = startId;
        email = startEmail;
        firstName = startFirstName;
        lastName = startLastName;
        address = startAddress;
        date = startDate;
    }
}

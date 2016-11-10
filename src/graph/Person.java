package graph;

import java.util.Date;

/**
 * Created by adrielklein on 11/10/16.
 */
public class Person {
    public String id;
    public String email;
    public String firstName;
    public String lastName;
    public String address;
    public Date date;

    public Person(String startId, String startEmail, String startFirstName,
                  String startLastName, String startAddress,Date startDate) {
        id = startId;
        email = startEmail;
        firstName = startFirstName;
        lastName = startLastName;
        address = startAddress;
        date = startDate;
    }
}

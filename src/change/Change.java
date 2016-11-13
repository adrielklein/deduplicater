package change;

import conversion.Converter;
import record.Record;

import java.util.ArrayList;

public class Change {
    public Record fromRecord;
    public Record toRecord;

    public Change(Record newFromRecord, Record newToRecord) {
        fromRecord = newFromRecord;
        toRecord = newToRecord;
    }

    public ArrayList<String> getFieldChanges() {
        ArrayList<String> result = new ArrayList<String>();
        if (!fromRecord.id.equals(toRecord.id))
            result.add(String.format("id: %s -> %s", fromRecord.id, toRecord.id));
        if (!fromRecord.email.equals(toRecord.email))
            result.add(String.format("email: %s -> %s", fromRecord.email, toRecord.email));
        if (!fromRecord.firstName.equals(toRecord.firstName))
            result.add(String.format("firstName: %s -> %s", fromRecord.firstName, toRecord.firstName));
        if (!fromRecord.lastName.equals(toRecord.lastName))
            result.add(String.format("lastName: %s -> %s", fromRecord.lastName, toRecord.lastName));
        if (!fromRecord.address.equals(toRecord.address))
            result.add(String.format("address: %s -> %s", fromRecord.address, toRecord.address));
        if (!fromRecord.date.equals(toRecord.date))
            result.add(String.format("date: %s -> %s", Converter.convertToString(fromRecord.date),
                    Converter.convertToString(toRecord.date)));
        return result;
    }
}

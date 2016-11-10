import conversion.Converter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.ArrayList;

import graph.Person;

public class ConversionTest {
    public void assertDate(int year, int month, int day, int hours, int minutes, int seconds, Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        assertEquals(year,cal.get(Calendar.YEAR));
        assertEquals(month-1,cal.get(Calendar.MONTH));
        assertEquals(day,cal.get(Calendar.DAY_OF_MONTH));
        assertEquals(hours,cal.get(Calendar.HOUR_OF_DAY));
        assertEquals(minutes,cal.get(Calendar.MINUTE));
        assertEquals(seconds,cal.get(Calendar.SECOND));
    }

    @Test
    public void StringGetsConvertedToArrayList() throws FileNotFoundException {
        String content = new Scanner(new File("tests/test_leads.json")).useDelimiter("\\Z").next();
        ArrayList<Person> people = Converter.convertToPeople(content);
        Person person = people.get(0);
        assertEquals("123", person.id);
        assertEquals("foo@bar.com", person.email);
        assertEquals("John", person.firstName);
        assertEquals("Smith", person.lastName);
        assertEquals("123 Street St", person.address);
        assertDate(2014,5,7,17,30,20, person.date);



    }


}
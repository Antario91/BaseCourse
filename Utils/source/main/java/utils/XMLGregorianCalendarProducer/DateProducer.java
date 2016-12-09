package utils.XMLGregorianCalendarProducer;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by olgo on 22-Nov-16.
 */
public class DateProducer {
    public static XMLGregorianCalendar produce(Date date) {
        XMLGregorianCalendar calendar = null;
        try {
            GregorianCalendar gregorianCalendar = new GregorianCalendar();
            gregorianCalendar.setTime(date);
            calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
        } catch (DatatypeConfigurationException ex){
            ex.printStackTrace();
        }
        return calendar;
    }
}

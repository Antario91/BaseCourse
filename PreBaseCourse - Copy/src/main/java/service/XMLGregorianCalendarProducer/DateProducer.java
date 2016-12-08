package service.XMLGregorianCalendarProducer;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;

/**
 * Created by olgo on 22-Nov-16.
 */
public interface DateProducer {
    XMLGregorianCalendar produce(Date date) throws DatatypeConfigurationException;
}

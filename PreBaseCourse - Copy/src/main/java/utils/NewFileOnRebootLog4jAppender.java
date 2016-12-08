package utils;

import org.apache.log4j.FileAppender;

import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by olgo on 25-Nov-16.
 */
public class NewFileOnRebootLog4jAppender extends FileAppender {
    public NewFileOnRebootLog4jAppender() {
    }

    @Override
    public void setFile(String file) {
        super.setFile(prependDate(file));
    }

    private static String prependDate(String filename) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");

        return filename + "-" + dateFormat.format(new Date()) + ".log";
    }
}

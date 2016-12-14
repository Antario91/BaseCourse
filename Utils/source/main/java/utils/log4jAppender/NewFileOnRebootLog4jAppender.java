package utils.log4jAppender;

import org.apache.log4j.FileAppender;

import java.text.SimpleDateFormat;
import java.util.Date;

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

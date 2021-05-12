import org.apache.log4j.Logger;

import java.io.*;
import java.sql.SQLException;
import java.util.*;

public class LogTester {
    /* Get actual class name to be printed on */
    //static Logger log = Logger.getLogger(HelloWorld.class.getName());
    static Logger log = Logger.getLogger(LogTester.class.getName());

    public static void main(String[] args) {

        log.debug("This is a debug message");
        log.info("Hello this is an info message");
        log.warn("Hello this is an warn message");
    }
}
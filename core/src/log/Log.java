package log;

import java.util.Date;

public abstract class Log {

    private static String filename;

    public static void begin() {
        Date date = new Date();
        filename = "log-" + date + ".txt";
        System.out.println("begin writing logfile " + filename);
    }

    public static void writeLog(String string) {
        System.out.println(string);
    }

    public static void main(String[] args) {
        begin();
    }
}

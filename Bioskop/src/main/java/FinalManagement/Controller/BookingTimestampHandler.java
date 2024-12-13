package FinalManagement.Controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Deprecated
public class BookingTimestampHandler {

    public static String getTimestamp() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }
}

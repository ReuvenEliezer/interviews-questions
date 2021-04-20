import org.junit.Assert;
import org.junit.Test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

import static java.time.temporal.ChronoUnit.DAYS;


public class TimeTest {

    @Test
    public void dateTimeFormatter() throws ParseException {
        //https://help.sumologic.com/03Send-Data/Sources/04Reference-Information-for-Sources/Timestamps%2C-Time-Zones%2C-Time-Ranges%2C-and-Date-Formats
        String timeTest ="2021-04-19T09:30:10.335Z";

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date date = simpleDateFormat.parse(timeTest);
        System.out.println(date);
        DateTimeFormatter inFormatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        DateTimeFormatter outFormatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime out = LocalDateTime.parse(timeTest, inFormatter);
        String formattedDate = out.format(outFormatter);
        System.out.println(formattedDate);

        OffsetDateTime offsetDateTime = OffsetDateTime.parse(timeTest);
        Date date1 = Date.from(offsetDateTime.toInstant());
        System.out.println(date1);
    }

    @Test
    public void getStartAndEndOfQuarterAndConvertDateToLocalDateTest(){
        LocalDate localDate = LocalDate.now(ZoneOffset.UTC);
        Date date = Date.from(localDate.atStartOfDay(ZoneOffset.UTC).toInstant());
        Timestamp timestamp = new Timestamp(date.getTime());
        long l = timestamp.toInstant().toEpochMilli();
        LocalDate firstDayOfQuarter = localDate.with(localDate.getMonth().firstMonthOfQuarter())
                .with(TemporalAdjusters.firstDayOfMonth());

        LocalDate lastDayOfQuarter = firstDayOfQuarter.plusMonths(2)
                .with(TemporalAdjusters.lastDayOfMonth());
        LocalDate currentDate = date.toInstant().atZone(ZoneOffset.UTC).toLocalDate();

        if (currentDate.isAfter(firstDayOfQuarter)&&currentDate.isBefore(lastDayOfQuarter) ){
            int i=0;
        }
        long between = DAYS.between(LocalDate.now(ZoneOffset.UTC).minusDays(1), LocalDate.now(ZoneOffset.UTC));
    }

    @Test
    public void convertLocalDateTimeToEpochMilliAndViceVersa(){
        //https://stackoverflow.com/questions/35183146/how-can-i-create-a-java-8-localdate-from-a-long-epoch-time-in-milliseconds/35187046
        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
//        LocalDate date = Instant.ofEpochMilli(longValue).atZone(ZoneId.systemDefault()).toLocalDate();
        long l = now.toInstant(ZoneOffset.UTC).toEpochMilli();
        LocalDateTime date1 = LocalDateTime.ofInstant(Instant.ofEpochMilli(l ), ZoneOffset.UTC);
        Assert.assertEquals(now,date1);
    }
}

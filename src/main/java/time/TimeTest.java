package time;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.Data;
import org.junit.Assert;
import org.junit.Test;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

import static java.time.temporal.ChronoUnit.DAYS;


public class TimeTest {

    @Test
    public void dateTimeFormatter1() throws ParseException {
        /**
         * https://www.callicoder.com/how-to-format-date-time-java/
         * https://www.callicoder.com/how-to-convert-parse-string-to-date-java/
         * https://www.callicoder.com/java-simpledateformat-thread-safety-issues/#how-should-i-use-simpledateformat-in-a-multi-threaded-environment
         */

        DateTimeFormatter inFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy H:mm:ss", Locale.ENGLISH);//thread-safety
        LocalDateTime out = LocalDateTime.parse("May 27, 2021 4:21:03", inFormatter);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd, yyyy H:mm:ss aaa", Locale.ENGLISH);//not-thread-safety
        Date date = simpleDateFormat.parse("May 27, 2021 4:21:03 PM");
        Timestamp timestamp = new Timestamp(date.getTime());
        long l = timestamp.toInstant().toEpochMilli();
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
        Date date1 = simpleDateFormat1.parse("May 27, 2021");
        Timestamp timestamp1 = new Timestamp(date1.getTime());
        long l1 = timestamp1.toInstant().toEpochMilli();

    }

    @Test
    public void customObjectMapper() throws JsonProcessingException {
        //https://stackoverflow.com/questions/4024544/how-to-parse-dates-in-multiple-formats-using-simpledateformat
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
//        objectMapper.registerModule(new JavaTimeModule());

        SimpleModule module = new SimpleModule();
        module.addSerializer(Timestamp.class, new CustomTimestampSerializer());
        module.addDeserializer(Timestamp.class, new CustomTimestampDeserializer());
        module.addSerializer(Date.class, new CustomDateSerializer());
        module.addDeserializer(Date.class, new CustomDateDeserializer());
        objectMapper.registerModule(module);

        TimeStampDateEntity timeStampDateEntity = objectMapper.readValue("{\"timestamp1\":\"May 27, 2021 4:21:03 PM\",\"timestamp2\":\"May 15, 2019 4:10:03AM\",\"date\":\"May 27, 2021\"}", TimeStampDateEntity.class);
        String s = objectMapper.writeValueAsString(timeStampDateEntity);
        TimeStampDateEntity timeStampDateEntity1 = objectMapper.readValue(s, TimeStampDateEntity.class);

    }

    @Test
    public void customObjectMapper1() throws JsonProcessingException {
        //https://www.javaguides.net/2019/04/jackson-list-set-and-map-serialization-and-deseialization-in-java-example.html

        // Create ObjectMapper object.
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        String json = "[ \"C\", \"C++\", \"Java\", \"Java EE\", \"Python\", \"Scala\", \"JavaScript\" ]";

        Set<String> progLangs = mapper.readValue(json, Set.class);
        System.out.println(progLangs.toString());
    }

    @Data
    private static class TimeStampDateEntity implements Serializable {
        private Timestamp timestamp1;
        private Timestamp timestamp2;
        private Date date;


        //for Serializable
        public TimeStampDateEntity() {
        }
    }

    @Test
    public void dateTimeFormatter() throws ParseException {
        //https://help.sumologic.com/03Send-Data/Sources/04Reference-Information-for-Sources/Timestamps%2C-Time-Zones%2C-Time-Ranges%2C-and-Date-Formats
        String timeTest = "2021-04-19T09:30:10.335Z";

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date date = simpleDateFormat.parse(timeTest);
        System.out.println(date);
        DateTimeFormatter inFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        DateTimeFormatter outFormatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime out = LocalDateTime.parse(timeTest, inFormatter);
        String formattedDate = out.format(outFormatter);
        System.out.println(formattedDate);

        OffsetDateTime offsetDateTime = OffsetDateTime.parse(timeTest);
        Date date1 = Date.from(offsetDateTime.toInstant());
        System.out.println(date1);

        ZonedDateTime zonedDateTime = date.toInstant().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("America/Chicago"));
        System.out.println(zonedDateTime);
        System.out.println(inFormatter.format(zonedDateTime));
        String dateStr = zonedDateTime.format(inFormatter);

        Date date2 = simpleDateFormat.parse(dateStr);
        System.out.println(date2);
        System.out.println(date);
        ZonedDateTime zonedDateTimeFromStr = ZonedDateTime.ofInstant(date.toInstant(), ZoneOffset.UTC);
        System.out.println(zonedDateTimeFromStr);

        System.out.println(dateStr);
    }

    @Test
    public void convertZoneDateTime() {
        /**
         * https://www.baeldung.com/java-zone-offset
         * https://stackoverflow.com/questions/49853999/convert-zoneddatetime-to-localdatetime-at-time-zone
         */
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);
        LocalDateTime time = convertZoneDateTime(now, ZoneId.systemDefault(), ZoneOffset.UTC);
        System.out.println(time);

        Date input = new Date();
        Instant instant = input.toInstant();
        Date date = Date.from(instant);
        System.out.println(date);
        ZonedDateTime zonedDateTime = convertZoneDateTime(date, ZoneId.of("UTC"));
        System.out.println(zonedDateTime);
//        Date from = Date.from(zonedDateTime.withZoneSameLocal(ZoneId.of("Asia/Jerusalem")).toInstant());
        Date from1 = Date.from(zonedDateTime.withZoneSameLocal(ZoneId.of("UTC")).toInstant());
//        System.out.println(from);
        System.out.println(from1);
    }

    public static LocalDateTime convertZoneDateTime(LocalDateTime localDateTime, ZoneId fromZoneId, ZoneId toZoneId) {
        return localDateTime.atZone(fromZoneId).withZoneSameInstant(toZoneId).toLocalDateTime();
    }

    public static ZonedDateTime convertZoneDateTime(Date date, ZoneId toZoneId) {
        return ZonedDateTime.ofInstant(date.toInstant(), toZoneId);
    }


    @Test
    public void getStartAndEndOfQuarterAndConvertDateToLocalDateTest() {
        LocalDate localDate = LocalDate.now(ZoneOffset.UTC);
        Date date = Date.from(localDate.atStartOfDay(ZoneOffset.UTC).toInstant());
        Timestamp timestamp = new Timestamp(date.getTime());
        long l = timestamp.toInstant().toEpochMilli();
        LocalDate firstDayOfQuarter = localDate.with(localDate.getMonth().firstMonthOfQuarter())
                .with(TemporalAdjusters.firstDayOfMonth());

        LocalDate lastDayOfQuarter = firstDayOfQuarter.plusMonths(2)
                .with(TemporalAdjusters.lastDayOfMonth());
        LocalDate currentDate = date.toInstant().atZone(ZoneOffset.UTC).toLocalDate();

        if (currentDate.isAfter(firstDayOfQuarter) && currentDate.isBefore(lastDayOfQuarter)) {
            int i = 0;
        }
        long between = DAYS.between(LocalDate.now(ZoneOffset.UTC).minusDays(1), LocalDate.now(ZoneOffset.UTC));
    }

    @Test
    public void convertLocalDateTimeToEpochMilliAndViceVersa() {
        //https://stackoverflow.com/questions/35183146/how-can-i-create-a-java-8-localdate-from-a-long-epoch-time-in-milliseconds/35187046
        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
//        LocalDate date = Instant.ofEpochMilli(longValue).atZone(ZoneId.systemDefault()).toLocalDate();
        long l = now.toInstant(ZoneOffset.UTC).toEpochMilli();
        LocalDateTime date1 = LocalDateTime.ofInstant(Instant.ofEpochMilli(l), ZoneOffset.UTC);
        Assert.assertEquals(now, date1);


        LocalDateTime.of(1986, Month.APRIL, 8, 12, 30);
    }

    @Test
    public void durationTest() {
        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);

        Duration between = Duration.between(now, now.plusYears(200));
        Duration duration = Duration.ofDays(5);
        int i = between.compareTo(duration);
    }


}

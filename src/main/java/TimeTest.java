import org.junit.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

import static java.time.temporal.ChronoUnit.DAYS;


public class TimeTest {

    @Test
    public void test(){
        Duration duration = Duration.ofHours(2);
    }

    @Test
    public void getStartAndEndOfQuarterAndConvertDateToLocalDateTest(){
        LocalDate localDate = LocalDate.now(ZoneOffset.UTC);
        Date date = Date.from(localDate.atStartOfDay(ZoneOffset.UTC).toInstant());

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
}

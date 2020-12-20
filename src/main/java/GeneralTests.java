import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

public class GeneralTests {

    @Test
    public void test1() {
        String dateStr = "2020-12-17";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(dateStr, dateTimeFormatter);

        String s = "sdf/dskkj/2020/2020-10-23/hghfd.csv";
        String s2 = StringUtils.substringBeforeLast(s, ".");
        String s1 = StringUtils.substringBeforeLast(s, "/");
        String result = StringUtils.substringAfterLast(s1, "/");
        long between = DAYS.between(LocalDate.now(ZoneOffset.UTC).minusDays(1), LocalDate.now(ZoneOffset.UTC));
    }

    @Test
    public void test2() {
        List<String> strings = Arrays.asList("hhf/ds", "df/ff/f");
        System.out.println(Arrays.toString(strings.toArray()));
        strings = strings.stream().map(e -> e + ".csv").collect(Collectors.toList());
        System.out.println(Arrays.toString(strings.toArray()));
    }

    @Test
    public void test3() {
        List<String> strings = Arrays.asList("1/aaa/ddd");
        Map<String,String> map = new HashMap<>();
        List<String> collect = strings.stream().map(s -> map.put(s, s.toUpperCase())).collect(Collectors.toList());
        System.out.println(map);
        System.out.println(Arrays.toString(collect.toArray()));

    }
}

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class MainTraining {

    public static void main(String[] args) {
////        String formula= new String("{\"x1\":0.2, \"x2\":0.8,\"y1\":30d,\"y2\":150d, \"x\":0.1}");


        checkTicksDeviation();


        List<Integer> integers = hashMap();
        integers.add(1);
        integers.add(3);
        integers.add(2);
        Collections.sort(integers);

        Collections.reverse(integers);

        Double x = 0d;
        if (x.equals(0d)) {
            Double y = x / 0;
            Double z = 0d / 1d;
        }

        Duration duration = Duration.ZERO;
        boolean b = duration.getSeconds() == 0;

        if (x < 1) {
//            Double y = x / 0;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        StraightLineEquation straightLineEquation = new StraightLineEquation(0.2, 0.8, 30, 150, 0.1);
        String formula1 = null;
        try {
            formula1 = objectMapper.writeValueAsString(straightLineEquation);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        ObjectMapper objectMapper1 = new ObjectMapper();

        try {
            straightLineEquation = objectMapper1.readValue(formula1, StraightLineEquation.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        double calc = straightLineEquation.getY();
        Writer writer = null;

        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("filename.txt"), StandardCharsets.UTF_8));
            writer.write(straightLineEquation.toString());
        } catch (IOException ex) {
            // Report
        } finally {
            try {
                writer.close();
            } catch (Exception ex) {/*ignore*/}
        }
//        System.out.println(straightLineEquation.toString());
////        Double a = 1.222222;
////        double round = Math.round(a*100d)/100d;
////        DecimalFormat decimalFormat = new DecimalFormat("##.00");
////        String format = decimalFormat.format(a);
////        BigDecimal aa = new BigDecimal("123.13698");
////        double s = aa.setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
////        ArrayList<Integer> integersMain = new ArrayList<>();
////        ArrayList<Integer> integersB = new ArrayList<>();
////        ArrayList<Integer> integersC = new ArrayList<>();
////        ArrayList<Integer> integersD = new ArrayList<>();
////        integersD.add(1);
////        integersB.add(3);
////        integersMain.addAll(integersB);
////        integersMain.addAll(integersB);
////        integersMain.add(null);
////        integersMain.add(4);
////
////        integersMain.addAll(integersC);
////        integersB.add(5);
////        int size = integersMain.size();
////        integersD.add(2);
////
////        for (int i = 0; i <= 10; i++) {
////            System.out.println(10 - i);
////            System.out.println(i);
////        }
////
////
////        StringBuilder sb = new StringBuilder();
////        List<Pair<Boolean, Boolean>> pairs = new ArrayList<>();
////        pairs.add(new Pair<>(false, false));
////        pairs.add(new Pair<>(false, true));
////        pairs.add(new Pair<>(true, false));
////        pairs.add(new Pair<>(true, true));
////
////        for (int i = 0; i < pairs.size(); i++) {
////            for (int j = 0; j < pairs.size(); j++) {
////                Pair<Boolean, Boolean> pair1 = pairs.get(i);
////                Pair<Boolean, Boolean> pair2 = pairs.get(j);
////                boolean result1 = pair1.getKey() && pair1.getValue() || pair2.getKey() && pair2.getValue();
////                boolean result2 = (pair1.getKey() && pair1.getValue()) || (pair2.getKey() && pair2.getValue());
////                if (result1 != result2)
////                    System.out.println("Failure");
////                sb.append(System.lineSeparator());
////                sb.append(String.format("%s %s", result1, result2));
////            }
////        }
////        System.out.println(sb.toString());
////
//
////      double  maxDiffDeviation = 5;
////        Duration expectedDuration = Duration.ofMillis(800);
////        Duration actualDuration = Duration.ofMillis(800);
////        long l = expectedDuration.toMillis()*100 / actualDuration.toMillis()*100/100;
////       if (Math.min(expectedDuration.toMillis(), actualDuration.toMillis())*100 / Math.max(expectedDuration.toMillis(), actualDuration.toMillis()) >= (100 - maxDiffDeviation)){
////           int c =0;
////       }
//
//        Duration duration = Duration.ofDays(2).plusSeconds(20);
//        long seconds = duration.getSeconds();
////        double v = convertDurationToAmountByFlowRate(Duration.ofMinutes(60), 30);
////        deleteRowsFromFile();
////        breakAndContinue();
////        calender();
////        calcDouble();
////        checkTime();
//    }
//
//    private static ArrayList<Integer> getIntegers() {
//        ArrayList<Integer> integers = new ArrayList<>();
//        integers.add(1);
//        integers.add(1);
//        return null;
//    }
//
//
//    public static double convertDurationToAmountByFlowRate(Duration duration, double flowRate) {
//        if (duration.isZero()) return 0d;
//        double quantity = duration.getSeconds() / flowRate;
//        return quantity;
//    }
//
    }

    private static List<Integer> hashMap() {
        HashMap<Integer, Integer> integerIntegerHashMap = new HashMap<>();

        Integer integer = integerIntegerHashMap.get(0);
        if (integer == null) return new ArrayList<>();
        ArrayList<Integer> integers = new ArrayList<>(integer);
        return integers;
//        Collection<Integer> values = integerIntegerHashMap.values();

//        List<Integer> rulesDataList = new ArrayList<>(values);

    }

    private static void checkTicksDeviation() {
        String WMPathFile = String.format("%snetafim%1$s%s", File.separator, "WM.txt");
        String FMPathFile = String.format("%snetafim%1$s%s", File.separator, "FM.txt");

        List<Tick> wmTicks = getTicksLog(WMPathFile);
        for (Tick tick : wmTicks) {
            tick.setWaterMeter(true);
        }

        List<Tick> fmTicks = getTicksLog(FMPathFile);
        for (Tick tick : wmTicks) {
            tick.setIsfertilizerMeter(true);
        }
        List<Tick> allTicks = new ArrayList<>();
        allTicks.addAll(wmTicks);
        allTicks.addAll(fmTicks);

        allTicks.sort(Comparator.comparing(tick -> tick.getLocalDateTime()));
        List<Tick> waterTicks = new ArrayList<>();
        List<Tick> fertilizerTicks = new ArrayList<>();

        LocalDateTime startTime = wmTicks.get(0).getLocalDateTime();
        Duration checkDeviationEvery = Duration.ofMinutes(5);
        LocalDateTime timeCheck = startTime.plus(checkDeviationEvery);
        StringBuilder sb = new StringBuilder();
        for (Tick tickEvent : allTicks) {

            if (tickEvent.isWaterMeter())
                waterTicks.add(tickEvent);
            else if (!tickEvent.isIsfertilizerMeter())
                fertilizerTicks.add(tickEvent);

            if (tickEvent.getLocalDateTime().isAfter(timeCheck)) {
                double deviation = 1 - (double) fmTicks.size() / wmTicks.size();
                sb.append(String.format("timeCheck %s, ActualStartDateTime %s, deviation is %s, total WM ticks %s, total FM ticks %s",
                        timeCheck, tickEvent.getLocalDateTime(),
                        deviation, waterTicks.size(), fertilizerTicks.size())).append(System.lineSeparator());
                timeCheck = timeCheck.plus(checkDeviationEvery);
            }
        }
        double deviation = 1 - (double) fertilizerTicks.size() / waterTicks.size();
        sb.append(String.format("total waterMeterTickEventsSize %s, total fertilizerMeterTickEventsSize %s, startCheck %s, deviation is %s",
                waterTicks.size(), fertilizerTicks.size(), timeCheck, deviation));
        System.out.println(sb.toString());
    }

    public static boolean isNullOrWhiteSpace(String string) {
        if (string == null)
            return true;

        for (int index = 0; index < string.length(); index++) {
            if (!Character.isWhitespace(string.charAt(index)))
                return false;
        }

        return true;
    }

    public static List<Tick> getTicksLog(String filePath) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filePath));
            List<Tick> events = new ArrayList<>();
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                if (isNullOrWhiteSpace(line))
                    continue;
                int tickCounter = Integer.parseInt(line.substring(line.lastIndexOf("counter: ") + 9));
                String date = line.substring(line.indexOf(": ") + 2, line.lastIndexOf(" - ") - 4);
                LocalDateTime localDateTime = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
                Tick tick = new Tick(tickCounter, localDateTime);
                events.add(tick);

            }
            return events;
        } catch (IOException e) {

        } finally {
            try {
                if (br != null)
                    br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
//
//    private static void deleteRowsFromFile() {
//        String textToDeleteRow = "TRACE";
//        String filePath = File.separator + "netafim" + File.separator + "logs" + File.separator + "hydroModule" + File.separator;
//        String fileName = "TraceToException.log.2";
//
//        BufferedReader br = null;
//        BufferedWriter bw = null;
//
//        try {
//            br = new BufferedReader(new FileReader(filePath + fileName));
//            bw = new BufferedWriter(new FileWriter(filePath + fileName + "copy"));
//
//            String line;
//            while ((line = br.readLine()) != null) {
//                if (!line.contains(textToDeleteRow)) {
//                    bw.append(line);
//                    bw.newLine();
//                }
//            }
//            br.close();
//            bw.close();
//
//        } catch (IOException e) {
//            System.out.println("IOException" + e.getMessage());
//        } finally {
//            try {
//                if (br != null)
//                    br.close();
//                if (bw != null)
//                    bw.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    private static void breakAndContinue() {
//        for (int i = 0; i < 10; i++) {
//            for (int j = 0; j < 10; j++) {
//                if (j == 1) {
//                    break;
//                }
//                if (i == 3) {
//                    continue;
//                }
//            }
//        }
//    }
//
//    private static void calender() {
//        Calendar c = Calendar.getInstance();
//        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
//
//        DayOfWeek[] values = DayOfWeek.values();
//        int length = values.length;
//
//    }
//
//    private static void calcDouble() {
//        Double d = 0.0;
//        if (d.equals(0d)) {
//            System.out.println("number is flout");
//
//        }
//        Double aDouble = new Double(51.1);
//        if (aDouble % 1 != 0) {
//            System.out.println("number is flout");
//        } else {
//            System.out.println("number is Integer");
//        }
//    }
//
//    private static void checkTime() {
//        LocalTime fromTime = LocalTime.parse("10:00");
//        Duration minimumRestTimeDuration = Duration.ofMinutes(30);
//        LocalTime fromTime1 = fromTime.plus(minimumRestTimeDuration);
//        LocalTime toTime = LocalTime.parse("11:00");
//        LocalTime now = LocalTime.now();
//        Duration fromTimeBetweenNow = Duration.between(fromTime1, now);
//        Duration toTimeBetweenNow = Duration.between(now, toTime);
//        if (fromTimeBetweenNow.isNegative() || toTimeBetweenNow.isNegative()) {
//            System.out.println("now time is not between time range");
//        } else {
//            System.out.println("now time is between time range");
//        }
//
//        LocalTime minimumRestTime = fromTime.plus(minimumRestTimeDuration);
//        Duration remainingTimeUntilEndMinRestTimeDuration = Duration.between(now, minimumRestTime);
//        if (!remainingTimeUntilEndMinRestTimeDuration.isNegative()) {
//            System.out.println("111now time is not between time range");
//        }
//    }
//    }
}



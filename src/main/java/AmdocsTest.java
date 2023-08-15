import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.junit.Assert;
import org.junit.Test;
import overlapping.ranges.EdgeTimeValue;
import overlapping.ranges.PeriodTimeResult;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class AmdocsTest {

    class Node {
        int value;
        Node right;
        Node left;

        public Node(int value) {
            this.value = value;
        }
    }


    class EvolvenNode {
        int value;
        List<EvolvenNode> children;

        public EvolvenNode(int value) {
            this.value = value;
        }
    }

    private void mirror(EvolvenNode node) {
        if (node == null || node.children == null)
            return;
        //replace children
        for (int i = 0; i < node.children.size() / 2; i++) {
            EvolvenNode evolvenNode = node.children.get(i);
            EvolvenNode last = node.children.get(node.children.size() - (i + 1));
            node.children.set(i, last);
            node.children.set(node.children.size() - (i + 1), evolvenNode);
        }

        for (EvolvenNode child : node.children) {
            mirror(child);
        }

    }

    @Test
    public void evolvenMirrorTreeWithMultipleChildTest() {
        EvolvenNode node = new EvolvenNode(1);
        node.children = Arrays.asList(new EvolvenNode(2), new EvolvenNode(3), new EvolvenNode(4));
        node.children.get(0).children = Arrays.asList(new EvolvenNode(21), new EvolvenNode(22), new EvolvenNode(23), new EvolvenNode(24), new EvolvenNode(25));
        node.children.get(1).children = Arrays.asList(new EvolvenNode(31), new EvolvenNode(32), new EvolvenNode(33));
        node.children.get(2).children = Arrays.asList(new EvolvenNode(41), new EvolvenNode(42), new EvolvenNode(43));

        mirror(node);

        Assert.assertEquals(4, node.children.get(0).value);
        Assert.assertEquals(3, node.children.get(1).value);
        Assert.assertEquals(2, node.children.get(2).value);

        Assert.assertEquals(43, node.children.get(0).children.get(0).value);
        Assert.assertEquals(42, node.children.get(0).children.get(1).value);
        Assert.assertEquals(41, node.children.get(0).children.get(2).value);

    }

    @Test
    public void mirrorNodeTest() {
        Node node = new Node(2);
        node.left = new Node(1);
        node.right = new Node(3);
        node.right.left = new Node(5);
        node.right.right = new Node(4);
        mirror(node);
    }

    private void mirror(Node node) {
        if (node == null)
            return;
        Node rightTemp = node.right;
        node.right = node.left;
        node.left = rightTemp;
        mirror(node.left);
        mirror(node.right);
    }

    @Test
    public void nodeTest() {
        Node node = new Node(1);
        node.left = new Node(2);
        node.right = new Node(3);
        node.right.left = new Node(4);

        printNodes(node);

        System.out.println();
        //הדפס רק את העומק של העץ הנתון
        printNodes(node, 1, 0);

    }


    private void printNodes(Node node) {
        if (node == null)
            return;
        printNodes(node.left);
        printNodes(node.right);
        System.out.println(node.value);
    }

    private void printNodes(Node node, int depthLevel, int currentLevel) {
        if (node == null || currentLevel > depthLevel)
            return;
        printNodes(node.left, depthLevel, currentLevel + 1);
        printNodes(node.right, depthLevel, currentLevel + 1);
        if (currentLevel == depthLevel)
            System.out.println(node.value);
    }


    private long timeInterval = 60;
    private Duration timeInterval1 = Duration.ofMinutes(1);

    @Test
    public void immutableStringTest() {
        String a = "a";
        String b = "a";
        Assert.assertTrue(a.equals(b));
        Assert.assertTrue(a == b);
        String c = new String("a");
        Assert.assertTrue(a.equals(c));
        Assert.assertFalse(a == c);
    }

    @Test
    public void test() {
        List<String> stringList = Arrays.asList("a", "b");
        AtomicInteger counter = new AtomicInteger();
        Map<Integer, String> map = stringList
                .stream()
                .collect(Collectors.toMap((c) -> counter.incrementAndGet(), (c) -> c));
        System.out.println(map);
    }

    @Test
    public void yairTest() throws IOException {
        Runtime.getRuntime().exec("notepad");//will open a new notepad
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= 100; i++) {
            String s = String.valueOf(i);
            sb.append(i + ":");
            boolean three = false;
            boolean fine = false;
            boolean seven = false;
            for (String c : s.split("")) {
                if (!three && (c.equals("3") || i % 3 == 0)) {
                    sb.append(" bim");
                    three = true;
                }

                if (!fine && (c.equals("5") || i % 5 == 0)) {
                    sb.append(" bam");
                    fine = true;
                }

                if (!seven && (c.equals("7") || i % 7 == 0)) {
                    sb.append(" bum");
                    seven = true;
                }
            }
            sb.append(System.lineSeparator());
        }
        System.out.println(sb);
    }


    /**
     * נתון רשימה של תוכניות טלוויזיה עם זמן התחלה וסיום לכל תוכנית.
     * יש למצוא את פרק הזמן בו ישנן הכי הרבה תוכניות ולהחזיר מהן ואת משך הזמן
     */


    @Test
    public void findMaxProgramAtPeriodTimeTest() {
        List<Program> programs = new ArrayList<>();

        programs.add(new Program("program 1", LocalDateTime.of(2023, 8, 13, 0, 0),
                LocalDateTime.of(2023, 8, 13, 0, 25)));
        programs.add(new Program("program 2", LocalDateTime.of(2023, 8, 13, 0, 10),
                LocalDateTime.of(2023, 8, 13, 0, 25)));
        programs.add(new Program("program 3", LocalDateTime.of(2023, 8, 13, 0, 20),
                LocalDateTime.of(2023, 8, 13, 0, 30)));

        programs.add(new Program("program 4", LocalDateTime.of(2023, 8, 13, 1, 0),
                LocalDateTime.of(2023, 8, 13, 1, 25)));
        programs.add(new Program("program 5", LocalDateTime.of(2023, 8, 13, 1, 10),
                LocalDateTime.of(2023, 8, 13, 1, 25)));
        programs.add(new Program("program 6", LocalDateTime.of(2023, 8, 13, 1, 20),
                LocalDateTime.of(2023, 8, 13, 1, 30)));

        List<ProgramResult> maxProgramsAtPeriodTime = findMaxProgramAtPeriodTime(programs);
        System.out.println(maxProgramsAtPeriodTime);
    }

    private List<ProgramResult> findMaxProgramAtPeriodTime(List<Program> programs) {
        Map<LocalDateTime, Set<String>> timeInMinutesToProgramNamesMap = new TreeMap<>();

        for (Program program : programs) {
            for (LocalDateTime time = program.startTime; time.isBefore(program.endTime); time = time.plusMinutes(1)) {
                timeInMinutesToProgramNamesMap.merge(time, Sets.newHashSet(program.name), (existingNames, newNames) -> {
                    existingNames.addAll(newNames);
                    return existingNames;
                });
            }
        }

        int max = getMaxProgramAtPeriod(timeInMinutesToProgramNamesMap);

        //filter all max programs in a period time
        List<Map.Entry<LocalDateTime, Set<String>>> results = timeInMinutesToProgramNamesMap.entrySet()
                .stream()
                .filter(entry -> entry.getValue().size() == max)
                .collect(Collectors.toList());


        for (Map.Entry<LocalDateTime, Set<String>> entry : results) {
            System.out.println(entry);
        }


        //grouping by program names
        Map<Set<String>, List<Map.Entry<LocalDateTime, Set<String>>>> groupedByProgramNames = results.stream()
                .collect(Collectors.groupingBy(Map.Entry::getValue, Collectors.toList()));

        //build a result with start and end period time
        List<ProgramResult> programResults = new ArrayList<>();
        groupedByProgramNames.forEach((key, value) -> {
            programResults.add(new ProgramResult(value.get(0).getKey(), value.get(value.size() - 1).getKey(), key));
        });

        return programResults;
    }

    private static int getMaxProgramAtPeriod(Map<LocalDateTime, Set<String>> timeInMinutesToProgramNamesMap) {
        //        Map.Entry<LocalDateTime, Set<String>> max = Collections.max(timeInMinutesToProgramNamesMap.entrySet(), Comparator.comparingInt(entry -> entry.getValue().size()));
//        Map.Entry<LocalDateTime, Set<String>> max = Collections.max(timeInMinutesToProgramNamesMap.entrySet(), Comparator.comparing(entry -> entry.getValue().size()));
        return timeInMinutesToProgramNamesMap.entrySet()
                .stream()
                .max((entry1, entry2) -> entry1.getValue().size() > entry2.getValue().size() ? 1 : -1)
                .get()
                .getValue()
                .size();
    }

    record ProgramResult(LocalDateTime start, LocalDateTime end, Set<String> programNames) {
    }

    record Program(String name, LocalDateTime startTime, LocalDateTime endTime) {
    }


    /**
     * בכביש  בו נוסעות מכוניות -מותקנת מצלמה המזהה את המספר ומוסיפה את השעה והרכב למאגר
     * נדרש להציג את תחילת פרק הזמן שבו עברו מס' המכוניות הגדול ביותר
     * <p>
     * (אפשר לבנות מפה לכל מקטע ולהחזיק
     * הכל בזיכרון)
     * אך זה תופס הרבה זיכרון.
     * צריך לשמור בכל פעם רק את ה period ואז לבדוק את הפריוד הבא - רק אם המקס' רכבים בו גדול יותר - לדרוס את המפה. וכן הלאה.
     */
    @Test
    public void carTest() {
        List<CarTime> carTimeList = new ArrayList<>();
        carTimeList.add(new CarTime(0, "12345678"));
        carTimeList.add(new CarTime(1, "12345678"));
        carTimeList.add(new CarTime(3, "12345678"));
        carTimeList.add(new CarTime(93, "12345678"));
        carTimeList.add(new CarTime(94, "12345678"));
        carTimeList.add(new CarTime(97, "12345678"));
        carTimeList.add(new CarTime(123, "12345678"));
        long startTimeStampThatPassedMaxCar = findMaxPassedCarsInPeriodTimeInterval(carTimeList);
        System.out.println(String.format("the period time with highest moving cars is from %s until %s", startTimeStampThatPassedMaxCar, startTimeStampThatPassedMaxCar + timeInterval));
    }

    @Test
    public void carOptimisticMemoryTest() {
        List<CarTime1> carTimeList = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        carTimeList.add(new CarTime1(now, "12345678"));
        carTimeList.add(new CarTime1(now.plusSeconds(1), "12345678"));
        carTimeList.add(new CarTime1(now.plusSeconds(3), "12345678"));
        carTimeList.add(new CarTime1(now.plusSeconds(93), "12345678"));
        carTimeList.add(new CarTime1(now.plusSeconds(94), "12345678"));
        carTimeList.add(new CarTime1(now.plusSeconds(97), "12345678"));
        carTimeList.add(new CarTime1(now.plusSeconds(123), "12345678"));
        long startTimeStampThatPassedMaxCarOptimisticMemory = findMaxPassedCarsInPeriodTimeIntervalOptimisticMemory(carTimeList);
    }

    private long findMaxPassedCarsInPeriodTimeIntervalOptimisticMemory(List<CarTime1> carTimeList) {
        Collections.sort(carTimeList, Comparator.comparing(CarTime1::getMovingTime));
        System.out.println(carTimeList);
        List<EdgeTimeValue> triplets = new ArrayList<>();
        for (CarTime1 carTime1 : carTimeList) {
            LocalDateTime start = carTime1.getMovingTime();
            LocalDateTime end = start.plus(timeInterval1);
            EdgeTimeValue startEdgeTimeValue = new EdgeTimeValue(start, 1, false);
            triplets.add(startEdgeTimeValue);
            EdgeTimeValue endEdgeTimeValue = new EdgeTimeValue(end, 1, true);
            triplets.add(endEdgeTimeValue);
        }
        Collections.sort(triplets, Comparator.comparing(EdgeTimeValue::getTime).thenComparing(EdgeTimeValue::isEndTime));
        System.out.println(triplets);
        long max = 0;
        PeriodTimeResult periodTimeResult = null;
        List<Integer> currentAccumulationS = new ArrayList<>();
        for (int i = 0; i < triplets.size(); i++) {
            EdgeTimeValue triplet = triplets.get(i);

            LocalDateTime timeTagN;

            if (triplet.isEndTime()) {
                currentAccumulationS.remove(triplet.getValue().get(0));
            } else {
                currentAccumulationS.add(triplet.getValue().get(0));
            }
            timeTagN = triplet.getTime();

            LocalDateTime timeTagM;

            if (i + 1 < triplets.size()) {
                EdgeTimeValue nextTriplet = triplets.get(i + 1);
                timeTagM = nextTriplet.getTime();
            } else {
                timeTagM = triplet.getTime();
            }

            /**
             *
             This answer doesn't take account of gaps (gaps should not appear in output), so I refined it: * If e=false, add a to S. If e=true, take away a from S. * Define n'=n if e=false or n'=n+1 if e=true * Define m'=m-1 if f=false or m'=m if f=true * If n' <= m' and (e and not f) = false, output (n',m',S), otherwise output nothing. – silentman.it
             */
            if (!timeTagN.isAfter(timeTagM) && i + 1 < triplets.size() && ((triplet.isEndTime() && !triplets.get(i + 1).isEndTime()) == false)) {
                periodTimeResult = new PeriodTimeResult(timeTagN, timeTagM, currentAccumulationS);
                System.out.println(periodTimeResult);
                if (currentAccumulationS.stream().mapToInt(Integer::intValue).sum() > max) {
                    max = currentAccumulationS.stream().mapToInt(Integer::intValue).sum();
                }
            }
        }
        if (periodTimeResult != null)
            System.out.println(String.format("the period time with highest moving cars (%s) is from %s until %s", max, periodTimeResult.getStart(), periodTimeResult.getEnd()));
        return max;
    }

    //מחזיר את הנקודה שבה מחחיל הזמן ממנו ועד סוף הtimeinterval עברו הכי הרבה מכוניות
    private long findMaxPassedCarsInPeriodTimeInterval(List<CarTime> carTimeList) {
        Collections.sort(carTimeList, Comparator.comparing(CarTime::getTimeStamp));
        CarTime first = carTimeList.get(0);
        CarTime last = carTimeList.get(carTimeList.size() - 1);
        Map<Long, Integer> totalCarsToStartPeriodTimeMap = new HashMap<>(); //for each timeInterval period
        for (long i = first.getTimeStamp(); i < last.getTimeStamp(); i++) {
            totalCarsToStartPeriodTimeMap.put(i, 0);
        }
        for (CarTime carTime : carTimeList) {
            long currentCarTime = carTime.timeStamp;
            for (Map.Entry<Long, Integer> snapshot : totalCarsToStartPeriodTimeMap.entrySet()) {
                //current time is between period time interval
                if (currentCarTime >= snapshot.getKey().longValue() && currentCarTime - timeInterval <= snapshot.getKey().longValue())
                    snapshot.setValue(snapshot.getValue() + 1);
            }
        }
        return Collections.max(totalCarsToStartPeriodTimeMap.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
    }

    public class CarTime1 {
        LocalDateTime movingTime;
        String carNumber;

        public CarTime1(LocalDateTime movingTime, String carNumber) {
            this.movingTime = movingTime;
            this.carNumber = carNumber;
        }

        public LocalDateTime getMovingTime() {
            return movingTime;
        }
    }

    public class CarTime {
        long timeStamp;
        String carNumber;

        public CarTime(long timeStamp, String carNumber) {
            this.timeStamp = timeStamp;
            this.carNumber = carNumber;
        }

        public long getTimeStamp() {
            return timeStamp;
        }

        public void setTimeStamp(long timeStamp) {
            this.timeStamp = timeStamp;
        }

        public String getCarNumber() {
            return carNumber;
        }

        public void setCarNumber(String carNumber) {
            this.carNumber = carNumber;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CarTime carTime = (CarTime) o;
            return timeStamp == carTime.timeStamp && Objects.equals(carNumber, carTime.carNumber);
        }

        @Override
        public int hashCode() {
            return Objects.hash(timeStamp, carNumber);
        }

        @Override
        public String toString() {
            return "CarTime{" +
                    "timeStamp=" + timeStamp +
                    ", carNumber='" + carNumber + '\'' +
                    '}';
        }
    }


    @Test
    public void scheduleTest() {


    }

    public class Schedule {
        //        private Map<Long, List<Task>> taskToTimeMap = new HashMap<>();
//        private long elapsedTime;
        private PriorityBlockingQueue<TaskTime> queue = new PriorityBlockingQueue(2, Comparator.comparing(TaskTime::getTaskExecutionTime));

        void schedule(Task task, LocalDateTime localDateTime) {
            queue.add(new TaskTime(task, localDateTime));
        }

        public void tick() {//this is called every second
            TaskTime taskTime = queue.peek();
            if (taskTime.getTaskExecutionTime().isBefore(LocalDateTime.now())) {
                queue.remove(taskTime);
            }
        }

//        void schedule(Task task, long time) {
//            if (taskToTimeMap.containsKey(time)) {
//                    taskToTimeMap.get(time).add(task);
//            } else {
//                List<Task> tasks = new ArrayList<>();
//                tasks.add(task);
//                taskToTimeMap.put(time, tasks);
//            }
//        }
//
//        public void tick() {//this is called every second
//            List<Task> tasks = taskToTimeMap.get(elapsedTime);
//            if (tasks != null) {
//                for (Task task : tasks) {
//                    //task.doExec();
//                }
//                taskToTimeMap.remove(elapsedTime);
//            }
//            elapsedTime++;
//        }
    }

    public class Task {

    }


    public class TaskTime {

        private Task task;
        private LocalDateTime taskExecutionTime;

        public TaskTime(Task task, LocalDateTime taskExecutionTime) {
            this.task = task;
            this.taskExecutionTime = taskExecutionTime;
        }

        public Task getTask() {
            return task;
        }

        public LocalDateTime getTaskExecutionTime() {
            return taskExecutionTime;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TaskTime taskTime = (TaskTime) o;
            return Objects.equals(task, taskTime.task) &&
                    Objects.equals(taskExecutionTime, taskTime.taskExecutionTime);
        }

        @Override
        public int hashCode() {
            return Objects.hash(task, taskExecutionTime);
        }

        @Override
        public String toString() {
            return "TaskTime{" +
                    "Task=" + task +
                    ", taskExecutionTime=" + taskExecutionTime +
                    '}';
        }
    }

}

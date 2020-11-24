import org.junit.Test;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.PriorityBlockingQueue;

public class AmdocsTest {

    class Node {
        int value;
        Node right;
        Node left;

        public Node(int value) {
            this.value = value;
        }
    }

    @Test
    public void nodeTest() {
        Node node = new Node(1);
        node.left = new Node(2);
        node.right = new Node(3);
        node.right.left = new Node(4);

        printNodes(node);

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


    /**
     * בכביש  בו נוסעות מכוניות -מותקנת מצלמה המזהה את המספר ומוסיפה את השעה והרכב למאגר
     * נדרש להציג את תחילת פרק הזמן שבו עברו מס' המכוניות הגדול ביותר
     * <p>
     * (אפשר לבנות מפה לכל מקטע ולהחזיק
     * הכל בזיכרון)
     * אך זה תופס הרבה זיכרון.
     * צריך לשמור בכל פעם רק את הperiod ואז לבדוק את הפריוד הבא - רק אם המקס' רכבים בו גדול יותר - לדרוס את המפה. וכן הלאה.
     */
    @Test
    public void carTest() {
        List<Shot> shotList = new ArrayList<>();
        shotList.add(new Shot(0, "12345678"));
        shotList.add(new Shot(1, "12345678"));
        shotList.add(new Shot(3, "12345678"));
        shotList.add(new Shot(120, "12345678"));
        long startTimeStampThatPassedMaxCar = findMaxPassedCarsInPeriodTimeInterval(shotList);
        System.out.println(String.format("%s עד %s פרק הזמן הכי עמוס בכביש (בו חלפו הכי הרבה מכוניות הוא מ ", startTimeStampThatPassedMaxCar, startTimeStampThatPassedMaxCar + timeInterval));
    }

    //מחזיר את הנקודה זבה מחחיל הזמן ממנו ועד סוף הtimeinterval עברו הכי הרבה מכוניות
    private long findMaxPassedCarsInPeriodTimeInterval(List<Shot> shotList) {
        Collections.sort(shotList, Comparator.comparing(Shot::getTimeStamp));

        Long timeResult;
        Integer carCountResult;
        Shot first = shotList.get(0);
        Shot last = shotList.get(shotList.size() - 1);
        Map<Long, Integer> totalCarsToStartPeriodTimeMap = new HashMap<>(); //for each timeInterval period
        for (long i = first.getTimeStamp(); i < last.getTimeStamp(); i++) {
            totalCarsToStartPeriodTimeMap.put(i, 0);
        }
        for (Shot shot : shotList) {
            long currentCarTime = shot.timeStamp;
            for (Map.Entry<Long, Integer> snapshot : totalCarsToStartPeriodTimeMap.entrySet()) {
                //current time is between period time interval
                if (currentCarTime >= snapshot.getKey().longValue() && currentCarTime - timeInterval <= snapshot.getKey().longValue())
                    //                 if (timeStamp >= entry.getKey().longValue() && entry.getKey().longValue() - timeInterval <= timeStamp)
                    snapshot.setValue(snapshot.getValue() + 1);
            }
        }
        return Collections.max(totalCarsToStartPeriodTimeMap.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
    }


    public class Shot {
        long timeStamp;
        String carNumber;

        public Shot(long timeStamp, String carNumber) {
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

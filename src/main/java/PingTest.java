import org.junit.Test;

import java.time.LocalDateTime;
import java.util.LinkedList;

public class PingTest {


    /**
     * Provide the implementation (code) for the following function:
     * <p>
     * void generateTable(int x, int n)
     * <p>
     * The expected result will be an output with numbers like a table where x - number of columns, and n cells containing numbers from 1 to n.
     * <p>
     * Example
     * for x = 3 and n = 8, the following result is expected
     * <p>
     * 1   2   3
     * 4   5   6
     * 7   8
     *
     *
     * select * from grades ;
     */

    @Test
    public void test1() {
        generateTable(3, 8);
    }


    private void generateTable(int colNum, int values) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= values; i++) {
            sb.append(i);
            if (i % colNum == 0) {
                sb.append(System.lineSeparator());
            }
            if (i < values)
                sb.append(" ");
        }

        System.out.print(sb);
    }


    @Test
    public void test() {
        /**
         *write a service that provide an interface with:
         * מקבל TASK להריץ בעוד X זמן מכל מיני שירותים ומבצע אותם כשמגיע הזמן
         *
         *פתרון:
         * נחשוף INTERFACE שמספק עם CALLBACK
         * הממשק יאכסן את הTASK ברשימה (LINKED LIST) ממויינת לפי זמן ריצה בסדר יורד
         * ירוץ נניח כל שניה ויבדוק את המיקום האחרון ברשימה, אם הזמן שלה גדול מהזמן הנוכחי ייצא מהלולאה, אחרת יקרא לCALLBASK בASYNC ויבצע את הACTION. יסיר את הרשומה מהליסט. ימשיך עד שהזמן הנוכחי קטן מהזמן של הTASK הבא. -
         */

        TaskSchedulerImpl taskScheduler = new TaskSchedulerImpl();
        CreateCallbackImpl1 createCallbackImpl1 = new CreateCallbackImpl1(1, taskScheduler, LocalDateTime.now());
        CreateCallbackImpl2 createCallbackImpl2 = new CreateCallbackImpl2("s", taskScheduler, LocalDateTime.now().plusSeconds(10));
//        createCallbackImpl1.invoke();

    }

    @FunctionalInterface
    public interface TaskScheduler<T> {
        void schedule(T t, LocalDateTime localDateTime);
    }

    public class TaskSchedulerImpl<T> implements TaskScheduler<T> {

        LinkedList<T> tLinkedList = new LinkedList<>();

        @Override
        public void schedule(T t, LocalDateTime localDateTime) {
            tLinkedList.add(t);
        }
    }

    @FunctionalInterface
    public interface CreateCallback<T> {
        void invoke(T t);
    }

    public class ExecutorTask<T> {
        private T callback;

        public T getCallback() {
            return callback;
        }

        public void setCallback(T callback) {
            this.callback = callback;
        }
    }

    public class CreateCallbackImpl1 extends ExecutorTask<Integer> implements CreateCallback<Integer> {
        public CreateCallbackImpl1(Integer integer, TaskScheduler taskScheduler, LocalDateTime localDateTime) {
            taskScheduler.schedule(integer, localDateTime);
        }

        @Override
        public void invoke(Integer integer) {
            System.out.println(integer.toString());
        }

    }

    public class CreateCallbackImpl2 implements CreateCallback<String> {
//        private TaskSchedulerImpl taskScheduler;

        public CreateCallbackImpl2(String string, TaskSchedulerImpl taskScheduler, LocalDateTime localDateTime) {
//            this.taskScheduler = taskScheduler;
            taskScheduler.schedule(string, localDateTime);
        }

        @Override
        public void invoke(String s) {
            System.out.println(s);
        }
    }


}

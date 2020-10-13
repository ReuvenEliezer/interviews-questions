import org.junit.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

import static sun.nio.cs.Surrogate.MAX;

public class ComparatorTest {


    @Test
    public void TreeSet_Test() {
        SortedSet<String> fruits = new TreeSet<>(Comparator.reverseOrder());
        Comparator<String> caseInsensitiveOrder = String.CASE_INSENSITIVE_ORDER;
    }


    @Test
    public void SortingList() {

        List<String> string = new ArrayList<>();
        string.add("aaa");
        string.add("aa");
        string.add("a");
        string.add("bbb");
//        Collections.sort(string);
//        Collections.reverse(string);
        Collections.sort(string, Comparator.comparing(String::length));
        Collections.reverse(string);


        string.forEach(System.out::println);
        Collections.sort(string, Comparator.comparing(String::length).reversed());
        string.forEach(plate -> System.out.println(plate));

    }

    @Test
    public void ReverseArray() {
        List<Integer> integers = new ArrayList<>();
        integers.add(1);
        integers.add(2);
        integers.add(3);
        integers.add(4);
        integers.add(5);
//        integers.add(6);


        integers.forEach(item -> System.out.println(item));

        int tempStart;
        int tempEnd;

        int size = integers.size();
        for (int i = 0; i < size / 2; i++) {
            tempStart = integers.get(i);
            int endIndex = size - (1 + i);
            tempEnd = integers.get(endIndex);
            integers.set(i, tempEnd);
            integers.set(endIndex, tempStart);

        }
        integers.forEach(item -> System.out.println(item));
//        integers.parallelStream().forEach(item -> System.out.println(item));
    }

    @Test
    public void ReverseArrayByRecursive() {
        List<Integer> integers = new ArrayList<>();
        integers.add(1);
        integers.add(2);
        integers.add(3);
        integers.add(4);
        integers.add(5);
        integers.add(6);

        integers.forEach(item -> System.out.println(item));

        int size = integers.size();
        int startIndex = 0;
        int endIndex = size - 1;
        integers = recursiveReverse(startIndex, endIndex, integers);
        integers.forEach(item -> System.out.println(item));
    }

    private List<Integer> recursiveReverse(int startIndex, int endIndex, List<Integer> array) {
        if (startIndex >= endIndex)
            return array;

        int tempStartValue = array.get(startIndex);
        int tempEndValue = array.get(endIndex);

        array.set(startIndex, tempEndValue);
        array.set(endIndex, tempStartValue);

        startIndex++;
        endIndex--;
        return recursiveReverse(startIndex, endIndex, array);
    }

    @Test
    public void ConcurrentHashMapVsHashMapTest() {
//        https://stackoverflow.com/questions/52193939/when-to-use-concurrenthashmap-vs-hashmap-for-put-and-get
//        The below code should put 2000 entries in each map from multiple threads. But for the HashMap, there will be consistently fewer entries than 2000 in the map after the operation, since some of the puts will clash with each other and their result will be lost.
        testIt(new HashMap<>());
        testIt(new ConcurrentHashMap<>());
    }

    public static void testIt(Map<Integer, Integer> map) {
        IntStream.range(0, 2000).parallel().forEach(i -> map.put(i, -1));
        System.out.println(map.size());
    }

    @Test
    public void HeapSortTest() {

    }
    

    @Test
    public void SortingObject() {
        List<Employee> employees = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        employees.add(new Employee(1010, "Rajeev", 100.00, now));
        employees.add(new Employee(1004, "Chris", 95.50, now.plusDays(2)));
        employees.add(new Employee(1015, "David", 134.00, now.plusDays(1)));
        Collections.sort(employees, Comparator.comparing(Employee::getSalary).reversed());
        employees.forEach(System.out::println);
    }


    @Test
    public void PriorityQueue() {
//        https://www.callicoder.com/java-priority-queue/

//        Comparator.comparingInt(String::length);
//        PriorityQueue<String> stringPriorityQueue = new PriorityQueue<>(Comparator.comparing(String::length));

        PriorityQueue<Employee> employeePriorityQueue = new PriorityQueue<>(Comparator.comparing(Employee::getSalary));

        LocalDateTime now = LocalDateTime.now();
        Employee rajeev = new Employee(1010, "Rajeev", 100.00, now);
        employeePriorityQueue.add(rajeev);
        employeePriorityQueue.add(new Employee(1004, "Chris", 950.50, now.plusDays(2)));
        employeePriorityQueue.add(new Employee(1015, "David", 134.00, now.plusDays(1)));
        employeePriorityQueue.remove(rajeev);
        rajeev.setSalary(500d);
        employeePriorityQueue.add(rajeev);
        while (!employeePriorityQueue.isEmpty()) {
            System.out.println(employeePriorityQueue.remove());
        }


        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(new Employee(1010, "e1", 100.00, LocalDateTime.now()));
        employeeList.add(new Employee(1010, "e2", 130.00, LocalDateTime.now()));
        employeeList.add(new Employee(1010, "e3", 150.00, LocalDateTime.now()));
        employeeList.add(new Employee(1010, "e4", 170.00, LocalDateTime.now()));

        Employee chris = new Employee(1004, "Chris", 145.50, LocalDateTime.now());
        int index = Collections.binarySearch(employeeList, chris);
        int insertionIndex = -1 * (index + 1);
        employeeList.add(insertionIndex, chris);
        System.out.println(insertionIndex);
    }

    @Test
    public void calc() {
        int x = 1;
        int y = 2;
        double result = x / y;
        double result1 = (double) x / y;
        System.out.println("result: " + result);
        System.out.println("result1: " + result1);
    }

    @Test
    public void ArrayList() {
//        Object o = null;
//        if (o == Boolean.FALSE) {
//            int i = 0;
//        }

        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(2);
        integers.add(1);
        integers.add(0);
        integers.add(0);
        System.out.println("integers size: " + integers.size());
        integers.remove(0);
        System.out.println("integers size: " + integers.size());

//        for (Iterator<Integer> iter = integers.iterator(); iter.hasNext(); ) {
//            System.out.println("iter: " + iter);
//        }
        //        integers.remove(0);

//        integers.remove(integers.get(1));
        int i1 = integers.lastIndexOf(0);
        System.out.println("lastIndexOf: " + i1);
        for (Integer i : integers) System.out.println(i);
    }

    class Watch {
        private long startTime;
        private long endTime;

        public void start() {
            startTime = System.nanoTime();
        }

        private void stop() {
            endTime = System.nanoTime();
        }

        public void totalTime(String s) {
            stop();
            System.out.println(s + (endTime - startTime));
        }
    }

    @Test
    public void arrayListAdd() {
        Watch watch = new Watch();
        ArrayList<String> strings = new ArrayList<>();
//        LinkedList<String> strings = new LinkedList<>();

        watch.start();
        for (String string : maxArray())
            strings.add(string);
        watch.totalTime("Array List add() = ");//152,46840 Nanoseconds
    }

    private String[] maxArray() {
        String[] strings = new String[MAX];
        Boolean result = Boolean.TRUE;
        for (int i = 0; i < MAX; i++) {
            strings[i] = getString(result, i);
            result = !result;
        }
        return strings;
    }

    private String getString(Boolean result, int i) {
        return String.valueOf(result) + i + String.valueOf(!result);
    }

    @Test
    public void Test1() {
        HashMap<Integer, ObjectClass> hashMap = new HashMap();
        ObjectClass objectClass = new ObjectClass(2, "new");
        hashMap.put(1, objectClass);
        ObjectClass objectClass1 = hashMap.get(1);
        objectClass1.setI(3);
        ObjectClass objectClass2 = hashMap.get(1);
//        Hashtable<Integer,String> hashtable = new Hashtable<>();
//        hashtable.put(null,null);

    }

    @Test
    public void undateMapTest() {
        Map<Integer, ObjectClass> hashMap = new HashMap();
        ObjectClass objectClass = new ObjectClass(2, "new");
        hashMap.put(1, objectClass);

        hashMap.get(1).setS("update");
        hashMap.entrySet().forEach(v -> System.out.println(v));
    }

    @Test
    public void InstanceOf_Vs_getClass_Performance_Test() {
        String a = new String();
        for (long i = 0; i < 1000000000; i++) {
            if (a.getClass().equals(String.class)) {
//            System.out.println("getClass = equals");
            }
        }
        for (int i = 0; i < 1000000000; i++) {

            if (a instanceof String) {
//            System.out.println("instanceof = equals");
            }
        }
    }

    @Test
    public void Test() {

        boolean aTrue = Boolean.parseBoolean("true");
        if (aTrue) {
            int x = 0;
        }

        LocalTime a = LocalTime.parse("08:00");
        LocalTime b = LocalTime.parse("07:00");
        LocalTime c = LocalTime.parse("09:00");
        List<LocalTime> localTimes = new ArrayList<>();
        localTimes.add(a);
        localTimes.add(b);
        localTimes.add(c);
        System.out.println("Before sorting");
        for (LocalTime localTime : localTimes) {
            System.out.println(String.format("LocalTime %s", localTime));
        }
        Collections.sort(localTimes);
        System.out.println("After sorting");
        for (LocalTime localTime : localTimes) {
            System.out.println(String.format("LocalTime %s", localTime));
        }
    }
}

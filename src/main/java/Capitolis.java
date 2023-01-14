import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class Capitolis {

    @Test
    public void createFileDirectoryArchitecture() {
        //create file directory architecture
        Directory mainDirectory = new Directory(new Prop("mainDirectory", null));
        File file = new File("content1".getBytes(), new Prop("file1", mainDirectory));
        file.addContent("addContent".getBytes());
        Assert.assertEquals("content1addContent", new String(file.content, StandardCharsets.UTF_8));
        File file2 = new File("content2".getBytes(), new Prop("file2", mainDirectory));
        List<Content> contents = mainDirectory.contents;
        Assert.assertEquals(2, contents.size());
        Directory subDirectory = new Directory(new Prop("subDirectory", mainDirectory));
        Assert.assertEquals(3, contents.size());
    }

    class Prop {
        String name;
        Directory parentDir;
        LocalDateTime createDateTime;
        LocalDateTime updatedDateTime;

        public Prop(String name, Directory parentDir) {
            this.name = name;
            this.parentDir = parentDir;
            this.createDateTime = LocalDateTime.now();
            this.updatedDateTime = this.createDateTime;
        }
    }

    abstract class Content {
        Prop prop;
    }

    class File extends Content {
        byte[] content;

        public File(byte[] content, Prop prop) {
            this.content = content;
            this.prop = prop;
            this.prop.parentDir.contents.add(this);
        }

        public void addContent(byte[] bytes) {
            try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
                output.write(this.content);
                output.write(bytes);
                this.content = output.toByteArray();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            this.prop.updatedDateTime = LocalDateTime.now();
        }

        public void replaceContent(byte[] bytes) {
            this.content = bytes;
            this.prop.updatedDateTime = LocalDateTime.now();
        }
    }

    class Directory extends Content {
        List<Content> contents = new ArrayList<>();

        public Directory(Prop prop) {
            this.prop = prop;
            if (this.prop.parentDir != null) {
                this.prop.parentDir.contents.add(this);
            }
        }
    }


    /**
     * https://www.geeksforgeeks.org/print-all-possible-combinations-of-r-elements-in-a-given-array-of-size-n/
     */

    @Test
    public void printAllCombinationsForPlacing20IdenticalBallsIn10Cells() {
        /**
         *
         * 20 0 0 0 0 0 0 0 0 0
         * 19 1 0 0 0 0 0 0 0 0
         * 19 0 1 0 0 0 0 0 0 0
         * .
         * 0 10 0 0 8 0 0 0 2 0
         * .
         * 0 0 0 0 0 0 0 0 0 20
         *
         * ----------
         *
         * 20 balls in 2 cells
         *
         * 20 0
         * 19 1
         * 18 2
         * .
         * 1 19
         * 0 20
         * ------------
         *
         * Pseudocode
         *public void printAllCombinations (int balls, int cells){
         * 	if (cells<0) return;
         * 	for (int i = 0; i<balls; i++){
         * 		print(balls-i)
         * 		printAllCombinations(i, cells-1);
         *        }
         * }
         *
         * 20 balls in 3 cells
         *
         * 20 0 0
         * 19 1 0
         * 19 0 1
         * 18 1 1
         * 18 2 0
         * .
         * 1 19
         * 0 20
         * ------------
         */

        StringBuilder sb = new StringBuilder();
        int cells = 5;
        int balls = 3;
        int[] arr = new int[cells];
        printAllCombinations(balls, cells, arr, sb);
        System.out.println(sb);
    }

    private void printArr(int[] arr, StringBuilder sb) {
        for (Integer i : arr) {
            sb.append(i).append(" ");
        }
        sb.append(System.lineSeparator());
    }

    private long threshold = 1000000;

    Map<Transaction, AtomicLong> transactionsAggMap = new ConcurrentHashMap<>();

    @Test
    public void test() {
        Transaction transaction1 = new Transaction(1l, "1", "1", LocalDate.now(), 100);
        Transaction transaction2 = new Transaction(2l, "1", "1", LocalDate.now(), 900);
        Transaction transaction3 = new Transaction(3l, "1", "1", LocalDate.now(), 200);
        Transaction transaction4 = new Transaction(4l, "2", "1", LocalDate.now(), 100);

        addToMap(transaction1);
        addToMap(transaction2);
        addToMap(transaction3);
        addToMap(transaction4);

        /**
         * init()  - load all aggregation that isHandled==false -> add to sender queue;
         *            load all transaction that isAggregate==false -> add to aggregation handler;
         * - received transaction - > get transactionId.equals(id) if exist-> return, else set receivedDateTime to now();
         * - save to db - transaction table(set save isAggregate  = false)
         * -       put to queue
         *          response entity
         *          set isHandled=true (in memory)
         * -  aggregation handler - generate aggregation if not exist
         *  -save to db aggregation table-> set isAggregation = done;
         *  - check aggregation value is over the threshold, if true - > put to sender queue.
         *  - tackScheduler every X millis while queue is not empty peek()>
         *      send aggregation.
         *      on response - > update the isHandled aggregation flag to true
         *      remove aggregation from queue and set isHandled =true;
         *
         */


    }

    private void addToMap(Transaction transaction) {
        long aggregatedValue = transactionsAggMap.computeIfAbsent(transaction, v -> new AtomicLong(0))
                .accumulateAndGet(transaction.quantity, (a, b) -> a + b);
        if (aggregatedValue >= threshold) {
            //TODO SEND and remove from map
            int i=0;
        }
    }

    static class Aggregation {
        Long id;
        String buyerId;
        String sellerId;
        LocalDate settlementDate;
        boolean isHandled;

    }

    static class Transaction {
        Long id;
        String buyerId;
        String sellerId;
        LocalDate settlementDate;
        Integer quantity;
        LocalDateTime receivedDateTime;
        boolean isHandled;

        public Transaction(Long id, String buyerId, String sellerId, LocalDate settlementDate, Integer quantity) {
            this.id = id;
            this.buyerId = buyerId;
            this.sellerId = sellerId;
            this.settlementDate = settlementDate;
            this.quantity = quantity;
            this.receivedDateTime = LocalDateTime.now();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Transaction that = (Transaction) o;
            return Objects.equals(buyerId, that.buyerId) && Objects.equals(sellerId, that.sellerId) && Objects.equals(settlementDate, that.settlementDate);
        }

        @Override
        public int hashCode() {
            return Objects.hash(buyerId, sellerId, settlementDate);
        }
    }

    public void printAllCombinations(int balls, int cells, int[] arr, StringBuilder sb) {
        if (cells == 0) {
            if (balls == 0) {
                printArr(arr, sb);
            }
            return;
        }
        for (int i = balls; i >= 0; i--) {
            arr[arr.length - cells] = i;
            printAllCombinations(balls - i, cells - 1, arr, sb);
        }
    }
}

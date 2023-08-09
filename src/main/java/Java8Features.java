import digibank.RuleEnum;
import org.junit.Test;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Java8Features {


    @Test
    public void test1(){
        Map<RuleEnum, String> map = new EnumMap<>(RuleEnum.class);
        final String string = map.toString();
    }

    @Test
    public void test() {
        boolean b = Boolean.TRUE == null;

        Supplier<String> s = () -> "{}";
        s.get();
    }

    @Test
    public void groupingBy() {
        List<Trade> trades = new ArrayList<>();
        trades.add(new Trade(1, 1, 2, LocalDate.now(), 100));
        trades.add(new Trade(2, 1, 2, LocalDate.now(), 100));

        trades.add(new Trade(3, 1, 2, LocalDate.now().minusDays(1), 100));
        trades.add(new Trade(4, 1, 2, LocalDate.now().minusDays(1), 100));

        trades.add(new Trade(5, 12, 2, LocalDate.now(), 100));

        Map<Integer, Map<Integer, Map<LocalDate, Integer>>> collect = trades.stream()
                .collect(Collectors.groupingBy(Trade::getBuyerId, Collectors.groupingBy(Trade::getSellerId, Collectors.groupingBy(Trade::getSettlementDate,
                        Collectors.summingInt(Trade::getQty)))));

        System.out.println("collect: " + collect);
        for (Map.Entry<Integer, Map<Integer, Map<LocalDate, Integer>>> entry : collect.entrySet()) {
            Integer key = entry.getKey();
            for (Map.Entry<Integer, Map<LocalDate, Integer>> entry1 : entry.getValue().entrySet()) {
                Integer key1 = entry1.getKey();
                for (Map.Entry<LocalDate, Integer> entry2 : entry1.getValue().entrySet()) {
                    Integer value = entry2.getValue();
                    LocalDate key2 = entry2.getKey();
                    System.out.print("      getBuyerId: " + key);
                    System.out.print("      getSellerId: " + key1);
                    System.out.print("      getSettlementDate: " + key2);
                    System.out.print("      getQty: " + value);
                    System.out.println();
                }
            }
        }
    }

    class Trade {
        int id;
        int buyerId;
        int sellerId;
        LocalDate settlementDate;
        int qty;

        public Trade(int id, int buyerId, int sellerId, LocalDate settlementDate, int qty) {
            this.id = id;
            this.buyerId = buyerId;
            this.sellerId = sellerId;
            this.settlementDate = settlementDate;
            this.qty = qty;
        }

        public int getId() {
            return id;
        }

        public int getBuyerId() {
            return buyerId;
        }

        public int getSellerId() {
            return sellerId;
        }

        public LocalDate getSettlementDate() {
            return settlementDate;
        }

        public int getQty() {
            return qty;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Trade trade = (Trade) o;
            return buyerId == trade.buyerId && sellerId == trade.sellerId && Objects.equals(settlementDate, trade.settlementDate);
        }

        @Override
        public int hashCode() {
            return Objects.hash(buyerId, sellerId, settlementDate);
        }
    }

}

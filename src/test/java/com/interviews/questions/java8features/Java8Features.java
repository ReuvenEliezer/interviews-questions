package com.interviews.questions.java8features;

import org.junit.Test;

import java.time.LocalDate;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Java8Features {


    /**
     * https://medium.com/swlh/understanding-java-8s-consumer-supplier-predicate-and-function-c1889b9423d
     */

    @Test
    public void whenNamesPresentConsumeAllTest() {
        Consumer<String> printConsumer = t -> System.out.println(t);
        Stream<String> cities = Stream.of("Sydney", "Dhaka", "New York", "London");
        cities.forEach(printConsumer);
    }

    @Test
    public void whenNamesPresentUseBothConsumerTest() {
        List<String> cities = Arrays.asList("Sydney", "Dhaka", "New York", "London");

        Consumer<List<String>> upperCaseConsumer = list -> {
            for (int i = 0; i < list.size(); i++) {
                list.set(i, list.get(i).toUpperCase());
            }
        };
        Consumer<List<String>> printConsumer = list -> list.stream().forEach(System.out::println);

        upperCaseConsumer.andThen(printConsumer).accept(cities);
    }


    @Test
    public void supplierTest() {
        Supplier<Double> doubleSupplier1 = () -> Math.random();
        DoubleSupplier doubleSupplier2 = Math::random;

        System.out.println(doubleSupplier1.get());
        System.out.println(doubleSupplier2.getAsDouble());
    }

    @Test
    public void supplierWithOptionalTest() {
        Supplier<Double> doubleSupplier = () -> Math.random();
        Optional<Double> optionalDouble = Optional.empty();
        System.out.println(optionalDouble.orElseGet(doubleSupplier));
    }


    @Test
    public void predicateTest() {
        List<String> names = Arrays.asList("John", "Smith", "Samueal", "Catley", "Sie");
        Predicate<String> nameStartsWithS = str -> str.startsWith("S");
        names.stream().filter(nameStartsWithS).forEach(System.out::println);
    }


    @Test
    public void predicateAndCompositionTest() {
        List<String> names = Arrays.asList("John", "Smith", "Samueal", "Catley", "Sie");
        Predicate<String> startPredicate = str -> str.startsWith("S");
        Predicate<String> lengthPredicate = str -> str.length() >= 5;
        names.stream().filter(startPredicate.and(lengthPredicate)).forEach(System.out::println);
    }

    @Test
    public void functionsTest() {
        List<String> names = Arrays.asList("Smith", "Gourav", "Heather", "John", "Catania");
        Function<String, Integer> nameMappingFunction = String::length;
        List<Integer> nameLength = names.stream().map(nameMappingFunction).collect(Collectors.toList());
        System.out.println(nameLength);
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

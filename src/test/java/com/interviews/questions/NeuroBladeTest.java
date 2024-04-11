package com.interviews.questions;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class NeuroBladeTest {

    @Test
    public void test() {
//        Ship big1 = new BigShip(); - we needs to call slow with value of 3
//            Ship small1 = new SmallShip();
//            Ship big2 = new BigShip(); - we needs to call slow with value of 1
//                   Ship small2 = new SmallShip();


        List<Ship> innerList = new ArrayList<>();
        innerList.add(new SmallShip());

        Ship bid2 = new BigShip(innerList);


        List<Ship> externalList = new ArrayList<>();
        externalList.add(new SmallShip());
        externalList.add(bid2);


        Ship root = new BigShip(externalList);

        root.move();

    }

    public interface Ship {
        int move();
    }

    class SmallShip implements Ship {

        @Override
        public int move() {
            System.out.println("SmallShip moving: 1");
            return 1;
        }

    }

    class BigShip implements Ship {
        List<Ship> shipList;

        public BigShip(List<Ship> shipList) {
            this.shipList = shipList;
        }

        @Override
        public int move() {
            int total = 1;
            for (Ship ship : shipList) {
                total += ship.move();
            }
            System.out.println("BigShip moving with: " + (total - 1));
            slow(total - 1);
            return total - 1;
        }

        void slow(int numOfShips) {
            //TODO
        }
    }

    class Shape {
        int x1;
        int y1;
        int x2;
        int y2;

        public Shape(int x1, int y1, int x2, int y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }
    }

    public static boolean isColliding(Shape shape1, Shape shape2) {
        if (shape1.x1 > shape2.x2 || shape1.x2 < shape2.x1)
            return false;
        if (shape1.y1 > shape2.y2 || shape1.y2 < shape2.y1)
            return false;
        return true;
    }
}

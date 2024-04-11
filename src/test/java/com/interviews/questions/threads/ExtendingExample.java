package com.interviews.questions.threads;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ScheduledExecutorService;

public class ExtendingExample {
    public static void main(String args[]) {

        com.interviews.questions.threads.Count cnt = new Count();

        try {
            while (cnt.isAlive()) {
                System.out.println("Main thread will be alive till the child thread is live");
                Thread.sleep(1500);
//                cnt.interrupt();
            }
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted");
        }
        System.out.println("Main thread's run is over");
    }
}

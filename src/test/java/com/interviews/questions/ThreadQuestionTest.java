package com.interviews.questions;

import org.junit.Test;

public class ThreadQuestionTest implements Runnable {

    @Test
    public void test() throws InterruptedException {
        ThreadQuestionTest te = new ThreadQuestionTest();

        Thread thread1 = new Thread(te);
        Thread thread2 = new Thread(te);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println(counter);

    }

    static int counter = 0;

    @Override
    public void run() {
        for (int j = 0; j < 10; j++) {
            counter++;
        }


/**
 * //counter++ separate to 3 step:
 * 		1. get value from memory
 * 		2. ++
 * 		3. set value from memory
 *
 * 		//thread1 getting CPU-time and do only first step :  get the value from the memory -> 0
 * 		//thread2 getting CPU-time and do only first step :  get the value from the memory -> 0
 *
 * 		//thread1 -> run all iterations excluding the last iteration (10) (form first iteration until 9) (get,++,set) -> and set counter to 9 (now the T2 going to sleep... and waiting...
 * 		//thread2 -> take the CPU time and do the (steps 2+3): ++, and set value -> now, the counter value set to 1
 *
 * 		//thread1 -> take CPU time and run the last iteration and doing only first step : "get counter value" -> 1;
 *
 * 		//thread2-> take CPU time go to end iterations -> set counter to 11
 *
 * 		//thread1 take CPU time and doing the steps 2+3: (value has 1, and now set to 2)  > set 2
 */

    }

}

package threads;

import org.junit.Test;

public class VolatileTest {


    @Test
    public void TestVolatile() throws InterruptedException {
        TestVolatile t = new TestVolatile();
        t.start();
        Thread.sleep(1000);
        System.out.println("after sleeping in main");
        t.keepRunning = false;
        t.join();
        System.out.println("keepRunning set to " + t.keepRunning);
    }

    class TestVolatile extends Thread {
        volatile
        boolean keepRunning = true;

        @Override
        public void run() {
            long count = 0;
            while (keepRunning) {
                count++;
            }

            System.out.println("Thread terminated." + count);
        }
    }

    @Test
    public void TestSync() throws InterruptedException {
        TestSync t = new TestSync();
        t.start();
        Thread.sleep(1000);
        System.out.println("after sleeping in main");
        t.setKeepRunning(false);
        t.join();
        System.out.println("keepRunning set to " + t.keepRunning);
    }

    class TestSync extends Thread {
        private boolean keepRunning = true;

        public synchronized void setKeepRunning(boolean keepRunning) {
            this.keepRunning = keepRunning;
        }

        public synchronized boolean isKeepRunning() {
            return keepRunning;
        }

        @Override
        public void run() {
            long count = 0;
            while (isKeepRunning()) {
                count++;
            }

            System.out.println("Thread terminated." + count);
        }
    }

}

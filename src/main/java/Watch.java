public class Watch {
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

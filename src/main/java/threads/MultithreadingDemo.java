package threads;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

class MultithreadingDemo extends ThreadPoolTaskScheduler implements Runnable{
    public void run(){
        System.out.println("My thread is in running state.");
    }
    public static void main(String args[]){
        MultithreadingDemo obj=new MultithreadingDemo();
        Thread tobj =new Thread(obj);
        tobj.start();
    }
}

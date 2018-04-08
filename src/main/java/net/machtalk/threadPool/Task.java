package net.machtalk.threadPool;

import java.util.concurrent.TimeUnit;

/**
 * Created by zhaop on 2018/4/8
 */
public class Task implements Runnable {
    @Override
    public void run() {
        try {
            Long duration = (long) (Math.random() * 5);
            System.out.println("Running Task! Thread Name: " + Thread.currentThread().getName());
            TimeUnit.SECONDS.sleep(duration);
            System.out.println("Task Completed! Thread Name: "+ Thread.currentThread().getName());
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

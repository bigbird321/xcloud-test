package net.machtalk.threadPool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhaop on 2018/4/8
 */
public class TestScheduledThreadPool {

    public static void main(final String[] arguments) throws InterruptedException {
        /**
         * 调度线程池
         */
        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        final ScheduledFuture<?> beepHandler =
                scheduler.scheduleAtFixedRate(new BeepTask(), 2, 2, TimeUnit.SECONDS);

        scheduler.schedule(new Runnable() {
            @Override
            public void run() {
                beepHandler.cancel(true);
                scheduler.shutdown();
            }
        }, 10, TimeUnit.SECONDS);
    }

    static class BeepTask implements Runnable {
        public void run() {
            System.out.println("beep");
        }
    }

}

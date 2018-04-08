package net.machtalk.threadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhaop on 2018/4/8
 */
public class TestFixedThreadPool {

    public static void main(String[] args) throws InterruptedException {
        /**
         * 最多2个线程将处于活动状态。
         * 如果提交了两个以上的线程，那么它们将保持在队列中，直到线程可用。
         * 如果一个线程由于执行关闭期间的失败而终止，则执行器尚未被调用，则创建一个新线程。
         * 线程会一直存在，直到池关闭。
         */
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // Cast the object to its class type
        ThreadPoolExecutor pool = (ThreadPoolExecutor) executor;

        //Stats before tasks execution
        System.out.println("Core threads: " + pool.getCorePoolSize());
        System.out.println("Largest executions: " + pool.getLargestPoolSize());
        System.out.println("Maximum allowed threads: " + pool.getMaximumPoolSize());
        System.out.println("Current threads in pool: " + pool.getPoolSize());
        System.out.println("Currently executing threads: " + pool.getActiveCount());
        System.out.println("Total number of threads(ever scheduled): " + pool.getTaskCount());

        executor.submit(new Task());
        executor.submit(new Task());
        executor.submit(new Task());

        //Stats after tasks execution
        System.out.println("======================================" + pool.getTaskCount());
        System.out.println("Core threads: " + pool.getCorePoolSize());
        System.out.println("Largest executions: "
                + pool.getLargestPoolSize());
        System.out.println("Maximum allowed threads: "
                + pool.getMaximumPoolSize());
        System.out.println("Current threads in pool: "
                + pool.getPoolSize());
        System.out.println("Currently executing threads: "
                + pool.getActiveCount());
        System.out.println("Total number of threads(ever scheduled): "
                + pool.getTaskCount());

        executor.shutdown();
    }


}

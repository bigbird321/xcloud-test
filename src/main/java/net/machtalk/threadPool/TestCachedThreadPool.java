package net.machtalk.threadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhaop on 2018/4/8
 */
public class TestCachedThreadPool {
    public static void main(final String[] arguments) throws InterruptedException {
        /**
         * newCachedThreadPool()方法创建一个具有可扩展线程池的执行器。
         * 这样的执行者适合于发起许多短命的任务的应用程序。
         */
        ExecutorService executor = Executors.newCachedThreadPool();

        // Cast the object to its class type
        ThreadPoolExecutor pool = (ThreadPoolExecutor) executor;

        //Stats before tasks execution
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

        executor.submit(new Task());
        executor.submit(new Task());
        executor.submit(new Task());

        System.out.println("======================================" + pool.getTaskCount());

        //Stats after tasks execution
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

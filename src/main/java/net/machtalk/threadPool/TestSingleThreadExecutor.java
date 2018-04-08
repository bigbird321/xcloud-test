package net.machtalk.threadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhaop on 2018/4/8
 */
public class TestSingleThreadExecutor {
    public static void main(final String[] arguments) throws InterruptedException {
        /**
         * 行单个任务的线程池
         * 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行
         */
        ExecutorService executor = Executors.newSingleThreadExecutor();

        try {
            executor.submit(new Task());
            executor.submit(new Task());
            System.out.println("Shutdown executor");
            executor.shutdown();
            executor.awaitTermination(5, TimeUnit.SECONDS);
        }
        catch (InterruptedException e) {
            System.err.println("tasks interrupted");
        }
        finally {
            if (!executor.isTerminated()) {
                System.err.println("cancel non-finished tasks");
            }
            executor.shutdownNow();
            System.out.println("shutdown finished");
        }
    }


}

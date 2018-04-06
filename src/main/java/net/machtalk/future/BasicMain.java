package net.machtalk.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Created by zhaop on 2018/3/12
 */
public class BasicMain {

    public static CompletableFuture<Integer> compute() {
        /**
         * future没有关联任何的Callback、线程池、异步任务等，如果客户端调用future.get就会一直等下去
         */
        final CompletableFuture<Integer> future = new CompletableFuture<>();
        return future;
    }

    public static void main(String[] args) throws Exception {
        final CompletableFuture<Integer> f = compute();

        class Client extends Thread {
            CompletableFuture<Integer> f;
            Client(String threadName, CompletableFuture<Integer> f) {
                super(threadName);
                this.f = f;
            }
            @Override
            public void run() {
                try {
                    System.out.println(this.getName() + ": " + f.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }


        new Client("Client1", f).start();
        new Client("Client2", f).start();
        System.out.println("waiting");
        /**
         * CompletableFuture.complete()、CompletableFuture.completeExceptionally只能被调用一次。
         * 但是我们有两个后门方法可以重设这个值:obtrudeValue、obtrudeException，
         * 使用的时候要小心，因为complete已经触发了客户端，有可能导致客户端会得到不期望的结果
         */
        f.complete(100);
        //f.completeExceptionally(new Exception());
        System.in.read();
    }

}

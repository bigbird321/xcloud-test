package net.machtalk.deferred;

import com.stumbleupon.async.Deferred;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhaop on 2018/3/2
 */
public class TestDeferred {

    public static void main(String[] args) throws InterruptedException {

        System.out.println(Thread.currentThread().getName());

        Deferred<String> deferred = new Deferred<>().addBoth(str -> {
            System.out.println(Thread.currentThread().getName() + " 1 " + str.toString());
            return str;
        }).addBothDeferring(str -> {
            System.out.println(Thread.currentThread().getName() + " 2 " + str);

            Deferred<String> d = new Deferred<>().addBoth(s -> {
                System.out.println(Thread.currentThread().getName() + " 3 " + s.toString());
                return s.toString();
            });

            Executors.newSingleThreadExecutor().execute(() -> {
                System.out.println(Thread.currentThread().getName() + " started.");
                d.callback("hello");
            });
            return d;
        }).addBoth(str -> {
            System.out.println(Thread.currentThread().getName() + " 4 " + str.toString());
            return str;
        }).addErrback(str -> {
            System.out.println(str.toString());
            return str;
        });

        Executors.newSingleThreadExecutor().execute(() -> {
            System.out.println(Thread.currentThread().getName() + " started.");
//            deferred.callback("hello");
            deferred.callback(new NullPointerException("error."));
        });

        System.out.println(Thread.currentThread().getName() + " sleeping...");

        TimeUnit.MINUTES.sleep(1);
    }

}

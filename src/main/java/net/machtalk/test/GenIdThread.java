package net.machtalk.test;

/**
 * Created by zhaop on 2017/9/6.
 */
public class GenIdThread extends Thread {
    String threadName;

    public GenIdThread(int i) {
        threadName = i + "";
    }


    public void run() {
        Snowflake idWorker = new Snowflake(0, 0);
        long id = idWorker.nextId();
        System.out.println(Long.toBinaryString(id));
        System.out.println(threadName + "=======" + id);
    }


}

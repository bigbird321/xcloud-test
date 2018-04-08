package net.machtalk.thread.notify;

/**
 * Created by zhaop on 2018/4/8
 */
public class TestThread {
    public static void main(String[] args) {
        Chat m = new Chat();
        new T1(m);
        new T2(m);
    }
}

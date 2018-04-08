package net.machtalk.lock.reentrantLock;

/**
 * Created by zhaop on 2018/4/8
 */
public class ThreadDemo extends Thread {
    PrintDemo  printDemo;

    ThreadDemo( String name,  PrintDemo printDemo) {
        super(name);
        this.printDemo = printDemo;
    }

    @Override
    public void run() {
        System.out.printf("%s starts printing a document\n", Thread.currentThread().getName());
        printDemo.print();
    }
}

package com.solutions.concurrents;

import java.time.Clock;

public class MyThread extends Thread {
    public MyThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        System.out.println(threadName + " Start time:" + Clock.systemUTC().millis());
        try {
            Thread.sleep(100);
            System.out.println("Executing thread " + threadName);
            System.out.println(threadName + ", End time:" + Clock.systemUTC().millis());
        } catch (Exception e) {;}
    }

    public static void main(String[] args) throws InterruptedException {
        MyThread myThread1 = new MyThread("myThread1");
        MyThread myThread2 = new MyThread("myThread2");

        myThread1.start(); myThread2.start();
    }

}

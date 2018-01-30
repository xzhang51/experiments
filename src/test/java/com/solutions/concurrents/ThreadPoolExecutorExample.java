package com.solutions.concurrents;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolExecutorExample implements Runnable {
    private static AtomicInteger counter = new AtomicInteger();
    private final int taskId;

    public int getTaskId() {
        return taskId;
    }

    public ThreadPoolExecutorExample(int taskId) {
        this.taskId = taskId;
    }

    public void run() {
        try {
            Thread.sleep(1000);
            System.out.println(taskId);
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName());
            // e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>(30);
        ThreadFactory threadFactory = new ThreadFactory() {
            public Thread newThread(Runnable r) {
                int currentCount = counter.getAndIncrement();
                System.out.println("Creating new thread: " + currentCount);
                return new Thread(r, "mythread" + currentCount);
            }
        };
        RejectedExecutionHandler rejectedHandler = new RejectedExecutionHandler() {
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                if (r instanceof ThreadPoolExecutorExample) {
                    ThreadPoolExecutorExample example = (ThreadPoolExecutorExample) r;
                    System.out.println("Rejecting task with id " + example.getTaskId());
                }
            }
        };
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 1, TimeUnit.SECONDS, queue, threadFactory, rejectedHandler);
        try {
            for (int i = 0; i < 50; i++) {
                executor.execute(new ThreadPoolExecutorExample(i));
                Thread.sleep(1);
            }
            System.out.println("pool size:" + executor.getPoolSize());
            Thread.sleep(1000);
            executor.shutdown();
        } catch (InterruptedException ie) {

        }
    }
}

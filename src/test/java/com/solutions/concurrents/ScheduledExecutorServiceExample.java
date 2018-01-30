package com.solutions.concurrents;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ScheduledExecutorServiceExample implements Runnable {
    private static AtomicInteger counter = new AtomicInteger();
    private final int taskId;
    private final long startTime;

    public int getTaskId() {
        return taskId;
    }

    public ScheduledExecutorServiceExample(int taskId) {
        this.taskId = taskId;
        this.startTime = System.currentTimeMillis();
    }

    public void run() {
        System.out.println((System.currentTimeMillis()-startTime)/1000);
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
                if (r instanceof ScheduledExecutorServiceExample) {
                    ScheduledExecutorServiceExample example = (ScheduledExecutorServiceExample) r;
                    System.out.println("Rejecting task with id " + example.getTaskId());
                }
            }
        };
        ScheduledExecutorService executor = new ScheduledThreadPoolExecutor(1, threadFactory, rejectedHandler);
        try {
            /**
             * Since there is no keepAlive configuration,
             */
            executor.scheduleAtFixedRate(new ScheduledExecutorServiceExample(5), 1, 10, TimeUnit.SECONDS);
            Thread.sleep(200000);
            executor.shutdown();
        } catch (InterruptedException ie) {

        }
    }
}

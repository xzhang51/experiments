package com.solutions.concurrents;

import junit.framework.TestCase;

import java.util.concurrent.*;

public class SimpleTest extends TestCase {
    private ExecutorService executor;

    public void setUp() {
        executor = Executors.newSingleThreadExecutor();
    }

    public void testExecutor() {
        executor.execute( () -> {
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + i);
            }
        });

        System.out.println(Thread.currentThread().getName() + " thread");
    }

    public void testFuture() throws Exception {
        Future< Long > result = executor.submit(new Callable< Long >() {
            @Override
            public Long call() throws Exception {
                return 100L;
            }
        } );

        System.out.println("Result=" + result.get( 1, TimeUnit.SECONDS ));

    }
}

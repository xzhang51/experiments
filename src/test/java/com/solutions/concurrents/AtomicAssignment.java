package com.solutions.concurrents;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Atomic assignment could be used for Bloom filter, which is not thread safe as HashMap. We could have an hourly
 * scheduled job to load a new BloomFilter from the DB lookup table and then atomically assign it to the BloomFilter
 * volatile variable.
 */
public class AtomicAssignment implements Runnable {
    private static volatile Map<String, String> configuration = new HashMap<String, String>();

    public void run() {
        for (int i = 0; i < 10000; i++) {
            // It is important to assign the shared volatile variable into a local variable. Otherwise, different
            // values could be returned if configuration is directly accessed.
            Map<String, String> currConfig = configuration;
            String value1 = currConfig.get("key-1");
            String value2 = currConfig.get("key-2");
            String value3 = currConfig.get("key-3");
            if (!(value1.equals(value2) && value2.equals(value3))) {
                throw new IllegalStateException("Values are not equal.");
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void readConfig() {
        Map<String, String> newConfig = new HashMap<String, String>();
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS");
        newConfig.put("key-1", sdf.format(now));
        newConfig.put("key-2", sdf.format(now));
        newConfig.put("key-3", sdf.format(now));
        configuration = newConfig;
    }

    public static void main(String[] args) throws InterruptedException {
        readConfig();
        Thread configThread = new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    readConfig();
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "configuration-thread");
        configThread.start();
        Thread[] threads = new Thread[5];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new AtomicAssignment(), "thread-" + i);
            threads[i].start();
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i].join();
        }
        configThread.join();
        System.out.println("[" + Thread.currentThread().getName() + "] All threads have finished.");
    }
}

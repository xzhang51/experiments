package com.solutions.filter;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;


public class BloomFilterSimulator extends TestCase {
    private long startTime;
    StringTypeBloomFilter bloomFilter = new StringTypeBloomFilter(1000000000, 0.01);
    List<StringTypeBloomFilter> bloomFilterList = new ArrayList<StringTypeBloomFilter>();

    /**
     * From the test, it took about 500 seconds to populate half G of members in the bloom and 25 seconds to check 50M
     * times with 2.5/10000 false positive rate. The memory usage is somewhere above 1.7GB to avoid OutOfMemory runtime
     * error.
     */
    public void setUp() {
        startTime = System.currentTimeMillis();
//        for (long index = 1000000000; index < 2000000000; index=index+2) {
//            bloomFilter.put(String.valueOf(index));
//        }

        for (int index = 0; index < 30; index++) {
            StringTypeBloomFilter filter = new StringTypeBloomFilter(20000000, 0.01);
            bloomFilterList.add(filter);
            for (int i = 10000000; i < 30000000; i=i+2) {
                filter.put(String.valueOf(i));
            }
        }
        System.out.println("Time to pupolate 0.5G members: " + (System.currentTimeMillis() - startTime));
    }

    public void testBloomFalsePositive() {
        int falsePostiveCnt = 0;
        startTime = System.currentTimeMillis();
        for (long index = 100001; index < 200000; index = index + 2) {
            for (StringTypeBloomFilter filter : bloomFilterList) {
                if (filter.isFound(String.valueOf(index))) {
                    falsePostiveCnt++;
                }
            }
        }
        System.out.println("Time to check 0.5G: " + (System.currentTimeMillis()-startTime));
        System.out.println("False positive count: " + falsePostiveCnt);
    }
}

package com.solutions.filter;

import junit.framework.TestCase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

public class StringTypeBloomFilterTest extends TestCase {
    private static String keyFilePath = "/Users/xifengzhang/IdeaProjects/webappexample/src/test/resources/bloomFilter";

    private StringTypeBloomFilter filter = null;

    public void setUp() {
        filter = new StringTypeBloomFilter(10000, 0.01);
    }

    public void testEmptyBloomFilter() {
        assertFalse(filter.isFound("1"));
        assertFalse(filter.isFound("2"));
        assertFalse(filter.isFound("3"));
    }

    public void testPuts() throws Exception {
        loadKeys(100);
        assertTrue(filter.isFound("2"));
        assertTrue(filter.isFound("5"));
        assertTrue(filter.isFound("90"));

        FileOutputStream out = new FileOutputStream(keyFilePath);
        filter.save(out);
        out.close();
        assertTrue((new File(keyFilePath)).exists());
    }

    public void testLoad() {

        try {
            InputStream in = new FileInputStream(keyFilePath);
            filter.load(in);
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertTrue(filter.isFound("2"));
        assertTrue(filter.isFound("5"));
        assertTrue(filter.isFound("99"));
        assertFalse(filter.isFound("999"));
    }

    private void loadKeys(int keyNumber) {
        for (int i = 0; i < keyNumber; i++) {
            filter.put(String.valueOf(i));
        }
    }

    public void testFalsePostiveRate() {
        loadKeys(100); // Load the same number of member as used to create the Bloom filter.
        int matchCount = 0;
        for (int i = 100; i < 100000; i++) {
            matchCount += filter.isFound(String.valueOf(i)) ? 1 : 0;
        }
        System.out.println("Tested 99900 non member values, match count = " + matchCount);
    }
}

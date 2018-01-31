package com.solutions.filter;
/**
 * This class is a wrapper to the Google Guava BloomFilter for String type.
**/
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.List;

public class StringTypeBloomFilter {
    private BloomFilter<CharSequence> bloomFilter = null;
    private int size = 1000000;
    private double rate = 0.01;

    public StringTypeBloomFilter() {
        bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charset.defaultCharset()), size, rate);
    }

    public StringTypeBloomFilter(int memeberSize, double falsePositiveRate) {
        this.size = memeberSize;
        this.rate = falsePositiveRate;
        bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charset.defaultCharset()), size, rate);
    }

    public BloomFilter<CharSequence> load(InputStream in) throws IOException { ;
        BloomFilter<CharSequence> newFilter = bloomFilter.readFrom(in, Funnels.stringFunnel(Charset.defaultCharset()));
        bloomFilter = newFilter;
        return newFilter;
    }

    public void save(OutputStream out) throws IOException {
        bloomFilter.writeTo(out);

        return;
    }

    public boolean isFound(String key) {
        return bloomFilter.mightContain(key);
    }

    public void put(String key) {
        bloomFilter.put(key);
    }

    public void putAll(List<String> keys) {
        if (keys == null) {
            return;
        }

        for (String key : keys) {
            bloomFilter.put(key);
        }
    }

    public BloomFilter<CharSequence> getBloomFilter() {
        return bloomFilter;
    }

    public void setBloomFilter(BloomFilter<CharSequence> bloomFilter) {
        this.bloomFilter = bloomFilter;
    }
}

package ru.lab.ooap.task10;

import java.util.Random;

abstract class BloomFilter<T> {
    protected int filterLength = 32;
    protected int filter = 0;

    // post-condition: new bloom filter of size 32 created
    public BloomFilter() {
    }

    /* Commands */
    // post-condition: value added to bloom filter
    abstract void add(T value);

    /* Queries */
    abstract boolean contains(T value);
}

class BloomFilterImpl<T> extends BloomFilter<T> {

    public BloomFilterImpl() {
        super();
    }

    private int hashA(T value) {
        return Math.abs(value.hashCode()) % filterLength;
    }

    private int hashB(T value) {
        return Math.abs(value.hashCode() + value.hashCode() / 3) % filterLength;
    }

    @Override
    void add(T value) {
        markPosition(hashA(value));
        markPosition(hashB(value));
    }

    @Override
    boolean contains(T value) {
        return existsOnPosition(hashA(value)) && existsOnPosition(hashB(value));
    }

    private void markPosition(int position) {
        int mark = 1 << position;
        filter |= mark;
    }

    private boolean existsOnPosition(int position) {
        int mark = 1 << position;
        return (filter & mark) == mark;
    }
}

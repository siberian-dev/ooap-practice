package ru.lab.ooap.task5;

import java.lang.reflect.Array;

interface Queue<T> {
    /* Commands */
    void enqueue(T value);
    T dequeue();

    /* Queries */
    T head();
    boolean isEmpty();
    boolean isFull();
    int capacity();
    int size();

    int getEnqueueStatus();
    int getHeadStatus();
    int getDequeueStatus();
}

enum Status {
    NOT_EXECUTED,
    QUEUE_EMPTY,
    QUEUE_FULL,
    SUCCESS
}

class QueueImpl<T> implements Queue<T> {
    private final T [] entry;
    private int size;
    private int capacity;
    private int headIdx;
    private int tailIdx;

    private int enqueueStatus = Status.NOT_EXECUTED.ordinal();
    private int headStatus = Status.NOT_EXECUTED.ordinal();
    private int dequeueStatus = Status.NOT_EXECUTED.ordinal();

    // post-condition: queue with defined capacity created
    QueueImpl(Class clz, int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.headIdx = capacity - 1;
        this.tailIdx = capacity - 1;
        this.entry = (T[]) Array.newInstance(clz, capacity);
    }

    // pre-condition: queue is not full
    // post-condition: new element added to queue tail
    @Override
    public void enqueue(T value) {
        if (isFull()) {
            enqueueStatus = Status.QUEUE_FULL.ordinal();
            return;
        }

        entry[roundIndex(tailIdx--)] = value;
        size++;
        enqueueStatus = Status.SUCCESS.ordinal();
    }

    // pre-condition: queue is not empty
    // post-condition: element removed from queue head
    @Override
    public T dequeue() {
        if (isEmpty()) {
            dequeueStatus = Status.QUEUE_EMPTY.ordinal();
            return null;
        }

        var el = entry[roundIndex(headIdx--)];
        size--;
        dequeueStatus = Status.SUCCESS.ordinal();
        return el;
    }

    // pre-condition: queue is not empty
    @Override
    public T head() {
        if (isEmpty()) {
            headStatus = Status.QUEUE_EMPTY.ordinal();
            return null;
        }

        var el = entry[headIdx];
        headStatus = Status.SUCCESS.ordinal();
        return el;
    }

    private int roundIndex(int idx) {
        if (idx < 0 || idx >= capacity) {
            return (capacity + idx) % capacity;
        } else {
            return idx;
        }
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean isFull() {
        return size == capacity;
    }

    @Override
    public int capacity() {
        return entry.length;
    }

    @Override
    public int size() {
        return size;
    }

    public int getEnqueueStatus() {
        return enqueueStatus;
    }

    @Override
    public int getHeadStatus() {
        return headStatus;
    }

    public int getDequeueStatus() {
        return dequeueStatus;
    }
}

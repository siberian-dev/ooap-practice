package ru.lab.ooap.task6;

import java.lang.reflect.Array;

enum Status {
    NOT_EXECUTED,
    IS_EMPTY,
    IS_FULL,
    SUCCESS
}

abstract class ParentQueue<T> {
    protected final T [] entry;
    protected int size;
    protected int capacity;
    protected int headIdx;
    protected int tailIdx;

    protected int appendTailStatus = Status.NOT_EXECUTED.ordinal();
    protected int headStatus = Status.NOT_EXECUTED.ordinal();
    protected int removeHeadStatus = Status.NOT_EXECUTED.ordinal();

    // post-condition: queue with defined capacity created
    ParentQueue(Class clz, int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.headIdx = capacity - 1;
        this.tailIdx = capacity - 1;
        this.entry = (T[]) Array.newInstance(clz, capacity);
    }

    // pre-condition: queue is not full
    // post-condition: new element added to queue tail
    public void appendTail(T value) {
        if (isFull()) {
            appendTailStatus = Status.IS_FULL.ordinal();
            return;
        }

        entry[roundIndex(tailIdx--)] = value;
        size++;
        appendTailStatus = Status.SUCCESS.ordinal();
    }

    // pre-condition: queue is not empty
    // post-condition: element removed from queue head
    public T removeHead() {
        if (isEmpty()) {
            removeHeadStatus = Status.IS_EMPTY.ordinal();
            return null;
        }

        var el = entry[roundIndex(headIdx--)];
        size--;
        removeHeadStatus = Status.SUCCESS.ordinal();
        return el;
    }

    // pre-condition: queue is not empty
    public T head() {
        if (isEmpty()) {
            headStatus = Status.IS_EMPTY.ordinal();
            return null;
        }

        var el = entry[headIdx];
        headStatus = Status.SUCCESS.ordinal();
        return el;
    }

    protected int roundIndex(int idx) {
        if (idx < 0 || idx >= capacity) {
            return (capacity + idx) % capacity;
        } else {
            return idx;
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == capacity;
    }

    public int capacity() {
        return entry.length;
    }

    public int size() {
        return size;
    }

    public int getAppendTailStatus() {
        return appendTailStatus;
    }

    public int getHeadStatus() {
        return headStatus;
    }

    public int getRemoveHeadStatus() {
        return removeHeadStatus;
    }
}

abstract class Queue<T> extends ParentQueue<T> {
    Queue(Class clz, int capacity) {
        super(clz, capacity);
    }
}

abstract class Deque<T> extends ParentQueue<T>{
    Deque(Class clz, int capacity) {
        super(clz, capacity);
    }
    abstract void appendHead(T value);
    abstract T removeTail();
    abstract T tail();

    abstract int getRemoveTailStatus();
    abstract int getTailStatus();
    abstract int getAppendHeadStatus();
}

class QueueImpl<T> extends Queue<T> {
    // post-condition: queue with defined capacity created
    QueueImpl(Class clz, int capacity) {
        super(clz, capacity);
    }
}

class DequeImpl<T> extends Deque<T> {
    protected int appendHeadStatus = Status.NOT_EXECUTED.ordinal();
    protected int tailStatus = Status.NOT_EXECUTED.ordinal();
    protected int removeTailStatus = Status.NOT_EXECUTED.ordinal();

    // post-condition: deque with defined capacity created
    DequeImpl(Class clz, int capacity) {
        super(clz, capacity);
    }

    // pre-condition: dequeue is not full
    // post-condition: new element added to dequeue head
    public void appendHead(T value) {
        if (isFull()) {
            appendHeadStatus = Status.IS_FULL.ordinal();
            return;
        }

        entry[roundIndex(++headIdx)] = value;
        size++;
        appendHeadStatus = Status.SUCCESS.ordinal();
    }

    // pre-condition: dequeue is not empty
    // post-condition: element removed from dequeue tail
    public T removeTail() {
        if (isEmpty()) {
            removeTailStatus = Status.IS_EMPTY.ordinal();
            return null;
        }

        var el = entry[roundIndex(++tailIdx)];
        size--;
        removeTailStatus = Status.SUCCESS.ordinal();
        return el;
    }

    // pre-condition: dequeue is not empty
    public T tail() {
        if (isEmpty()) {
            tailStatus = Status.IS_EMPTY.ordinal();
            return null;
        }

        var el = entry[tailIdx + 1];
        tailStatus = Status.SUCCESS.ordinal();
        return el;
    }

    public int getRemoveTailStatus() {
        return removeTailStatus;
    }

    public int getTailStatus() {
        return tailStatus;
    }

    public int getAppendHeadStatus() {
        return appendHeadStatus;
    }
}

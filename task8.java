package ru.lab.ooap.task8;

import java.lang.reflect.Array;
import java.util.Objects;

enum Status {
    NOT_EXECUTED,
    NOT_PLACED,
    NOT_EXISTING,
    SUCCESS
}

abstract class NativeDictionary<T> {
    protected final String[] slots;
    protected final T[] values;
    protected int step = 3;
    protected int capacity;

    int putStatus = Status.NOT_EXECUTED.ordinal();
    int removeStatus = Status.NOT_EXECUTED.ordinal();
    int getStatus = Status.NOT_EXECUTED.ordinal();

    // post-condition: created native dictionary with defined capacity
    public NativeDictionary(int capacity, Class clazz) {
        this.capacity = capacity;
        this.slots = new String[capacity];
        this.values = (T[]) Array.newInstance(clazz, capacity);
    }

    /* Command */
    // pre-condition: value with key existing or free slot exist for value with key
    // post-condition: value placed (new/replaced) in slots  by key
    abstract void put(String key, T value);

    // pre-condition: value with key presented
    // post-condition: value removed by key
    abstract void remove(String key);

    /* Query */
    abstract boolean hasKey(String value);

    // pre-condition: value with key presented
    abstract T get(String key);

    /* Statuses Getting */
    public int getPutStatus() {
        return putStatus;
    }

    public int getRemoveStatus() {
        return removeStatus;
    }

    public int getGetStatus() {
        return getStatus;
    }
}

class NativeDictionaryImpl<T> extends NativeDictionary<T> {

    public NativeDictionaryImpl(int capacity, Class clazz) {
        super(capacity, clazz);
    }

    @Override
    void put(String key, T value) {
        var index = Math.abs(key.hashCode()) % capacity;
        var startIndex = index;
        while (slots[index] != null) {
            index = (index + step) % capacity;

            if (index == startIndex) {
                putStatus = Status.NOT_PLACED.ordinal();
                return;
            }
        }

        slots[index] = key;
        values[index] = value;

        putStatus = Status.SUCCESS.ordinal();
    }

    @Override
    void remove(String key) {
        var index = Math.abs(key.hashCode()) % capacity;

        if (!hasKey(key)) {
            removeStatus = Status.NOT_EXISTING.ordinal();
            return;
        }

        slots[index] = null;
        values[index] = null;

        removeStatus = Status.SUCCESS.ordinal();
    }

    @Override
    boolean hasKey(String key) {
        var index = Math.abs(key.hashCode()) % capacity;
        return Objects.equals(slots[index], key);
    }

    @Override
    T get(String key) {
        if (!hasKey(key)) {
            getStatus = Status.NOT_EXISTING.ordinal();
            return null;
        }
        var index = Math.abs(key.hashCode()) % capacity;
        getStatus = Status.SUCCESS.ordinal();
        return values[index];
    }

}

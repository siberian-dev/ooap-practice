package ru.lab.ooap.task4;

import java.lang.reflect.Array;

interface DynArray<T> {
    /* Commands */
  
    // post-condition: created array with defined capacity
    void makeArray(int newCapacity);
    // post-condition: added new item to array, capacity increased if needed
    void append(T itm);
    // pre-condition: index in allowed range (0, count)
    // post-condition: added new item to array in defined index, other after elements shifts for 1 position rights
    void insert(T itm, int index);
    // pre-condition: index in allowed range (0, count - 1)
    // post-condition: removed item from array by defined index, other after elements shifts for 1 position left
    void remove(int index);

    /* Queries */
  
    // pre-condition: index in allowed range (0, count - 1)
    T getItem(int index);
    int size();

    int getGetStatus();
    int getRemoveStatus();
    int getInsertStatus();
}

enum Status {
    NOT_EXECUTED,
    ARRAY_EMPTY,
    SUCCESS
}

class DynArrayImpl<T> implements DynArray<T> {
    private T [] array;
    private int count;
    private int capacity;
    private Class clazz;
    private static int MIN_CAPACITY = 16;
    private static double CAPACITY_REDUCE_BOUNDARY_PERC = 0.5;

    private int getStatus = Status.NOT_EXECUTED.ordinal();
    private int removeStatus = Status.NOT_EXECUTED.ordinal();
    private int insertStatus = Status.NOT_EXECUTED.ordinal();

    public DynArrayImpl(Class clz) {
        clazz = clz;
        count = 0;
        makeArray(MIN_CAPACITY);
    }

    @Override
    public void makeArray(int newCapacity) {
        array = (T[]) Array.newInstance(this.clazz, newCapacity);
        capacity = newCapacity;
        count = 0;
    }

    @Override
    public T getItem(int index) {
        if (index < 0 || index >= count) {
            getStatus = Status.ARRAY_EMPTY.ordinal();
            return null;
        }
      
        getStatus = Status.SUCCESS.ordinal();
        return array[index];
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public int getGetStatus() {
        return getStatus;
    }

    @Override
    public void append(T itm) {
        if (count == capacity) {
            var newArray = getNewArray(false);
            System.arraycopy(array, 0, newArray, 0, count);
            array = newArray;
        }

        array[count] = itm;
        count++;
        capacity = array.length;
    }

    @Override
    public void insert(T itm, int index) {
        if (index < 0 || index > count) {
            insertStatus = Status.ARRAY_EMPTY.ordinal();
            return;
        }

        if (index == count) {
            append(itm);
            return;
        }

        if (count == capacity) {
            var newArray = getNewArray(false);
            System.arraycopy(array, 0, newArray, 0, count);
            array = newArray;
        }

        System.arraycopy(array, index, array, index + 1, count - index);

        array[index] = itm;
        count++;
        capacity = array.length;
        insertStatus = Status.SUCCESS.ordinal();
    }

    @Override
    public int getInsertStatus() {
        return insertStatus;
    }

    @Override
    public void remove(int index) {
        if (index < 0 || index >= count) {
            removeStatus = Status.ARRAY_EMPTY.ordinal();
            return;
        }

        System.arraycopy(array, index + 1, array, index, count - (index + 1));

        if (count - 1 < (capacity * CAPACITY_REDUCE_BOUNDARY_PERC)) {
            var newArray = getNewArray(true);
            System.arraycopy(array, 0, newArray, 0, count);
            array = newArray;
        }

        count--;
        capacity = array.length;
        removeStatus = Status.SUCCESS.ordinal();
    }

    @Override
    public int getRemoveStatus() {
        return removeStatus;
    }

    private T[] getNewArray(boolean isDecreased) {
        var changedCapacity = capacity;
        if (isDecreased) {
            changedCapacity = (int) Math.round(capacity / 1.5);
        } else  {
            changedCapacity = capacity * 2;
        }

        if (changedCapacity < MIN_CAPACITY) {
            changedCapacity = MIN_CAPACITY;
        }

        var extendedArray = (T[]) Array.newInstance(this.clazz, changedCapacity);
        return extendedArray;
    }

}

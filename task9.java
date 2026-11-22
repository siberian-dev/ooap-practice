package ru.lab.ooap.task9;

import java.lang.reflect.Array;
import java.util.Objects;

enum Status {
    NOT_EXECUTED,
    ALREADY_PLACED,
    NOT_ENOUGH_SPACE,
    NOT_EXISTING,
    DIFFERENT_CAPACITIES,
    SUCCESS
}

abstract class HashTable<T> {
    protected final T[] slots;
    protected int capacity;
    protected int step = 3;
    protected int size = 0;
    protected Class clazz;

    int putStatus = Status.NOT_EXECUTED.ordinal();
    int removeStatus = Status.NOT_EXECUTED.ordinal();

    public HashTable(int capacity, Class clazz) {
        this.capacity = capacity;
        this.clazz = clazz;
        this.slots = (T[]) Array.newInstance(clazz, capacity);
    }

    /* Commands */
    // pre-condition: free slot exists
    // post-condition: value placed in table
    void put(T value) {
        if (size == capacity) {
            putStatus = Status.NOT_ENOUGH_SPACE.ordinal();
            return;
        }

        var index = Math.abs(value.hashCode()) % capacity;
        var startIndex = index;

        if (Objects.equals(slots[index], value)) {
            putStatus = Status.ALREADY_PLACED.ordinal();
            return;
        }

        while (slots[index] != null) {
            index = (index + step) % capacity;

            if (index == startIndex) {
                putStatus = Status.NOT_ENOUGH_SPACE.ordinal();
                return;
            }
        }

        slots[index] = value;
        size++;

        putStatus = Status.SUCCESS.ordinal();
    }

    // pre-condition: value exists in table
    // post-condition: value removed from table
    void remove(T value) {
        int index = Math.abs(value.hashCode()) % capacity;

        if (slots[index] == null) {
            removeStatus = Status.NOT_EXISTING.ordinal();
            return;
        }

        slots[index] = null;
        size--;

        removeStatus = Status.SUCCESS.ordinal();
    }

    /* Queries */
    boolean get(T value) {
        var index = Math.abs(value.hashCode()) % capacity;
        return Objects.equals(slots[index], value);
    }

    int size() {
        return size;
    }

    /* Statuses Getting */
    int getPutStatus() {
        return putStatus;
    }

    int getRemoveStatus() {
        return removeStatus;
    }
}

abstract class PowerSet<T> extends HashTable<T> {
    public PowerSet(int capacity, Class clazz) {
        super(capacity, clazz);
    }

    int differenceStatus = Status.NOT_EXECUTED.ordinal();
    int unionStatus = Status.NOT_EXECUTED.ordinal();
    int intersectionStatus = Status.NOT_EXECUTED.ordinal();

    /* Commands */
    // pre-condition: sets have same capacity
    // post-condition: intersection of set1 and set2 created
    abstract PowerSet<T> intersection(PowerSet<T> set2);
    // pre-condition: sets have same capacity
    // post-condition: union of set1 and set2 created
    abstract PowerSet<T> union(PowerSet<T> set2);
    // pre-condition: sets have same capacity
    // post-condition: difference of set1 and set2 created
    abstract PowerSet<T> difference(PowerSet<T> set2);

    /* Queries */
    abstract T[] values();

    public int getIntersectionStatus() {
        return intersectionStatus;
    }

    public int getUnionStatus() {
        return unionStatus;
    }

    public int getDifferenceStatus() {
        return differenceStatus;
    }
}

class PowerSetImpl<T> extends PowerSet<T> {

    public PowerSetImpl(int capacity, Class clazz) {
        super(capacity, clazz);
    }

    @Override
    PowerSet<T> intersection(PowerSet<T> set2) {
        if (this.capacity != set2.capacity) {
            intersectionStatus = Status.DIFFERENT_CAPACITIES.ordinal();
        }

        PowerSet<T> intersection = new PowerSetImpl<>(capacity, clazz);

        PowerSet<T> biggerSet = set2.size > this.size ? set2 : this;
        PowerSet<T> smallerSet = biggerSet == this ? set2 : this;

        for (T element : smallerSet.values()) {
            if (biggerSet.get(element)) {
                intersection.put(element);
            }
        }

        intersectionStatus = Status.SUCCESS.ordinal();
        return intersection;
    }

    @Override
    PowerSet<T> union(PowerSet<T> set2) {
        if (this.capacity != set2.capacity) {
            unionStatus = Status.DIFFERENT_CAPACITIES.ordinal();
        }

        PowerSet<T> union = new PowerSetImpl<>(this.size + set2.size(), clazz);

        PowerSet<T> biggerSet = set2.size > this.size ? set2 : this;
        PowerSet<T> smallerSet = biggerSet == this ? set2 : this;

        for (T element : biggerSet.values()) {
            union.put(element);
        }

        for (T element : smallerSet.values()) {
            if (!union.get(element)) {
                union.put(element);
            }
        }

        unionStatus = Status.SUCCESS.ordinal();
        return union;
    }

    @Override
    PowerSet<T> difference(PowerSet<T> set2) {
        if (this.capacity != set2.capacity) {
            differenceStatus = Status.DIFFERENT_CAPACITIES.ordinal();
        }

        PowerSet<T> difference = new PowerSetImpl<>(capacity, clazz);

        for (T element : this.values()) {
            if (!set2.get(element)) {
                difference.put(element);
            }
        }

        differenceStatus = Status.SUCCESS.ordinal();
        return difference;
    }

    @Override
    T[] values() {
        int existing = 0;

        for (T element : slots) {
            if (element != null) {
                existing++;
            }
        }

        var values = (T[]) Array.newInstance(clazz, existing);
        var currentIndex = 0;
        for (T slot : slots) {
            if (slot != null) {
                values[currentIndex++] = slot;
            }
        }

        return values;
    }

}

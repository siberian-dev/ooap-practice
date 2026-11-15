package ru.lab.ooap.task7;

enum Status {
    NOT_EXECUTED,
    NOT_PLACED,
    INVALID_INDEX,
    ALREADY_REMOVED,
    SUCCESS
}

abstract class HashTable {
    public static final int INVALID_INDEX = -1;

    protected final String[] slots;
    protected int step = 3;
    protected int capacity;
    protected int size = 0;

    int putStatus = Status.NOT_EXECUTED.ordinal();
    int removeStatus = Status.NOT_EXECUTED.ordinal();
    int findStatus = Status.NOT_EXECUTED.ordinal();

    public HashTable(int capacity) {
        this.capacity = capacity;
        this.slots = new String[capacity];
    }

    /* Command */
    // pre-condition: free slot exist, index can be found
    // post-condition: value placed in free slot
    abstract int put(String value);
    // pre-condition: defined slot index in range (0, capacity - 1)
    // post-condition: value removed by index
    abstract void remove(int index);

    /* Query */
    // pre-condition: value in slot presented
    abstract int find(String value);

    /* Statuses Getting */
    public int getPutStatus() {
        return putStatus;
    }

    public int getRemoveStatus() {
        return removeStatus;
    }

    public int getFindStatus() {
        return findStatus;
    }
}

class HashTableImpl extends HashTable {

    public HashTableImpl(int capacity) {
        super(capacity);
    }

    @Override
    int put(String value) {
        var index = value.hashCode() % capacity;
        var startIndex = index;
        while (slots[index] != null) {
            index = (index + step) % capacity;

            if (index == startIndex) {
                putStatus = Status.NOT_PLACED.ordinal();
                return INVALID_INDEX;
            }
        }

        putStatus = Status.SUCCESS.ordinal();
        return index;
    }

    @Override
    void remove(int index) {
        if (index < 0 || index >= capacity) {
            removeStatus = Status.INVALID_INDEX.ordinal();
            return;
        }

        if (slots[index] == null) {
            removeStatus = Status.ALREADY_REMOVED.ordinal();
            return;
        }

        slots[index] = null;
        removeStatus = Status.SUCCESS.ordinal();
    }

    @Override
    int find(String value) {
        var index = value.hashCode() % capacity;

        if (slots[index] != null) {
            findStatus = Status.SUCCESS.ordinal();
            return index;
        } else {
            findStatus = Status.INVALID_INDEX.ordinal();
            return INVALID_INDEX;
        }
    }

}

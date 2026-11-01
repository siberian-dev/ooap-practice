package ru.lab.ooap.task2;

/* 2.1. */
public abstract class LinkedList<T> {
    public static int GET_NIL = 0;
    public static int GET_OK = 1;
    public static int GET_ERR = 2;

    public static int RIGHT_NIL = 0;
    public static int RIGHT_OK = 1;
    public static int RIGHT_ERR = 2;

    public static int REMOVE_NIL = 0;
    public static int REMOVE_OK = 1;
    public static int REMOVE_ERR = 2;

    public static int PUT_RIGHT_NIL = 0;
    public static int PUT_RIGHT_OK = 1;
    public static int PUT_RIGHT_ERR = 2;

    public static int PUT_LEFT_NIL = 0;
    public static int PUT_LEFT_OK = 1;
    public static int PUT_LEFT_ERR = 2;

    public static int ADD_TO_EMPTY_NIL = 0;
    public static int ADD_TO_EMPTY_OK = 1;
    public static int ADD_TO_EMPTY_ERR = 2;

    public static int ADD_TAIL_NIL = 0;
    public static int ADD_TAIL_OK = 1;
    public static int ADD_TAIL_ERR = 2;

    public static int REPLACE_NIL = 0;
    public static int REPLACE_OK = 1;
    public static int REPLACE_ERR = 2;

    public static int REMOVE_ALL_NIL = 0;
    public static int REMOVE_ALL_OK = 1;
    public static int REMOVE_ALL_ERR = 2;

    public static int FIND_NIL = 0;
    public static int FIND_OK = 1;
    public static int FIND_ERR = 2;

    public static int HEAD_NIL = 0;
    public static int HEAD_OK = 1;
    public static int HEAD_ERR = 2;

    public static int TAIL_NIL = 0;
    public static int TAIL_OK = 1;
    public static int TAIL_ERR = 2;

    // post-condition: empty list created
    LinkedList() {}

    /* Query */

    // get current node value
    // pre-condition: list is not empty
    abstract T get();

    abstract int getGetStatus();

    // count list nodes
    abstract int size();

    // is cursor on list start?
    abstract boolean isHead();

    // is cursor on list end?
    abstract boolean isTail();

    // is cursor set on any node (is list empty?)
    abstract boolean isValue();

    /* Commands */

    // shift cursor right for 1 position
    // pre-condition: list is not empty, cursor is not in end
    // post-condition: cursor shifted for 1 position right
    abstract void right();

    abstract void getRightStatus();

    // clear list
    // post-condition: removed all values from list, statuses reset
    abstract void clear();

    // remove current node
    // pre-condition: list is not empty
    // post-condition: current node is removed, cursor set on right node (if presented), else set on left node (if presented)
    abstract void remove();

    abstract int getRemoveStatus();

    // add new node with value after current node (cursor)
    // pre-condition: list is not empty
    // post-condition: new node added after current node
    abstract void putRight(T value);

    abstract int getPutRightStatus();

    // add new node with value before current node (cursor)
    // pre-condition: list is not empty
    // post-condition: new node added before current node
    abstract void putLeft(T value);

    abstract int getPutLeftStatus();

    // add new node to empty list
    // pre-condition: list is empty
    // post-condition: new node added to empty list, cursor/head/tail set
    abstract void addToEmpty(T value);

    abstract int getAddToEmptyStatus();

    // add new node to list tail
    // pre-condition: list is not empty
    // post-condition: new node added to list tail (after last tail node)
    abstract void addTail(T value);

    abstract int getAddTailStatus();

    // change current node value with new defined value
    // pre-condition: list is not empty
    // post-condition: new value set to current node
    abstract void replace(T value);

    abstract int getReplaceStatus();

    // remove all nodes with defined value
    // pre-condition: nodes with defined value presented
    // post-condition: all nodes with defined value are removed
    abstract void removeAll(T value);

    abstract int getRemoveAllStatus();

    // set cursor on next node with defined value (related to current node)
    // pre-condition: node with defined value is presented
    // post-condition: cursor set on next node with defined value
    abstract void find(T value);

    abstract int getFindStatus();

    // set cursor on first node in list
    // pre-condition: list is not empty
    // post-condition: cursor set on first node in list
    abstract void head();

    abstract int getHeadStatus();

    // set cursor on last node in list
    // pre-condition: list is not empty
    // post-condition: cursor set on last node in list
    abstract void tail();

    abstract int getTailStatus();

}

/* 2.2. */

/* We can set cursor on tail element in O(1) by tail() without N right() from head O(N) */

/* 2.3. */

/* We can use find(value) until it works to get all nodes with defined value */

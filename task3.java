package ru.lab.ooap.task3;

import java.util.Objects;

import static ru.lab.ooap.task3.ListStatusModel.NOT_EXECUTED;

class Node<T> {
    private T value;
    private Node<T> left;
    private Node<T> right;

    public Node(T value, Node<T> left, Node<T> right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public Node<T> getLeft() {
        return left;
    }

    public void setLeft(Node<T> left) {
        this.left = left;
    }

    public Node<T> getRight() {
        return right;
    }

    public void setRight(Node<T> right) {
        this.right = right;
    }
}

enum ListStatusModel {
    NOT_EXECUTED,
    SUCCESS,
    LIST_EMPTY,
    LIST_NOT_EMPTY,
    NEXT_FOUND,
    NEXT_NOT_FOUND,
    PREVIOUS_FOUND,
    PREVIOUS_NOT_FOUND,
}

abstract class ParentList<T> {
    private int size;
    protected Node<T> head;
    protected Node<T> tail;
    protected Node<T> cursor;

    private int getStatus = NOT_EXECUTED.ordinal();
    private int rightStatus = NOT_EXECUTED.ordinal();
    private int findStatus = NOT_EXECUTED.ordinal();
    private int headStatus = NOT_EXECUTED.ordinal();
    private int tailStatus = NOT_EXECUTED.ordinal();
    private int addTailStatus = NOT_EXECUTED.ordinal();
    private int addToEmptyStatus = NOT_EXECUTED.ordinal();
    private int putRightStatus = NOT_EXECUTED.ordinal();
    private int putLeftStatus = NOT_EXECUTED.ordinal();
    private int removeStatus = NOT_EXECUTED.ordinal();
    private int replaceStatus = NOT_EXECUTED.ordinal();

    // post-condition: empty list created
    ParentList() {
        this.size = 0;
    }

    /* Query */

    // get current node value
    // pre-condition: list is not empty
    T get() {
        if (cursor == null) {
            getStatus = ListStatusModel.LIST_EMPTY.ordinal();
            return null;
        } else {
            getStatus = ListStatusModel.SUCCESS.ordinal();
            return cursor.getValue();
        }
    }

    // count list nodes
    int size() {
        return size;
    }

    // is cursor on list start?
    boolean isHead() {
        return cursor == head;
    }

    // is cursor on list end?
    boolean isTail() {
        return cursor == tail;
    }

    // is cursor set on any node (is list empty?)
    boolean isValue() {
        return cursor != null;
    }

    /* Commands */

    // shift cursor right for 1 position
    // pre-condition: list is not empty, cursor is not in end
    // post-condition: cursor shifted for 1 position right
    void right() {
        if (cursor == null) {
            rightStatus = ListStatusModel.LIST_EMPTY.ordinal();
        } else if (cursor.getRight() != null) {
            cursor = cursor.getRight();
            rightStatus = ListStatusModel.NEXT_FOUND.ordinal();
        } else {
            rightStatus = ListStatusModel.NEXT_NOT_FOUND.ordinal();
        }
    }

    // clear list
    // post-condition: removed all values from list
    void clear() {
        cursor = head;
        while (cursor != null) {
            remove();
        }
    }

    // remove current node
    // pre-condition: list is not empty
    // post-condition: current node is removed, cursor set on right node (if presented), else set on left node (if presented)
    void remove() {
        if (cursor == null) {
            removeStatus = ListStatusModel.LIST_EMPTY.ordinal();
        } else {
            var current = cursor;
            var left = current.getLeft();
            var right = current.getRight();

            if (right != null) {
                right.setLeft(left);
            }

            if (left != null) {
                left.setRight(right);
            }

            if (current == head) {
                head = right;
            }

            if (current == tail) {
                tail = left;
            }

            if (right != null) {
                cursor = right;
            } else {
                cursor = left;
            }

            size = size - 1;

            removeStatus = ListStatusModel.SUCCESS.ordinal();
        }
    }

    // add new node with value after current node (cursor)
    // pre-condition: list is not empty
    // post-condition: new node added after current node
    void putRight(T value) {
        if (cursor == null) {
            putRightStatus = ListStatusModel.LIST_EMPTY.ordinal();
        } else {
            var current = cursor;
            var right = cursor.getRight();

            var node = new Node<>(value, current, right);
            current.setRight(node);
            if (right != null) {
                right.setLeft(node);
            }

            if (current == tail) {
                tail = node;
            }

            size = size + 1;

            putRightStatus = ListStatusModel.SUCCESS.ordinal();
        }
    }

    // add new node with value before current node (cursor)
    // pre-condition: list is not empty
    // post-condition: new node added before current node
    void putLeft(T value) {
        if (cursor == null) {
            putLeftStatus = ListStatusModel.LIST_EMPTY.ordinal();
        } else {
            var current = cursor;
            var left = cursor.getLeft();

            var node = new Node<>(value, left, current);
            current.setLeft(node);
            if (left != null) {
                left.setRight(node);
            }

            if (current == head) {
                head = node;
            }

            size = size + 1;

            putLeftStatus = ListStatusModel.SUCCESS.ordinal();
        }
    }

    // add new node to empty list
    // pre-condition: list is empty
    // post-condition: new node added to empty list, cursor/head/tail set
    void addToEmpty(T value) {
        if (cursor == null) {
            var node = new Node<>(value, null, null);
            cursor = node;
            head = node;
            tail = node;
            size = size + 1;
            addToEmptyStatus = ListStatusModel.SUCCESS.ordinal();
        } else {
            addToEmptyStatus = ListStatusModel.LIST_NOT_EMPTY.ordinal();
        }
    }

    // add new node to list tail
    // post-condition: new node added to list tail (after last tail node)
    void addTail(T value) {
        if (cursor == null) {
            addTailStatus = ListStatusModel.LIST_EMPTY.ordinal();
        } else {
            tail.setRight(new Node<>(value, tail, null));
            size = size + 1;
            addTailStatus = ListStatusModel.SUCCESS.ordinal();
        }
    }

    // change current node value with new defined value
    // pre-condition: list is not empty
    // post-condition: new value set to current node
    void replace(T value) {
        if (cursor == null) {
            replaceStatus = ListStatusModel.LIST_EMPTY.ordinal();
        } else {
            cursor.setValue(value);
            replaceStatus = ListStatusModel.SUCCESS.ordinal();
        }
    }

    // post-condition: all nodes with defined value are removed
    void removeAll(T value) {
        if (cursor == null) {
            return;
        }

        cursor = head;

        while (cursor != null) {
            if (Objects.equals(cursor.getValue(), value)) {
                remove();
                continue;
            }

            cursor = cursor.getRight();
        }

        cursor = head;
    }

    // set cursor on next node with defined value (related to current node)
    // post-condition: cursor set on next node with defined value
    void find(T value) {
        if (cursor == null) {
            findStatus = ListStatusModel.NEXT_NOT_FOUND.ordinal();
            return;
        }

        var current = cursor;
        do {
            current = current.getRight();
        } while (current != null && !Objects.equals(current.getValue(), value));

        if (current != null && Objects.equals(current.getValue(), value)) {
            findStatus = ListStatusModel.NEXT_FOUND.ordinal();
            cursor = current;
        } else {
            findStatus = ListStatusModel.NEXT_NOT_FOUND.ordinal();
        }
    }

    // set cursor on first node in list
    // pre-condition: list is not empty
    // post-condition: cursor set on first node in list
    void head() {
        if (cursor == null) {
            headStatus = ListStatusModel.LIST_EMPTY.ordinal();
        } else {
            cursor = head;
            headStatus = ListStatusModel.SUCCESS.ordinal();
        }
    }

    // set cursor on last node in list
    // pre-condition: list is not empty
    // post-condition: cursor set on last node in list
    void tail() {
        if (cursor == null) {
            tailStatus = ListStatusModel.LIST_EMPTY.ordinal();
        } else {
            cursor = tail;
            tailStatus = ListStatusModel.SUCCESS.ordinal();
        }
    }

    int getGetStatus() {
        return getStatus;
    }

    int getTailStatus() {
        return tailStatus;
    }

    public int getRightStatus() {
        return rightStatus;
    }

    public int getFindStatus() {
        return findStatus;
    }

    public int getHeadStatus() {
        return headStatus;
    }

    public int getAddToEmptyStatus() {
        return addToEmptyStatus;
    }

    public int getPutRightStatus() {
        return putRightStatus;
    }

    public int getPutLeftStatus() {
        return putLeftStatus;
    }

    public int getRemoveStatus() {
        return removeStatus;
    }

    public int getReplaceStatus() {
        return replaceStatus;
    }

    public int getAddTailStatus() {
        return addTailStatus;
    }
}

abstract class LinkedList<T> extends ParentList<T> {
}

abstract class TwoWayList<T> extends ParentList<T> {
    abstract void left();
}

class LinkedListImpl<T> extends LinkedList<T> { }

class TwoWayListImpl<T> extends TwoWayList<T> {
    private int leftStatus = NOT_EXECUTED.ordinal();

    @Override
    void left() {
        if (cursor == null) {
            leftStatus = ListStatusModel.LIST_EMPTY.ordinal();
        } else if (cursor.getLeft() != null) {
            cursor = cursor.getLeft();
            leftStatus = ListStatusModel.PREVIOUS_FOUND.ordinal();
        } else {
            leftStatus = ListStatusModel.PREVIOUS_NOT_FOUND.ordinal();
        }
    }

    public int getLeftStatus() {
        return leftStatus;
    }
}

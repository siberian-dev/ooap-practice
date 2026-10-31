package ru.lab.ooap.task1;

import java.util.ArrayList;
import java.util.List;

interface BoundedStack<T> {
    int POP_NIL = 0;
    int POP_OK = 1;
    int POP_ERR = 2;

    int PEEK_NIL = 0;
    int PEEK_OK = 1;
    int PEEK_ERR = 2;

    int PUSH_NIL = 0;
    int PUSH_OK = 1;
    int PUSH_ERR = 2;

    void push(T value);
    void pop();
    void clear();
    T peek();
    int size();
    int getPushStatus();
    int getPopStatus();
    int getPeekStatus();
}

class BoundedStackImpl<T> implements BoundedStack<T> {
    private final int capacity = 32;
    private final List<T> stack;
    private int pushStatus;
    private int peekStatus;
    private int popStatus;

    // post-condition: created new empty stack with capacity 32
    BoundedStackImpl() {
        stack = new ArrayList<>(capacity);
    }

    // pre-condition: current stack size < capacity
    // post-condition: new value added to stack
    @Override
    public void push(T value) {
        if (stack.size() < capacity) {
            stack.addLast(value);
            pushStatus = PUSH_OK;
        } else {
            pushStatus = PUSH_ERR;
        }
    }

    // pre-condition: stack is not empty
    // post-condition: removed upper value from stack
    @Override
    public void pop() {
        if (stack.isEmpty()) {
            popStatus = POP_ERR;
        } else {
            stack.removeLast();
            popStatus = POP_OK;
        }
    }

    // post-condition: removed all values from stack
    @Override
    public void clear() {
        stack.clear();

        peekStatus = PEEK_NIL;
        popStatus = POP_NIL;
        pushStatus = PUSH_NIL;
    }

    // pre-condition: stack is not empty
    @Override
    public T peek() {
        if (stack.isEmpty()) {
            peekStatus = PEEK_ERR;
            return null;
        } else {
            T last = stack.getLast();
            peekStatus = PEEK_OK;
            return last;
        }
    }

    @Override
    public int size() {
        return stack.size();
    }

    @Override
    public int getPushStatus() {
        return pushStatus;
    }

    @Override
    public int getPopStatus() {
        return popStatus;
    }

    @Override
    public int getPeekStatus() {
        return peekStatus;
    }

}

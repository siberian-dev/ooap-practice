package ru.lab.ooap.task1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BoundedStackTest {

    private final BoundedStack<Integer> stack = new BoundedStackImpl<>();

    @BeforeEach
    void beforeEach() {
        stack.clear();
    }

    @Test
    void push_empty_ok() {
        stack.push(1);

        Assertions.assertEquals(1, stack.size());
        Assertions.assertEquals(BoundedStack.PUSH_OK, stack.getPushStatus());
    }

    @Test
    void push_full_err() {
        for (int i = 0; i < 32; i++) {
            stack.push(i);
            Assertions.assertEquals(BoundedStack.PUSH_OK, stack.getPushStatus());
        }

        stack.push(33);

        Assertions.assertEquals(32, stack.size());
        Assertions.assertEquals(BoundedStack.PUSH_ERR, stack.getPushStatus());
    }

    @Test
    void pop_empty_err() {
        stack.pop();

        Assertions.assertEquals(0, stack.size());
        Assertions.assertEquals(BoundedStack.POP_ERR, stack.getPopStatus());
    }

    @Test
    void pop_filled_ok() {
        stack.push(1);
        stack.pop();

        Assertions.assertEquals(0, stack.size());
        Assertions.assertEquals(BoundedStack.POP_OK, stack.getPopStatus());
    }

    @Test
    void peek_empty_err() {
        Integer value = stack.peek();

        Assertions.assertNull(value);
        Assertions.assertEquals(0, stack.size());
        Assertions.assertEquals(BoundedStack.PEEK_ERR, stack.getPeekStatus());
    }

    @Test
    void peek_filled_ok() {
        stack.push(32);
        Integer value = stack.peek();

        Assertions.assertEquals(32, value);
        Assertions.assertEquals(1, stack.size());
        Assertions.assertEquals(BoundedStack.PEEK_OK, stack.getPeekStatus());
    }

}

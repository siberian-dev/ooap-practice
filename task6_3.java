package ru.lab.ooap.task6;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class DequeImplTest {

    @Test
    void appendTail_empty_success() {
        Deque<Integer> dequeue = new DequeImpl<>(Integer.class, 10);
        Assertions.assertTrue(dequeue.isEmpty());

        for (int i = 0; i < 10; i++) {
            dequeue.appendTail(i);
            Assertions.assertEquals(Status.SUCCESS.ordinal(), dequeue.getAppendTailStatus());
        }

        Assertions.assertTrue(dequeue.isFull());
        Assertions.assertFalse(dequeue.isEmpty());
        Assertions.assertEquals(10, dequeue.size());
    }

    @Test
    void appendHead_empty_success() {
        Deque<Integer> dequeue = new DequeImpl<>(Integer.class, 10);
        Assertions.assertTrue(dequeue.isEmpty());

        for (int i = 0; i < 10; i++) {
            dequeue.appendHead(i);
            Assertions.assertEquals(Status.SUCCESS.ordinal(), dequeue.getAppendHeadStatus());
        }

        Assertions.assertTrue(dequeue.isFull());
        Assertions.assertFalse(dequeue.isEmpty());
        Assertions.assertEquals(10, dequeue.size());
    }

    @Test
    void appendTail_full_failure() {
        Deque<Integer> dequeue = new DequeImpl<>(Integer.class, 10);
        Assertions.assertTrue(dequeue.isEmpty());

        for (int i = 0; i < 10; i++) {
            dequeue.appendTail(i);
            Assertions.assertEquals(Status.SUCCESS.ordinal(), dequeue.getAppendTailStatus());
        }

        dequeue.appendTail(11);
        Assertions.assertEquals(Status.IS_FULL.ordinal(), dequeue.getAppendTailStatus());
        Assertions.assertEquals(10, dequeue.size());
    }

    @Test
    void appendHead_full_failure() {
        Deque<Integer> dequeue = new DequeImpl<>(Integer.class, 10);
        Assertions.assertTrue(dequeue.isEmpty());

        for (int i = 0; i < 10; i++) {
            dequeue.appendHead(i);
            Assertions.assertEquals(Status.SUCCESS.ordinal(), dequeue.getAppendHeadStatus());
        }

        dequeue.appendHead(11);
        Assertions.assertEquals(Status.IS_FULL.ordinal(), dequeue.getAppendHeadStatus());
        Assertions.assertEquals(10, dequeue.size());
    }

    @Test
    void removeHead_full_success() {
        Deque<Integer> dequeue = new DequeImpl<>(Integer.class, 10);
        Assertions.assertTrue(dequeue.isEmpty());

        for (int i = 0; i < 10; i++) {
            dequeue.appendTail(i);
            Assertions.assertEquals(Status.SUCCESS.ordinal(), dequeue.getAppendTailStatus());
        }

        for (int i = 0; i < 10; i++) {
            var element = dequeue.removeHead();
            Assertions.assertEquals(i, element);
            Assertions.assertEquals(Status.SUCCESS.ordinal(), dequeue.getRemoveHeadStatus());
        }
    }

    @Test
    void removeHead_empty_failure() {
        Deque<Integer> dequeue = new DequeImpl<>(Integer.class, 10);
        Assertions.assertTrue(dequeue.isEmpty());

        for (int i = 0; i < 10; i++) {
            dequeue.appendTail(i);
            Assertions.assertEquals(Status.SUCCESS.ordinal(), dequeue.getAppendTailStatus());
        }

        for (int i = 0; i < 10; i++) {
            var element = dequeue.removeHead();
            Assertions.assertEquals(i, element);
            Assertions.assertEquals(Status.SUCCESS.ordinal(), dequeue.getRemoveHeadStatus());
        }

        var lastElement = dequeue.head();
        lastElement = dequeue.removeHead();
        Assertions.assertEquals(Status.IS_EMPTY.ordinal(), dequeue.getHeadStatus());
        Assertions.assertEquals(Status.IS_EMPTY.ordinal(), dequeue.getRemoveHeadStatus());
        Assertions.assertNull(lastElement);
    }

    @Test
    void appendTail_removeHead_empty_success() {
        Deque<Integer> dequeue = new DequeImpl<>(Integer.class, 10);
        Assertions.assertTrue(dequeue.isEmpty());

        dequeue.appendTail(123);
        dequeue.appendTail(321);
        dequeue.appendTail(122);

        dequeue.removeHead();
        dequeue.removeHead();

        dequeue.appendTail(88);
        dequeue.appendTail(777);
        dequeue.appendTail(666);

        dequeue.removeHead();

        var expectedElements = List.of(88, 777, 666);

        for (var el : expectedElements) {
            Assertions.assertEquals(el, dequeue.head());
            Assertions.assertEquals(Status.SUCCESS.ordinal(), dequeue.getHeadStatus());
            Assertions.assertEquals(el, dequeue.removeHead());
            Assertions.assertEquals(Status.SUCCESS.ordinal(), dequeue.getRemoveHeadStatus());
        }

        Assertions.assertTrue(dequeue.isEmpty());
    }

    @Test
    void appendTail_removeHead_appendHead_removeTail_empty_success() {
        Deque<Integer> dequeue = new DequeImpl<>(Integer.class, 10);
        Assertions.assertTrue(dequeue.isEmpty());

        dequeue.appendTail(123);
        dequeue.appendTail(321);
        dequeue.appendTail(122);

        dequeue.removeHead();
        dequeue.removeHead();

        dequeue.appendTail(88);
        dequeue.appendTail(777);
        dequeue.appendTail(666);

        dequeue.removeHead();

        dequeue.appendHead(808);
        dequeue.appendHead(322);

        dequeue.appendTail(101);

        dequeue.removeTail();
        dequeue.removeTail();

        dequeue.removeHead();

        var expectedElements = List.of(808, 88, 777);

        for (var el : expectedElements.reversed()) {
            Assertions.assertEquals(el, dequeue.tail());
            Assertions.assertEquals(Status.SUCCESS.ordinal(), dequeue.getTailStatus());
            Assertions.assertEquals(el, dequeue.removeTail());
            Assertions.assertEquals(Status.SUCCESS.ordinal(), dequeue.getRemoveTailStatus());
        }

        Assertions.assertTrue(dequeue.isEmpty());
    }


}

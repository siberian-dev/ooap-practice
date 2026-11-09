package ru.lab.ooap.task5;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class QueueImplTest {

    @Test
    void enqueue_empty_success() {
        Queue<Integer> queue = new QueueImpl<>(Integer.class, 10);
        Assertions.assertTrue(queue.isEmpty());

        for (int i = 0; i < 10; i++) {
            queue.enqueue(i);
            Assertions.assertEquals(Status.SUCCESS.ordinal(), queue.getEnqueueStatus());
        }

        Assertions.assertTrue(queue.isFull());
        Assertions.assertFalse(queue.isEmpty());
        Assertions.assertEquals(10, queue.size());
    }

    @Test
    void enqueue_full_failure() {
        Queue<Integer> queue = new QueueImpl<>(Integer.class, 10);
        Assertions.assertTrue(queue.isEmpty());

        for (int i = 0; i < 10; i++) {
            queue.enqueue(i);
            Assertions.assertEquals(Status.SUCCESS.ordinal(), queue.getEnqueueStatus());
        }

        queue.enqueue(11);
        Assertions.assertEquals(Status.QUEUE_FULL.ordinal(), queue.getEnqueueStatus());
        Assertions.assertEquals(10, queue.size());
    }

    @Test
    void dequeue_full_success() {
        Queue<Integer> queue = new QueueImpl<>(Integer.class, 10);
        Assertions.assertTrue(queue.isEmpty());

        for (int i = 0; i < 10; i++) {
            queue.enqueue(i);
            Assertions.assertEquals(Status.SUCCESS.ordinal(), queue.getEnqueueStatus());
        }

        for (int i = 0; i < 10; i++) {
            var element = queue.dequeue();
            Assertions.assertEquals(i, element);
            Assertions.assertEquals(Status.SUCCESS.ordinal(), queue.getDequeueStatus());
        }
    }

    @Test
    void dequeue_empty_failure() {
        Queue<Integer> queue = new QueueImpl<>(Integer.class, 10);
        Assertions.assertTrue(queue.isEmpty());

        for (int i = 0; i < 10; i++) {
            queue.enqueue(i);
            Assertions.assertEquals(Status.SUCCESS.ordinal(), queue.getEnqueueStatus());
        }

        for (int i = 0; i < 10; i++) {
            var element = queue.dequeue();
            Assertions.assertEquals(i, element);
            Assertions.assertEquals(Status.SUCCESS.ordinal(), queue.getDequeueStatus());
        }

        var lastElement = queue.head();
        lastElement = queue.dequeue();
        Assertions.assertEquals(Status.QUEUE_EMPTY.ordinal(), queue.getHeadStatus());
        Assertions.assertEquals(Status.QUEUE_EMPTY.ordinal(), queue.getDequeueStatus());
        Assertions.assertNull(lastElement);
    }

    @Test
    void enqueue_dequeue_empty_success() {
        Queue<Integer> queue = new QueueImpl<>(Integer.class, 10);
        Assertions.assertTrue(queue.isEmpty());

        queue.enqueue(123);
        queue.enqueue(321);
        queue.enqueue(122);

        queue.dequeue();
        queue.dequeue();

        queue.enqueue(88);
        queue.enqueue(777);
        queue.enqueue(666);

        queue.dequeue();

        var expectedElements = List.of(88, 777, 666);

        for (var el : expectedElements) {
            Assertions.assertEquals(el, queue.head());
            Assertions.assertEquals(Status.SUCCESS.ordinal(), queue.getHeadStatus());
            Assertions.assertEquals(el, queue.dequeue());
            Assertions.assertEquals(Status.SUCCESS.ordinal(), queue.getDequeueStatus());
        }

        Assertions.assertTrue(queue.isEmpty());
    }


}

package ru.lab.ooap.task3;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class LinkedListTest {

    @Test
    void add_to_empty_fail() {
        LinkedList<Integer> linkedList  = new LinkedListImpl<>();
        linkedList.addToEmpty(1);
        Assertions.assertEquals(linkedList.getAddToEmptyStatus(), ListStatusModel.SUCCESS.ordinal());
        Assertions.assertEquals(linkedList.size(), 1);

        linkedList.addToEmpty(1);
        Assertions.assertEquals(linkedList.getAddToEmptyStatus(), ListStatusModel.LIST_NOT_EMPTY.ordinal());
        Assertions.assertEquals(linkedList.size(), 1);
    }

    @Test
    void add_to_empty_success() {
        LinkedList<Integer> linkedList  = new LinkedListImpl<>();

        linkedList.addToEmpty(1);
        Assertions.assertEquals(linkedList.size(), 1);
        Assertions.assertEquals(linkedList.getAddToEmptyStatus(), ListStatusModel.SUCCESS.ordinal());
        Assertions.assertTrue(linkedList.isHead());
        Assertions.assertTrue(linkedList.isTail());
        Assertions.assertTrue(linkedList.isValue());
        Assertions.assertEquals(linkedList.get(), 1);
        Assertions.assertEquals(linkedList.getGetStatus(), ListStatusModel.SUCCESS.ordinal());
    }

    @Test
    void pushes_fail() {
        LinkedList<Integer> linkedList  = new LinkedListImpl<>();
        linkedList.putLeft(-1);
        Assertions.assertEquals(linkedList.getPutLeftStatus(), ListStatusModel.LIST_EMPTY.ordinal());

        linkedList.putRight(3);
        Assertions.assertEquals(linkedList.getPutRightStatus(), ListStatusModel.LIST_EMPTY.ordinal());
    }

    @Test
    void pushes_success() {
        LinkedList<Integer> linkedList  = new LinkedListImpl<>();

        linkedList.addToEmpty(1);

        linkedList.putLeft(-1);
        Assertions.assertEquals(linkedList.size(), 2);
        Assertions.assertEquals(linkedList.getPutLeftStatus(), ListStatusModel.SUCCESS.ordinal());
        Assertions.assertFalse(linkedList.isHead());
        Assertions.assertTrue(linkedList.isTail());

        linkedList.putLeft(0);
        Assertions.assertEquals(linkedList.size(), 3);
        Assertions.assertEquals(linkedList.getPutLeftStatus(), ListStatusModel.SUCCESS.ordinal());
        Assertions.assertFalse(linkedList.isHead());
        Assertions.assertTrue(linkedList.isTail());

        linkedList.putRight(3);
        Assertions.assertEquals(linkedList.size(), 4);
        Assertions.assertEquals(linkedList.getPutRightStatus(), ListStatusModel.SUCCESS.ordinal());
        Assertions.assertFalse(linkedList.isHead());
        Assertions.assertFalse(linkedList.isTail());

        linkedList.putRight(2);
        Assertions.assertEquals(linkedList.size(), 5);
        Assertions.assertEquals(linkedList.getPutRightStatus(), ListStatusModel.SUCCESS.ordinal());
        Assertions.assertFalse(linkedList.isHead());
        Assertions.assertFalse(linkedList.isTail());

        linkedList.addTail(4);
        Assertions.assertEquals(linkedList.size(), 6);
        Assertions.assertEquals(linkedList.getAddTailStatus(), ListStatusModel.SUCCESS.ordinal());
        Assertions.assertFalse(linkedList.isHead());
        Assertions.assertFalse(linkedList.isTail());
    }

    @Test
    void right_success() {
        LinkedList<Integer> linkedList  = new LinkedListImpl<>();

        linkedList.addToEmpty(1);
        linkedList.putLeft(-1);
        linkedList.putLeft(0);
        linkedList.putRight(3);
        linkedList.putRight(2);
        linkedList.addTail(4);

        var expectedList = List.of(-1, 0, 1, 2, 3, 4);
        checkList(expectedList, linkedList);
    }

    @Test
    void remove_success() {
        LinkedList<Integer> linkedList  = new LinkedListImpl<>();

        linkedList.addToEmpty(1);
        linkedList.putLeft(0);
        linkedList.putLeft(1);
        linkedList.putRight(0);
        linkedList.putRight(1);

        Assertions.assertEquals(linkedList.size(), 5);

        linkedList.removeAll(1);

        Assertions.assertEquals(linkedList.size(), 2);

        var expectedList = List.of(0, 0);
        checkList(expectedList, linkedList);

        linkedList.remove();
        Assertions.assertEquals(linkedList.size(), 1);

        linkedList.remove();
        Assertions.assertEquals(linkedList.size(), 0);
    }

    private void checkList(List<Integer> expectedList, LinkedList<Integer> actualList) {
        actualList.head();

        for (var i : expectedList) {
            Assertions.assertEquals(actualList.get(), i);
            Assertions.assertEquals(actualList.getGetStatus(), ListStatusModel.SUCCESS.ordinal());
            actualList.right();
        }
    }

}

class TwoWaysListTest {

    @Test
    void left_success() {
        TwoWayList<Integer> twoWayList  = new TwoWayListImpl<>();

        twoWayList.addToEmpty(1);
        twoWayList.putLeft(-1);
        twoWayList.putLeft(0);
        twoWayList.putRight(3);
        twoWayList.putRight(2);
        twoWayList.addTail(4);

        var expectedList = List.of(-1, 0, 1, 2, 3, 4);
        checkList(expectedList, twoWayList);
    }

    private void checkList(List<Integer> expectedList, TwoWayList<Integer> actualList) {
        actualList.head();

        for (var i : expectedList) {
            Assertions.assertEquals(actualList.get(), i);
            Assertions.assertEquals(actualList.getGetStatus(), ListStatusModel.SUCCESS.ordinal());
            actualList.right();
        }

        for (var i : expectedList.reversed()) {
            Assertions.assertEquals(actualList.get(), i);
            Assertions.assertEquals(actualList.getGetStatus(), ListStatusModel.SUCCESS.ordinal());
            actualList.left();
        }
    }

}

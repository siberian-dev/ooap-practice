package ru.lab.ooap.task4;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class DynArrayImplTest {

    @Test
    void append_empty_success() {
        DynArray<Integer> dynArray = new DynArrayImpl<>(Integer.class);

        for (int i = 0; i < 33; i++) {
            dynArray.append(i);
        }

        Assertions.assertEquals(dynArray.size(), 33);
        for (int i = 0; i < 33; i++) {
            Assertions.assertEquals(dynArray.getItem(i), i);
        }
    }

    @Test
    void insert_append_empty_success() {
        DynArray<Integer> dynArray = new DynArrayImpl<>(Integer.class);

        for (int i = 0; i < 33; i++) {
            dynArray.insert(i, i);
        }

        Assertions.assertEquals(dynArray.size(), 33);
        for (int i = 0; i < 33; i++) {
            Assertions.assertEquals(dynArray.getItem(i), i);
        }
    }

    @Test
    void insert_part_filled_success() {
        DynArray<Integer> dynArray = new DynArrayImpl<>(Integer.class);

        for (int i = 0; i < 8; i++) {
            dynArray.append(i);
        }
        Assertions.assertEquals(dynArray.size(), 8);

        for (int i = 0; i < 8; i++) {
            dynArray.insert(888, 1);
        }
        Assertions.assertEquals(dynArray.size(), 16);

        var expectedElements = List.of(0, 888, 888, 888, 888, 888, 888, 888, 888, 1, 2, 3, 4, 5, 6, 7);
        for (int i = 0; i < 16; i++) {
            Assertions.assertEquals(expectedElements.get(i), dynArray.getItem(i));
        }
    }

    @Test
    void insert_remove_part_filled_success() {
        DynArray<Integer> dynArray = new DynArrayImpl<>(Integer.class);

        for (int i = 0; i < 8; i++) {
            dynArray.append(i);
        }
        Assertions.assertEquals(dynArray.size(), 8);

        for (int i = 0; i < 8; i++) {
            dynArray.insert(888, 1);
        }
        Assertions.assertEquals(dynArray.size(), 16);

        for (int i = 0; i < 8; i++) {
            dynArray.remove(1);
        }
        Assertions.assertEquals(dynArray.size(), 8);

        for (int i = 0; i < 8; i++) {
            Assertions.assertEquals(i, dynArray.getItem(i));
        }
    }
}

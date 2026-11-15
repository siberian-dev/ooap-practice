package ru.lab.ooap.task7;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class HashTableImpTest {

    @Test
    void empty_put_success() {
        var table = new HashTableImpl(10);

        for (int i = 0; i < 10; i++) {
            table.put("ole " + i);
            Assertions.assertEquals(Status.SUCCESS.ordinal(), table.putStatus);
        }

        table.put("ole " + 10);
        Assertions.assertEquals(Status.NOT_PLACED.ordinal(), table.putStatus);
    }

    @Test
    void filled_find_success() {
        var table = new HashTableImpl(10);

        for (int i = 0; i < 10; i++) {
            table.put("ole " + i);
        }

        for (int i = 0; i < 10; i++) {
            table.find("ole " + i);
            Assertions.assertEquals(Status.SUCCESS.ordinal(), table.findStatus);
        }

        var result = table.find("ole " + 10);
        Assertions.assertEquals(Status.INVALID_INDEX.ordinal(), table.findStatus);
        Assertions.assertEquals(-1, result);
    }

    @Test
    void filled_removed_success() {
        var table = new HashTableImpl(10);

        for (int i = 0; i < 10; i++) {
            table.put("ole " + i);
        }

        for (int i = 0; i < 10; i++) {
            table.remove(i);
            Assertions.assertEquals(Status.SUCCESS.ordinal(), table.removeStatus);
        }

        table.remove(0);
        Assertions.assertEquals(Status.ALREADY_REMOVED.ordinal(), table.removeStatus);

        table.remove(11);
        Assertions.assertEquals(Status.INVALID_INDEX.ordinal(), table.removeStatus);
    }

}

package ru.lab.ooap.task8;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class NativeDictionaryImpTest {

    @Test
    void empty_put_success() {
        var table = new NativeDictionaryImpl<Integer>(10, Integer.class);

        for (int i = 0; i < 10; i++) {
            table.put("ole " + i, i);
            Assertions.assertEquals(Status.SUCCESS.ordinal(), table.getPutStatus());
        }

        table.put("ole " + 10, 10);
        Assertions.assertEquals(Status.NOT_PLACED.ordinal(), table.getPutStatus());
    }

    @Test
    void filled_has_key_success() {
        var table = new NativeDictionaryImpl<Integer>(10, Integer.class);

        for (int i = 0; i < 10; i++) {
            table.put("ole " + i, i);
        }

        for (int i = 0; i < 10; i++) {
            Assertions.assertTrue(table.hasKey("ole " + i));
        }

        Assertions.assertFalse(table.hasKey("ole " + 10));
    }

    @Test
    void filled_get_success() {
        var table = new NativeDictionaryImpl<Integer>(10, Integer.class);

        for (int i = 0; i < 10; i++) {
            table.put("ole " + i, i);
        }

        for (int i = 0; i < 10; i++) {
            Assertions.assertEquals(table.get("ole " + i), i);
            Assertions.assertEquals(Status.SUCCESS.ordinal(), table.getGetStatus());
        }

        Assertions.assertEquals(table.get("ole " + 10), null);
        Assertions.assertEquals(Status.NOT_EXISTING.ordinal(), table.getGetStatus());
    }

    @Test
    void filled_removed_success() {
        var table = new NativeDictionaryImpl<Integer>(10, Integer.class);

        for (int i = 0; i < 10; i++) {
            table.put("ole " + i, i);
        }

        for (int i = 0; i < 10; i++) {
            table.remove("ole " + i);
            Assertions.assertEquals(Status.SUCCESS.ordinal(), table.getRemoveStatus());
        }

        table.remove("ole " + 0);
        Assertions.assertEquals(Status.NOT_EXISTING.ordinal(), table.getRemoveStatus());

        table.remove("ole " + 11);
        Assertions.assertEquals(Status.NOT_EXISTING.ordinal(), table.getRemoveStatus());

        table.get("ole " + 0);
        Assertions.assertEquals(Status.NOT_EXISTING.ordinal(), table.getGetStatus());

        table.get("ole " + 11);
        Assertions.assertEquals(Status.NOT_EXISTING.ordinal(), table.getGetStatus());
    }

}

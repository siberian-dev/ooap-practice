package ru.lab.ooap.task9;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

class PowerSetImplTest {

    @Test
    void empty_put() {
        var set = new PowerSetImpl<String>(10, String.class);

        for (int i = 0; i < 10; i++) {
            set.put("ole " + i);
            Assertions.assertEquals(Status.SUCCESS.ordinal(), set.getPutStatus());
        }

        set.put("ole " + 10);
        Assertions.assertEquals(Status.NOT_ENOUGH_SPACE.ordinal(), set.getPutStatus());
    }

    @Test
    void filled_find() {
        var set = new PowerSetImpl<String>(10, String.class);

        for (int i = 0; i < 10; i++) {
            set.put("ole " + i);
        }

        for (int i = 0; i < 10; i++) {
            Assertions.assertTrue(set.get("ole " + i));
        }

        Assertions.assertFalse(set.get("ole " + 10));
    }

    @Test
    void filled_removed() {
        var set = new PowerSetImpl<String>(10, String.class);

        for (int i = 0; i < 10; i++) {
            set.put("ole " + i);
        }

        for (int i = 0; i < 10; i++) {
            set.remove("ole " + i);
            Assertions.assertEquals(Status.SUCCESS.ordinal(), set.getRemoveStatus());
        }

        set.remove("ole " + 0);
        Assertions.assertEquals(Status.NOT_EXISTING.ordinal(), set.getRemoveStatus());

        set.remove("ole " + 11);
        Assertions.assertEquals(Status.NOT_EXISTING.ordinal(), set.getRemoveStatus());
    }

    @Test
    void filled_difference() {
        var setA = prepareSetA();
        var setB = prepareSetB();

        var differenceA = setA.difference(setB);
        Assertions.assertArrayEquals(differenceA.values(), new String[]{"ole 5", "ole 6", "ole 7", "ole 8", "ole 9"});
        Assertions.assertEquals(Status.SUCCESS.ordinal(), setA.getDifferenceStatus());

        var differenceB = setB.difference(setA);
        Assertions.assertArrayEquals(differenceB.values(), new String[]{});
        Assertions.assertEquals(Status.SUCCESS.ordinal(), setB.getDifferenceStatus());
    }

    @Test
    void filled_intersection() {
        var setA = prepareSetA();
        var setB = prepareSetB();

        var intersectionA = setA.intersection(setB);
        Assertions.assertArrayEquals(intersectionA.values(), new String[]{"ole 0", "ole 1", "ole 2", "ole 3", "ole 4"});
        Assertions.assertEquals(Status.SUCCESS.ordinal(), setA.getIntersectionStatus());

        var intersectionB = setB.intersection(setA);
        Assertions.assertArrayEquals(intersectionB.values(), new String[]{"ole 0", "ole 1", "ole 2", "ole 3", "ole 4"});
        Assertions.assertEquals(Status.SUCCESS.ordinal(), setB.getIntersectionStatus());
    }

    @Test
    void filled_union() {
        var setA = prepareSetA();
        var setB = prepareSetB();
        var setC = prepareSetC();

        var unionA = setA.union(setB);
        Assertions.assertEquals(Set.of(unionA.values()), Set.of(setA.values()));
        Assertions.assertEquals(Status.SUCCESS.ordinal(), setA.getUnionStatus());

        var unionB = setB.union(setA);
        Assertions.assertEquals(Set.of(unionB.values()), Set.of(setA.values()));
        Assertions.assertEquals(Status.SUCCESS.ordinal(), setA.getUnionStatus());

        var unionC = setC.union(setA);
        Assertions.assertEquals(Set.of(unionC.values()), Set.of(setC.values()));
        Assertions.assertEquals(Status.SUCCESS.ordinal(), setC.getUnionStatus());
    }

    private PowerSet<String> prepareSetA() {
        var set = new PowerSetImpl<String>(10, String.class);
        for (int i = 0; i < 10; i++) {
            set.put("ole " + i);
        }

        return set;
    }

    private PowerSet<String> prepareSetB() {
        var set = new PowerSetImpl<String>(10, String.class);
        for (int i = 0; i < 5; i++) {
            set.put("ole " + i);
        }

        return set;
    }

    private PowerSet<String> prepareSetC() {
        var set = new PowerSetImpl<String>(10, String.class);
        for (int i = 0; i < 15; i++) {
            set.put("ole " + i);
        }

        return set;
    }
    
}

package ru.lab.ooap.task10;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class BloomFilterTest {

    @Test
    void fill_check_success() {
        BloomFilter<String> bloomFilter = new BloomFilterImpl<>();

        var valuesForFill = List.of(
            "hello", "hi", "salut", "privet"
        );

        for (var value : valuesForFill) {
            bloomFilter.add(value);
        }

        for (var value : valuesForFill) {
            Assertions.assertTrue(bloomFilter.contains(value));
        }

        var invalidValues = List.of(
            "bye", "adios", "poka"
        );

        for (var value : invalidValues) {
            Assertions.assertFalse(bloomFilter.contains(value));
        }
    }

}

package me.kotsu.sort;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Arrays;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class SortingAlgorithmsDynamicTest {

    private static Stream<Sorter> algorithms() {
        return Arrays.stream(SortersRegistry.values()).map(SortersRegistry::get);
    }

    @ParameterizedTest(name = "should sort correctly using {0}")
    @MethodSource("algorithms")
    void shouldSortArrayInAscendingOrder(Sorter sorter) {
        int[] data = {5, 3, 8, 4, 2};
        int[] expected = {2, 3, 4, 5, 8};
        sorter.sort(data);
        assertArrayEquals(expected, data);
    }

    @ParameterizedTest(name = "should handle reversed array using {0}")
    @MethodSource("algorithms")
    void shouldHandleReversedArray(Sorter sorter) {
        int[] data = {9, 7, 5, 3, 1};
        int[] expected = {1, 3, 5, 7, 9};
        sorter.sort(data);
        assertArrayEquals(expected, data);
    }

    @ParameterizedTest(name = "should handle duplicates using {0}")
    @MethodSource("algorithms")
    void shouldHandleDuplicates(Sorter sorter) {
        int[] data = {4, 2, 4, 1, 2};
        int[] expected = {1, 2, 2, 4, 4};
        sorter.sort(data);
        assertArrayEquals(expected, data);
    }

    @ParameterizedTest(name = "should handle empty array using {0}")
    @MethodSource("algorithms")
    void shouldHandleEmptyArray(Sorter sorter) {
        int[] data = {};
        int[] expected = {};
        sorter.sort(data);
        assertArrayEquals(expected, data);
    }
}
package me.kotsu.sort;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Arrays;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class SortingAlgorithmsDynamicTest {

    private static Stream<SortingAlgorithm> algorithms() {
        return Arrays.stream(SortingAlgorithmsRegistry.values()).map(SortingAlgorithmsRegistry::get);
    }

    @ParameterizedTest(name = "should sort correctly using {0}")
    @MethodSource("algorithms")
    void shouldSortArrayInAscendingOrder(SortingAlgorithm sorter) {
        int[] data = {5, 3, 8, 4, 2};
        int[] expected = {2, 3, 4, 5, 8};
        sorter.sort(data);
        assertArrayEquals(expected, data);
    }

    @ParameterizedTest(name = "should handle reversed array using {0}")
    @MethodSource("algorithms")
    void shouldHandleReversedArray(SortingAlgorithm sorter) {
        int[] data = {9, 7, 5, 3, 1};
        int[] expected = {1, 3, 5, 7, 9};
        sorter.sort(data);
        assertArrayEquals(expected, data);
    }

    @ParameterizedTest(name = "should handle duplicates using {0}")
    @MethodSource("algorithms")
    void shouldHandleDuplicates(SortingAlgorithm sorter) {
        int[] data = {4, 2, 4, 1, 2};
        int[] expected = {1, 2, 2, 4, 4};
        sorter.sort(data);
        assertArrayEquals(expected, data);
    }

    @ParameterizedTest(name = "should handle empty array using {0}")
    @MethodSource("algorithms")
    void shouldHandleEmptyArray(SortingAlgorithm sorter) {
        int[] data = {};
        int[] expected = {};
        sorter.sort(data);
        assertArrayEquals(expected, data);
    }
}
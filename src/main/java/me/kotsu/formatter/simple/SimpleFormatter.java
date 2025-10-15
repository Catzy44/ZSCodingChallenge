package me.kotsu.formatter.simple;

import java.util.Arrays;
import java.util.stream.Collectors;

import me.kotsu.formatter.Formatter;

public class SimpleFormatter implements Formatter {
    @Override
    public String format(int[] data) {
        return Arrays.stream(data)
        		.mapToObj(String::valueOf)
        		.collect(Collectors.joining(", "));
    }
}
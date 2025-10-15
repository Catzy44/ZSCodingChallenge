package me.kotsu.formatter;

public interface Formatter {
	/**
	 * Formats sorted data into a readable string.
	 *
	 * Behavior:
	 * - Valid input: returns formatted string
	 * - Null input: throws exception (formatting nothing makes no sense)
	 */
    String format(int[] data);
}
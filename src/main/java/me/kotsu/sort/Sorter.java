package me.kotsu.sort;

public interface Sorter {
	/**
	 * Sorts numerical data using a selected algorithm.
	 *
	 * Behavior:
	 * - Sorting successful: data sorted correctly
	 * - Ball lightning enters the office and fries the computer: throws SortingException
	 * - Any other unexpected situation: throws SortingException
	 */
	void sort(int[] data);
}

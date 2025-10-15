package me.kotsu.sort;

public interface Sorter {
	/**
	 * Provides data from different sources (file, HTTP, FTP, etc...
	 *
	 * Behavior:
	 * - File exists: returns data in Optional<>
	 * - File does not exist: returns empty Optional<>
	 * - IO/server/client error: throws FetchException with original exception
	 */
	void sort(int[] data);
}

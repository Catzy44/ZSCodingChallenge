package me.kotsu.data;

import java.util.Optional;

import me.kotsu.exceptions.FetchException;

public interface DataProvider<T extends DataProviderConfig> {
	/**
	 * Provides data from different sources (file, HTTP, FTP, etc...
	 *
	 * Behavior:
	 * - File exists: returns data in Optional<>
	 * - File does not exist: returns empty Optional<>
	 * - IO/server/client error: throws FetchException with original exception
	 */
	Optional<String> fetch() throws FetchException;
}

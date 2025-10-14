package me.kotsu.data;

import java.util.Optional;

import me.kotsu.exceptions.FetchException;

public interface DataProvider<T extends DataProviderConfig> {
	Optional<String> fetch() throws FetchException;
}

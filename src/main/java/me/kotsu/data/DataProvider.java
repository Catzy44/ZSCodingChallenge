package me.kotsu.data;

import java.util.Optional;

public interface DataProvider<T extends DataProviderConfig> {
	Optional<String> fetch();
}

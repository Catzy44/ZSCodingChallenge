package me.kotsu.data.dumb;

import java.util.Optional;

import me.kotsu.data.DataProvider;
import me.kotsu.exceptions.FetchException;

public class DumbDataProvider implements DataProvider<DumbDataProviderConfig> {
	
	private DumbDataProviderConfig config;
	public DumbDataProvider(DumbDataProviderConfig config) {
		this.config = config;
	}
	@Override
	public Optional<String> fetch() throws FetchException {
		return config.resp();
	}
}

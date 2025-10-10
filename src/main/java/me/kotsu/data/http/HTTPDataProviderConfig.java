package me.kotsu.data.http;

import java.net.URL;

import me.kotsu.data.DataProviderConfig;

public record HTTPDataProviderConfig(
		URL url,
		int timeout
) implements DataProviderConfig {}

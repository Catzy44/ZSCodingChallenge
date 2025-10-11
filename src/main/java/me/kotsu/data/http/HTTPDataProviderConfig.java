package me.kotsu.data.http;

import java.net.URI;
import java.nio.charset.Charset;

import me.kotsu.data.DataProviderConfig;

public record HTTPDataProviderConfig(
		URI url,
		int timeout,
		Charset decoderCharset
) implements DataProviderConfig {}

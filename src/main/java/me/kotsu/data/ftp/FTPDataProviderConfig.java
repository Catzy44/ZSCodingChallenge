package me.kotsu.data.ftp;

import java.nio.charset.Charset;

import me.kotsu.data.DataProviderConfig;

public record FTPDataProviderConfig(
		String server,
		int port,
		String user,
		String password,
		String path,
		Charset decoderCharset
) implements DataProviderConfig {}

package me.kotsu.data.ftp;

import me.kotsu.data.DataProviderConfig;

public record FTPDataProviderConfig(
		String server,
		int port,
		String user,
		String password,
		String path
) implements DataProviderConfig {}

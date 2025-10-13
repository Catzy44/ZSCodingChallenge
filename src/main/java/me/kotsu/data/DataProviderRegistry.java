package me.kotsu.data;

import me.kotsu.data.file.FileDataProvider;
import me.kotsu.data.ftp.FTPDataProvider;
import me.kotsu.data.http.HTTPDataProvider;


public enum DataProviderRegistry {
    FILE(FileDataProvider.class),
	HTTP(HTTPDataProvider.class),
	FTP(FTPDataProvider.class);

	private final Class<? extends DataProvider<?>> classToCreate;

	DataProviderRegistry(Class<? extends DataProvider<?>> class1) {
        this.classToCreate = class1;
    }

    /**
     * @return new instance of choosen DataProvider class
     */
    public DataProvider<?> create(DataProviderConfig config) {
        try {
			return classToCreate.getConstructor(config.getClass()).newInstance(config);
		} catch (Exception e) {
			throw new RuntimeException("failed to instantiate DataProvider class", e);
		}
    }
}
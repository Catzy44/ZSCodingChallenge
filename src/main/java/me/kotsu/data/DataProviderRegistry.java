package me.kotsu.data;

import java.util.Arrays;
import java.util.stream.Stream;

import me.kotsu.data.file.FileDataProvider;
import me.kotsu.data.ftp.FTPDataProvider;
import me.kotsu.data.http.HTTPDataProvider;


public enum DataProviderRegistry {
    FILE(FileDataProvider.class),
	HTTP(HTTPDataProvider.class),
	FTP(FTPDataProvider.class);

	private final Class<? extends DataProvider<?>> selectedClass;

	DataProviderRegistry(Class<? extends DataProvider<?>> class1) {
        this.selectedClass = class1;
    }
	
	private Class<?> getAsClass() {
        return selectedClass;
    }

    /**
     * @return new instance of choosen DataProvider class
     */
    public DataProvider<?> create(DataProviderConfig config) {
        try {
			return selectedClass.getConstructor(config.getClass()).newInstance(config);
		} catch (Exception e) {
			throw new RuntimeException("failed to instantiate DataProvider class", e);
		}
    }
    
    public static Stream<Class<?>> getAllClasses() {
        return Arrays.stream(DataProviderRegistry.values()).map(DataProviderRegistry::getAsClass);
    }
}
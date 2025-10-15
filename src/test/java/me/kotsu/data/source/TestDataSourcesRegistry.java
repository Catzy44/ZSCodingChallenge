package me.kotsu.data.source;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.kotsu.data.DataProviderRegistry;
import me.kotsu.parser.Parser;

public enum TestDataSourcesRegistry {
    FILE(new FileDataSource()),
	HTTP(new HTTPDataSource()),
	FTP(new FTPDataSource());
	
	private static final Logger logger = LoggerFactory.getLogger(TestDataSourcesRegistry.class);

	private final TestDataSource instance;

	TestDataSourcesRegistry(TestDataSource instance) {
        this.instance = instance;
    }

    /**
     * @return singleton instance of selected {@link Parser}
     */
    public TestDataSource get() {
        return instance;
    }
    
    public static Stream<TestDataSource> streamSingletons() {
        return Arrays.stream(TestDataSourcesRegistry.values()).map(TestDataSourcesRegistry::get);
    }
    
    public static List<Class<?>> getDataProvidersClassesExcludedFromThrowingTests() {
    	Set<String> excludedProvidersClasses = streamSingletons()
    		.filter(TestDataSource::excludeFromThrowingTest)
    		.map(TestDataSource::dataProviderClass)
    		.map(Class::getName)
    		.collect(Collectors.toUnmodifiableSet());
    	
    	List<Class<?>> list = DataProviderRegistry.getClasses()
    			.filter(c -> excludedProvidersClasses.contains(c.getName()))
    			.toList();
    	
    	return list;
    }
    
    public static void startAllTestDataSources() throws Exception {
		for(TestDataSource source : streamSingletons().toList()) {
			source.enable();
		}
	}
	public static void stopAllTestDataSources() throws Exception {
		for(TestDataSource source : streamSingletons().toList()) {
			source.disable();
		}
	}
}
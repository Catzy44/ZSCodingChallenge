package me.kotsu.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import me.kotsu.AppUtils;
import me.kotsu.config.test.AppConfigurationTest;
import me.kotsu.data.source.TestDataSourcesRegistry;
import me.kotsu.exceptions.FetchException;

public class DataProvidersGenericTest {
	private static AppConfigurationTest configTest;

	@BeforeAll
	static void setUp() throws Exception {
		configTest = new AppConfigurationTest();
		TestDataSourcesRegistry.startAllTestDataSources();
	}

	@AfterAll
	static void tearDown() throws Exception {
		TestDataSourcesRegistry.stopAllTestDataSources();
	}
    
    @Test
    void returnsCorrectFileContentIfPresent() throws IOException {
    	configTest.buildTestDataProviders(false);
    	
    	for(DataProvider<?> p : configTest.getTestProviders()) {
    		String providerName = p.getClass().getName();
    		
			Optional<String> dataFetched = p.fetch();
			assertTrue(dataFetched.isPresent(), String.format("DataProvider %s should return data!", providerName));

			String fetched = dataFetched.get();
			String expected = AppUtils.decodeBytesToString(AppUtils.getFullTestFileContent(), configTest.getDecoderCharset());

			assertEquals(expected, fetched, String.format("DataProvider %s fetched content should equals test file content!", providerName));
		}
    }

    @Test
    void returnsEmptyIfFileMissing() throws IOException {
    	configTest.buildTestDataProviders(true);
    	
    	for(DataProvider<?> p : configTest.getTestProviders()) {
    		String providerName = p.getClass().getName();
    		
			Optional<String> dataFetched = p.fetch();
			assertTrue(dataFetched.isEmpty(), String.format("DataProvider %s should return nothing if the file is missing!", providerName));
		}
    }
    
    @Test
    void throwsIfServersAreDown() throws Exception {
    	configTest.buildTestDataProviders(false);
    	
    	//after every single data source should throw
    	//some data providers can be ommitted from this test in AppConfigurationTest
    	TestDataSourcesRegistry.stopAllTestDataSources();
    	
    	List<Class<?>> exclusionsFromThisTest = TestDataSourcesRegistry.getDataProvidersClassesExcludedFromThrowingTests();
    	for(DataProvider<?> p : configTest.getTestProviders()) {
    		if(exclusionsFromThisTest.contains(p.getClass())) {
    			continue;
    		}
    		String providerName = p.getClass().getName();
    		
    		assertThrows(FetchException.class, () -> p.fetch(), String.format("DataProvider %s should throw if the server is down", providerName));
    	}
    }
}

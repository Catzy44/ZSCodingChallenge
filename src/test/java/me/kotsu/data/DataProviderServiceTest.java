package me.kotsu.data;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import me.kotsu.config.test.AppConfigurationTest;

public class DataProviderServiceTest {
	private static AppConfigurationTest configTest;
	private DataProviderService service;
	
    @BeforeEach
    void setUp() throws IOException {
    	configTest = new AppConfigurationTest();
    	service = configTest.buildDataProviderService();
    }

    @AfterEach
    void tearDown() throws IOException {
    	
    }
    
    @Test
	public void returnsNothingIfNoDataPresent() {
		
		assertTrue(service.fetchFirstAvailableDataSource().isEmpty());
	}
	
	@Test
	public void returnsTrueIfOnlyFilePlaced() throws IOException {
		//configTest.getTestFile().place();
		
		//assertTrue(service.fetchFirstAvailableDataSource().isPresent());
	}
}

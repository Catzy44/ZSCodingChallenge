package me.kotsu;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.kotsu.config.test.AppConfigurationTest;
import me.kotsu.data.source.TestDataSourcesRegistry;
import me.kotsu.exceptions.ParsingException;
import me.kotsu.formatter.Formatter;
import me.kotsu.parser.Parser;
import me.kotsu.sorter.Sorter;

public class MainServiceTest {
	private static final Logger logger = LoggerFactory.getLogger(MainServiceTest.class);
	
	@BeforeAll
	static void setUp() throws Exception {
		TestDataSourcesRegistry.startAllTestDataSources();
	}

	@AfterAll
	static void tearDown() throws Exception {
		TestDataSourcesRegistry.stopAllTestDataSources();
	}
	
	@Test
	public void shouldReturnProperlyFormattedResponse() throws ParsingException, IOException {
		AppConfigurationTest configTest = new AppConfigurationTest();
		Parser parser = configTest.buildParser();
	    Sorter sorter = configTest.buildSorter();
	    Formatter formatter = configTest.buildFormatter();
		
		configTest.buildTestDataProviders(false);
		
		MainService serviceMain = new MainService(configTest);
		
		String testFileDecoded = AppUtils.decodeBytesToString(AppUtils.getFullTestFileContent(), configTest.getDecoderCharset());
		int[] parsedContent = parser.parse(testFileDecoded).get();
		sorter.sort(parsedContent);
		String finalOutput = formatter.format(parsedContent);
		
		assertEquals(serviceMain.start(), finalOutput);
	}
}

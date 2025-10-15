package me.kotsu;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.kotsu.config.test.AppConfigurationTest;
import me.kotsu.exceptions.ParsingException;

public class MainServiceTest {
	private static final Logger logger = LoggerFactory.getLogger(MainServiceTest.class);
	
	@Test
	public void shouldReturnProperlyFormattedResponse() throws ParsingException, IOException {
		AppConfigurationTest configTest = new AppConfigurationTest();
		configTest.getTestFile().place();
		
		MainService serviceMain = new MainService(configTest);
		
		String testFileDecoded = AppUtils.decodeBytesToString(AppUtils.getFullTestFileContent(), configTest.getDecoderCharset());
		int[] parsedContent = configTest.buildParser().parse(testFileDecoded).get();
		configTest.buildSorter().sort(parsedContent);
		String finalOutput = serviceMain.formatOutput(parsedContent);
		
		assertEquals(serviceMain.start(), finalOutput);
	}
	
	@Test
	public void formatsOutput() {
		AppConfigurationTest configTest = new AppConfigurationTest();
		MainService serviceMain = new MainService(configTest);
		
		int[] elements = new int[] {0,1,2,3,4};
		String formatted = serviceMain.formatOutput(elements);
		assertEquals("0, 1, 2, 3, 4", formatted);
	}
}

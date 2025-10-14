package me.kotsu.data.file;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import me.kotsu.AppUtils;
import me.kotsu.config.test.AppConfigurationTest;

class FileDataProviderTest {
	private static AppConfigurationTest configTest = new AppConfigurationTest();
    private FileDataProvider fileProvider;

    @BeforeEach
    void setUp() throws IOException {
    	configTest.getTestFile().place();
        fileProvider = configTest.buildTestFileDataProvider();
    }

    @AfterEach
    void tearDown() throws IOException {
    	configTest.getTestFile().delete();
    }

    @Test
    void returnsCorrectFileContentIfPresent() throws IOException {
        Optional<String> dataFetched = fileProvider.fetch();
        assertTrue(dataFetched.isPresent(), "FileDataProvider should return data");

        String fetched = dataFetched.get();
        String expected = AppUtils.decodeBytesToString(AppUtils.getFullTestFileContent(), configTest.getDecoderCharset());

        assertEquals(expected, fetched, "fetched content should == file content");
    }

    @Test
    void returnsEmptyIfFileMissing() throws IOException {
        tearDown(); // symulacja braku pliku
        
        Optional<String> dataFetched = fileProvider.fetch();
        assertTrue(dataFetched.isEmpty(), "should return nothing if the file is missing");
    }
}

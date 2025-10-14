package me.kotsu.data.http;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import me.kotsu.AppUtils;
import me.kotsu.config.test.AppConfigurationTest;
import me.kotsu.exceptions.FetchException;

class HTTPDataProviderTest {
	private static AppConfigurationTest configTest = new AppConfigurationTest();
    private static HTTPDataProvider httpDataProvider;

    @BeforeAll
    public static void setUp() throws IOException {
    	configTest.getTestHttpServer().bootWithTestRoutesAndFiles();
    	httpDataProvider = configTest.buildTestHTTPDataProvider();
    }

    @AfterAll
    public static void tearDown() {
    	configTest.getTestHttpServer().kill();
    }

    @Test
    public void returnsCorrectFileContentIfPresent() throws IOException {
        Optional<String> dataFetched = httpDataProvider.fetch();
        assertTrue(dataFetched.isPresent(), "HTTPDataProvider should return data");

        String fetched = dataFetched.get();
        String expected = AppUtils.decodeBytesToString(AppUtils.getFullTestFileContent(), configTest.getDecoderCharset());

        assertEquals(expected, fetched, "fetched content should == file content");
    }
    
    @Test
    public void returnsEmptyIfTheFileIsMissing() throws IOException, InterruptedException, URISyntaxException {
    	HTTPDataProvider brokenProvider = new HTTPDataProvider(
    			new HTTPDataProviderConfig(new URI(
    					String.format("http://localhost:%d/nonExistingFile.json", configTest.getTestHttpServer().getPort())
    			), 10 , configTest.getDecoderCharset())
    	);
    	
        Optional<String> dataFetched = brokenProvider.fetch();
        
        assertTrue(dataFetched.isEmpty(), "HTTPDataProvider should return nothing and do not throw on non-existsnt file!");
    }

    @Test
    public void throwsIfTheServerIsDown() throws IOException {
        tearDown(); // symulacja braku serwera
        
        assertThrows(FetchException.class, () -> httpDataProvider.fetch(), "should throw if the server is down");
    }
}

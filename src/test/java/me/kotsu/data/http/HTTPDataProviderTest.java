package me.kotsu.data.http;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import me.kotsu.AppUtils;

class HTTPDataProviderTest {

    private static final Charset DECODER_CHARSET = StandardCharsets.UTF_8;
    
    private HTTPDataProviderTestHTTPServer testServer;
    private HTTPDataProvider httpDataProvider;
    

    @BeforeAll
    void setUp() throws IOException {
    	testServer = new HTTPDataProviderTestHTTPServer();
    	testServer.bootWithTestRoutesAndFiles();
    	
    	httpDataProvider = new HTTPDataProvider(new HTTPDataProviderConfig(testServer.getTestFileURI(), 1000, DECODER_CHARSET));
    }

    @AfterAll
    void tearDown() {
    	testServer.kill();
    }

    @Test
    void returnsCorrectFileContentIfPresent() throws IOException {
        Optional<String> dataFetched = httpDataProvider.fetch();
        assertTrue(dataFetched.isPresent(), "HTTPDataProvider should return data");

        String fetched = dataFetched.get();
        String expected = AppUtils.decodeBytesToString(AppUtils.getFullTestFileContent(), DECODER_CHARSET);

        assertEquals(expected, fetched, "fetched content should == file content");
    }

    @Test
    void returnsEmptyIfFileMissing() throws IOException {
        tearDown(); // symulacja braku serwera
        
        Optional<String> dataFetched = httpDataProvider.fetch();
        assertTrue(dataFetched.isEmpty(), "should return nothing if the server is down");
    }
}

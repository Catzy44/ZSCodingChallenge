package me.kotsu.data.http;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.sun.net.httpserver.HttpServer;

import me.kotsu.AppUtils;

class HTTPDataProviderTest {

    private static final Charset DECODER_CHARSET = StandardCharsets.UTF_8;

    private HTTPDataProvider httpDataProvider;
    private static HttpServer server;
    private URI testFileURI;

    private void bootHttpServerAndInitTestRoute() throws IOException {
    	//init serwera na dowolnym WOLNYM porcie...
    	server = HttpServer.create(new InetSocketAddress(0), 0);
        int port = server.getAddress().getPort();

        //nasza lista.json
        server.createContext("/lista.json", exchange -> {
            byte[] data = AppUtils.getFullTestFileContent();
            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, data.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(data);
            }
        });

        server.start();
        testFileURI = URI.create("http://localhost:" + port + "/lista.json");
    }
    private void killHttpServer() {
    	if(server != null) {
    		server.stop(0);
    	}
        server = null;//niech go tam GC posprząta szybciutko
    }

    @BeforeAll
    void setUp() throws IOException {
    	bootHttpServerAndInitTestRoute();
    	httpDataProvider = new HTTPDataProvider(new HTTPDataProviderConfig(testFileURI, 1000, DECODER_CHARSET));
    }

    @AfterAll
    void tearDown() {
    	killHttpServer();
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

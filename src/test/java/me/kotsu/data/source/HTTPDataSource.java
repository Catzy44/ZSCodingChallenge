package me.kotsu.data.source;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;

import com.sun.net.httpserver.HttpServer;

import lombok.Getter;
import me.kotsu.AppUtils;
import me.kotsu.data.http.HTTPDataProvider;

@Getter
public class HTTPDataSource implements TestDataSource {
	private static HttpServer server;
    private URI testFileURI;
    private int port;
    
    @Override
	public void enable() throws IOException {
    	//init serwera na dowolnym WOLNYM porcie...
    	server = HttpServer.create(new InetSocketAddress(0), 0);
        port = server.getAddress().getPort();

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
    
    @Override
	public void disable() {
    	if(server != null) {
    		server.stop(0);
    	}
        server = null;//niech go tam GC posprząta szybciutko
    }

	@Override
	public boolean excludeFromThrowingTest() {
		return false;
	}
	
	@Override
	public Class<?> dataProviderClass() {
		return HTTPDataProvider.class;
	}
}

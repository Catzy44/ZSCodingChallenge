package me.kotsu.data.http;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;

import com.sun.net.httpserver.HttpServer;

import lombok.Getter;
import me.kotsu.AppUtils;

@Getter
public class HTTPDataProviderTestHTTPServer {
	private static HttpServer server;
    private URI testFileURI;
    private int port;
    
	public void bootWithTestRoutesAndFiles() throws IOException {
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
	public void kill() {
    	if(server != null) {
    		server.stop(0);
    	}
        server = null;//niech go tam GC posprząta szybciutko
    }
}

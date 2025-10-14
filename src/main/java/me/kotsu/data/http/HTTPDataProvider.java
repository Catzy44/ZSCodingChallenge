package me.kotsu.data.http;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpResponse;

import me.kotsu.AppUtils;
import me.kotsu.data.DataProvider;
import me.kotsu.exceptions.FetchException;

public class HTTPDataProvider implements DataProvider<HTTPDataProviderConfig> {
	private HTTPDataProviderConfig config;
	public HTTPDataProvider(HTTPDataProviderConfig config) {
		this.config = config;
	}

	@Override
	public Optional<String> fetch() throws FetchException {
		if(config == null || config.url() == null) {
			return Optional.empty();
		}
		try (CloseableHttpClient client = HttpClients.createDefault()) {//samo pozamyka co trzeba
			
			HttpGet request = new HttpGet(config.url());
			try (ClassicHttpResponse response = client.executeOpen(null, request, null)) {
				int code = response.getCode();
				
	            if (code == 204 || code == 404 || code == 410 || response == null || response.getEntity() == null) {
	            	return Optional.empty(); //no data - return empty response
	            }

	            if (code < 200 || code >= 300) {
	            	throw new FetchException("HTTP " + code, null); //error - throw
	            }
				
				return readResponseEntity(response); //we have data - return
			}
		} catch (IOException e) {
			throw new FetchException("HTTP exception: ", e);//IO error - throw
		}
	}
	
	private Optional<String> readResponseEntity(ClassicHttpResponse response) throws IOException {
        try (InputStream stream = response.getEntity().getContent()) {
            byte[] allFileBytes = stream.readAllBytes();
            return Optional.of(AppUtils.decodeBytesToString(allFileBytes, config.decoderCharset()));
        }
    }
}

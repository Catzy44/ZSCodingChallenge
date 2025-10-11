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

public class HTTPDataProvider implements DataProvider<HTTPDataProviderConfig> {
	private HTTPDataProviderConfig config;
	public HTTPDataProvider(HTTPDataProviderConfig config) {
		this.config = config;
	}

	@Override
	public Optional<String> fetch() {
		try (CloseableHttpClient client = HttpClients.createDefault()) {//samo pozamyka co trzeba
			
			HttpGet request = new HttpGet(config.url());
			try (ClassicHttpResponse response = client.executeOpen(null, request, null)) {
				if(!isSuccessfull(response)) {
					return Optional.empty();
				}
				
				return readResponseEntity(response);
			}
		} catch (IOException ignored) {
			//e.printStackTrace();
		}
		
		return Optional.empty();
	}
	
	private Optional<String> readResponseEntity(ClassicHttpResponse response) throws IOException {
        try (InputStream stream = response.getEntity().getContent()) {
            byte[] allFileBytes = stream.readAllBytes();
            return Optional.of(AppUtils.decodeBytesToString(allFileBytes, config.decoderCharset()));
        }
    }
	
	private boolean isSuccessfull(ClassicHttpResponse response) {
		if(response == null || response.getEntity() == null) {
			return false;
		}
		int responseStatusCode = response.getCode();
		return responseStatusCode >= 200 && responseStatusCode < 300; // 200-300 -> OK
	}
}

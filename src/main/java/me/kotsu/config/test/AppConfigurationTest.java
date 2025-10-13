package me.kotsu.config.test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import me.kotsu.config.AppConfiguration;
import me.kotsu.data.DataProviderService;
import me.kotsu.data.file.FileDataProvider;
import me.kotsu.data.file.FileDataProviderConfig;
import me.kotsu.data.ftp.FTPDataProvider;
import me.kotsu.data.ftp.FTPDataProviderConfig;
import me.kotsu.data.http.HTTPDataProvider;
import me.kotsu.data.http.HTTPDataProviderConfig;
import me.kotsu.parser.Parser;
import me.kotsu.parser.ParsersRegistry;
import me.kotsu.sort.SortingAlgorithmsRegistry;
import me.kotsu.sort.SortingService;

public class AppConfigurationTest implements AppConfiguration {
	public final Charset STRING_DECODER_CHARSET = StandardCharsets.UTF_8;
	private Path listaTestFileTemp;
	
	public AppConfigurationTest() {
		try {
			listaTestFileTemp = Files.createTempFile(Math.random() + "_lista", ".tmp");
			InputStream is = getClass().getResourceAsStream("lista.json");

			Files.write(listaTestFileTemp, is.readAllBytes());
		} catch (IOException e) {
			throw new IllegalStateException("failed to copy test configuration", e);
		}
	}

	public DataProviderService buildFactory() {
		return DataProviderService.builder()
				.add(new FileDataProvider(new FileDataProviderConfig(listaTestFileTemp, STRING_DECODER_CHARSET)))
				.add(new HTTPDataProvider(new HTTPDataProviderConfig(URI.create("https://zaiks.org.pl/dane/lista.json"), 10 , STRING_DECODER_CHARSET)))
				.add(new FTPDataProvider(new FTPDataProviderConfig("ftp.server.com", 21, "/lista.json", "user", "pass" , STRING_DECODER_CHARSET)))
				.build();
	}
	
	public Parser buildParser() {
		return ParsersRegistry.JSON.get();
	}
	
	public SortingService buildSorter() {
		return new SortingService(SortingAlgorithmsRegistry.BUBBLE.get()); 
	}

	@Override
	public void cleanUp() {
		try {
			Files.delete(listaTestFileTemp);
		} catch (IOException e) {
			throw new IllegalStateException("failed to clean up test configuration", e);
		}
	}
}

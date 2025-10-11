package me.kotsu.config.prod;

import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import me.kotsu.config.AppConfiguration;
import me.kotsu.data.DataProviderFactory;
import me.kotsu.data.file.FileDataProvider;
import me.kotsu.data.file.FileDataProviderConfig;
import me.kotsu.data.ftp.FTPDataProvider;
import me.kotsu.data.ftp.FTPDataProviderConfig;
import me.kotsu.data.http.HTTPDataProvider;
import me.kotsu.data.http.HTTPDataProviderConfig;
import me.kotsu.parser.Parser;
import me.kotsu.parser.json.JsonParser;
import me.kotsu.sort.BubbleSortingAlhorithm;
import me.kotsu.sort.SortingAlgorithm;
import me.kotsu.sort.SortingService;

public class AppConfigurationProd implements AppConfiguration {
	public static final Charset STRING_DECODER_CHARSET = StandardCharsets.UTF_8;
	public static final SortingAlgorithm SORTING_ALGORITHM = new BubbleSortingAlhorithm();
	
	public DataProviderFactory buildFactory() {
		return DataProviderFactory.builder()
				.add(new FileDataProvider(new FileDataProviderConfig(Path.of("Z:/dane/lista.json"), StandardCharsets.UTF_8)))
				.add(new HTTPDataProvider(new HTTPDataProviderConfig(URI.create("https://zaiks.org.pl/dane/lista.json"), 10 , StandardCharsets.UTF_8)))
				.add(new FTPDataProvider(new FTPDataProviderConfig("ftp.server.com", 21, "/lista.json", "user", "pass" , StandardCharsets.UTF_8)))
				.build();
	}
	
	public Parser buildParser() {
		return new JsonParser();
	}
	
	public SortingService buildSorter() {
		return new SortingService(AppConfigurationProd.SORTING_ALGORITHM); 
	}
}

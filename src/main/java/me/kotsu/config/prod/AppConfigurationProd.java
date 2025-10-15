package me.kotsu.config.prod;

import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import me.kotsu.config.AppConfiguration;
import me.kotsu.data.DataProviderRegistry;
import me.kotsu.data.DataProviderService;
import me.kotsu.data.file.FileDataProviderConfig;
import me.kotsu.data.ftp.FTPDataProviderConfig;
import me.kotsu.data.http.HTTPDataProviderConfig;
import me.kotsu.monitoring.MeasureSorterTimeDecorator;
import me.kotsu.parser.Parser;
import me.kotsu.parser.ParsersRegistry;
import me.kotsu.sort.Sorter;
import me.kotsu.sort.SortersRegistry;

public class AppConfigurationProd implements AppConfiguration {
	public final Charset decoderCharset = StandardCharsets.UTF_8;
	
	@Override
	public DataProviderService buildDataProviderService() {
		return DataProviderService.builder()
				.add(DataProviderRegistry.FILE.create(new FileDataProviderConfig(Path.of("Z:/dane/lista.json"), decoderCharset)))
				.add(DataProviderRegistry.HTTP.create(new HTTPDataProviderConfig(URI.create("https://zaiks.org.pl/dane/lista.json"), 10 , decoderCharset)))
				.add(DataProviderRegistry.FTP.create(new FTPDataProviderConfig("ftp.server.com", 21, "/lista.json", "user", "pass" , decoderCharset)))
				.build();
	}
	
	@Override
	public Parser buildParser() {
		return ParsersRegistry.JSON.get();
	}
	
	@Override
	public Sorter buildSorter() {
		return new MeasureSorterTimeDecorator(SortersRegistry.BUBBLE.get()); 
	}
}

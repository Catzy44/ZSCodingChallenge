package me.kotsu.config.test;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import me.kotsu.config.AppConfiguration;
import me.kotsu.data.DataProvider;
import me.kotsu.data.DataProviderService;
import me.kotsu.data.file.FileDataProvider;
import me.kotsu.data.file.FileDataProviderConfig;
import me.kotsu.data.ftp.FTPDataProvider;
import me.kotsu.data.ftp.FTPDataProviderConfig;
import me.kotsu.data.http.HTTPDataProvider;
import me.kotsu.data.http.HTTPDataProviderConfig;
import me.kotsu.data.source.FTPDataSource;
import me.kotsu.data.source.FileDataSource;
import me.kotsu.data.source.HTTPDataSource;
import me.kotsu.data.source.TestDataSourcesRegistry;
import me.kotsu.formatter.Formatter;
import me.kotsu.formatter.FormattersRegistry;
import me.kotsu.monitoring.MeasureSorterTimeDecorator;
import me.kotsu.parser.Parser;
import me.kotsu.parser.ParsersRegistry;
import me.kotsu.sort.Sorter;
import me.kotsu.sort.SortersRegistry;

@Getter
public class AppConfigurationTest implements AppConfiguration {
	public final Charset decoderCharset = StandardCharsets.UTF_8;
	
	/* TEST RESOURCES - INITIALIZED BUT NOT STARTED */
	/* THIS TEST SERVERS ARE RESPONSIBLE FOR INITIALIZING TEST RESOURCES*/
	
	
	/* THIS ARE DATA PROVIDERS - RESPONSIBLE FOR FETCHING TEST RESOURCES FROM TEST SERVERS */
	private List<DataProvider<?>> testProviders = new ArrayList<DataProvider<?>>();
	public void buildTestDataProviders(boolean shouldFilesBeMissing) {
		FileDataSource file = (FileDataSource) TestDataSourcesRegistry.FILE.get();
		HTTPDataSource http = (HTTPDataSource) TestDataSourcesRegistry.HTTP.get();
		FTPDataSource ftp = (FTPDataSource) TestDataSourcesRegistry.FTP.get();
		
		testProviders.clear();
		
		testProviders.add(new FileDataProvider(new FileDataProviderConfig(
				shouldFilesBeMissing ? file.getFile().getParent().resolve("nonExisting.json") : file.getFile()
				, decoderCharset)));	
		
		testProviders.add(new HTTPDataProvider(new HTTPDataProviderConfig(
				shouldFilesBeMissing ? http.getTestFileURI().resolve("nonExisting.json") : http.getTestFileURI()
				, 10 , decoderCharset)));
		
		testProviders.add(new FTPDataProvider(new FTPDataProviderConfig(
				"localhost", 
				ftp.getServerPort(), 
				ftp.getUsername(), 
				ftp.getPassword(), 
				shouldFilesBeMissing ? "/nonExisting.json" : "/"+ftp.getTestFileName(), 
				decoderCharset
		)));
	}
	
	@Override
	public DataProviderService buildDataProviderService() {
		return DataProviderService.builder()
				.addAll(testProviders)
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
	
	@Override
	public Formatter buildFormatter() {
		return FormattersRegistry.SIMPLE.get();
	}
}

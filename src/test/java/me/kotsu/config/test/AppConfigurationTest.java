package me.kotsu.config.test;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import lombok.Getter;
import me.kotsu.config.AppConfiguration;
import me.kotsu.data.DataProviderService;
import me.kotsu.data.file.FileDataProvider;
import me.kotsu.data.file.FileDataProviderConfig;
import me.kotsu.data.file.FileDataProviderTestFile;
import me.kotsu.data.ftp.FTPDataProvider;
import me.kotsu.data.ftp.FTPDataProviderConfig;
import me.kotsu.data.ftp.FTPDataProviderTestFTPServer;
import me.kotsu.data.http.HTTPDataProvider;
import me.kotsu.data.http.HTTPDataProviderConfig;
import me.kotsu.data.http.HTTPDataProviderTestHTTPServer;
import me.kotsu.monitoring.MeasureSorterTimeDecorator;
import me.kotsu.parser.Parser;
import me.kotsu.parser.ParsersRegistry;
import me.kotsu.sort.SortingAlgorithm;
import me.kotsu.sort.SortingAlgorithmsRegistry;

@Getter
public class AppConfigurationTest implements AppConfiguration {
	public final Charset decoderCharset = StandardCharsets.UTF_8;
	
	/* TEST RESOURCES - INITIALIZED BUT NOT STARTED */
	/* THIS TEST SERVERS ARE RESPONSIBLE FOR INITIALIZING TEST RESOURCES*/
	private HTTPDataProviderTestHTTPServer testHttpServer = new HTTPDataProviderTestHTTPServer();
	private FTPDataProviderTestFTPServer testFtpServer = new FTPDataProviderTestFTPServer();
	private FileDataProviderTestFile testFile = new FileDataProviderTestFile();
	
	/*  */
	public FileDataProvider buildTestFileDataProvider() {
		return new FileDataProvider(new FileDataProviderConfig(testFile.getFile(), decoderCharset));
	}
	
	public HTTPDataProvider buildTestHTTPDataProvider() {
		return new HTTPDataProvider(new HTTPDataProviderConfig(testHttpServer.getTestFileURI(), 10 , decoderCharset));
	}

	public FTPDataProvider buildTestFTPDataProvider() {
		return new FTPDataProvider(new FTPDataProviderConfig(
				"localhost", 
				testFtpServer.getServerPort(), 
				testFtpServer.getUsername(), 
				testFtpServer.getPassword(), 
				"/"+testFtpServer.getTestFileName(), 
				decoderCharset
		));
	}

	@Override
	public DataProviderService buildDataProviderService() {
		return DataProviderService.builder()
				.add(buildTestFileDataProvider())
				.add(buildTestHTTPDataProvider())
				.add(buildTestFTPDataProvider())
		.build();
	}
	
	@Override
	public Parser buildParser() {
		return ParsersRegistry.JSON.get();
	}
	
	@Override
	public SortingAlgorithm buildSorter() {
		return new MeasureSorterTimeDecorator(SortingAlgorithmsRegistry.BUBBLE.get()); 
	}
}

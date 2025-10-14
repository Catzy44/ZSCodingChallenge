package me.kotsu.data.ftp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.Optional;

import org.apache.ftpserver.ftplet.FtpException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.kotsu.AppUtils;
import me.kotsu.config.test.AppConfigurationTest;
import me.kotsu.exceptions.FetchException;

class FTPDataProviderTest {
	private static final Logger logger = LoggerFactory.getLogger(FTPDataProviderTest.class);
	private static AppConfigurationTest configTest;
    private static FTPDataProvider ftpDataProvider;

    @BeforeAll
    public static void setUp() throws IOException, FtpException {
    	configTest = new AppConfigurationTest();
    	configTest.getTestFtpServer().bootWithTestRoutesAndFiles();
    	ftpDataProvider = configTest.buildTestFTPDataProvider();
    }

    @AfterAll
    public static void tearDown() throws IOException, InterruptedException {
    	configTest.getTestFtpServer().kill();
    }

    @Test
    public void returnsCorrectFileContentIfPresent() throws IOException, InterruptedException {
        Optional<String> dataFetched = ftpDataProvider.fetch();
        
        assertTrue(dataFetched.isPresent(), "HTTPDataProvider should return data");

        String fetched = dataFetched.get();
        String expected = AppUtils.decodeBytesToString(AppUtils.getFullTestFileContent(), configTest.getDecoderCharset());

        assertEquals(expected, fetched, "fetched content should == file content");
    }
    
    @Test
    public void returnsEmptyIfTheFileIsMissing() throws IOException, InterruptedException {
    	FTPDataProviderTestFTPServer testFtpServer = configTest.getTestFtpServer();
    	
    	FTPDataProvider brokenProvider = new FTPDataProvider(new FTPDataProviderConfig(
				"localhost", 
				testFtpServer.getServerPort(), 
				testFtpServer.getUsername(), 
				testFtpServer.getPassword(), 
				"/nonExistantFile.json", // <- this file does not exist 
				configTest.getDecoderCharset()
		));
    	
        Optional<String> dataFetched = brokenProvider.fetch();
        
        assertTrue(dataFetched.isEmpty(), "FTPDataProvider should return nothing and do not throw on non-existsnt file!");
    }

    @Test
    public void throwsIfServerIsDown() throws IOException, InterruptedException {
        tearDown(); // server is down simulation
       
        assertThrows(FetchException.class, () -> ftpDataProvider.fetch(), "should throw if the server is down");
    }
}

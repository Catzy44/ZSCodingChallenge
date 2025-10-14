package me.kotsu.data.ftp;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.UserManager;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.ClearTextPasswordEncryptor;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.PropertiesUserManager;

import lombok.Getter;
import me.kotsu.AppUtils;

@Getter
public class FTPDataProviderTestFTPServer {
	private static FtpServer server;
	private String serverDefaultListener = "default";
	private Path serverRootDirTemp;
	private int serverPort;

	private final String testFileName = "lista.json";
	
	private final String username = "test";
	private final String password = "testpass";
	    
	public void bootWithTestRoutesAndFiles() throws IOException, FtpException {
		serverRootDirTemp = Files.createTempDirectory("ZAiKS_FTP_TEST_" + Math.random()); // testowy root tymczasowego serwera FTP

		byte[] bytes = AppUtils.getFullTestFileContent();
		
		Path testFile = serverRootDirTemp.resolve(testFileName);
		Files.createFile(testFile);
		Files.write(testFile, bytes);

		ListenerFactory listenerFactory = new ListenerFactory();
		listenerFactory.setPort(0); // dowolny wolny port

		BaseUser user = new BaseUser();
		user.setName(username);
		user.setPassword(password);
		user.setHomeDirectory(serverRootDirTemp.toAbsolutePath().toString());

		UserManager userManager = new PropertiesUserManager(new ClearTextPasswordEncryptor(), (File) null, "admin");
		userManager.save(user);

		FtpServerFactory serverFactory = new FtpServerFactory();
		serverFactory.setUserManager(userManager);
		serverFactory.addListener(serverDefaultListener, listenerFactory.createListener());

		server = serverFactory.createServer();
		server.start();

		serverPort = serverFactory.getListener(serverDefaultListener).getPort();
	}

	public void kill() throws IOException, InterruptedException {
		if (server != null) {
			server.stop();
		}
		server = null;
		//nie usuwam pliku tyczasowego bo system operacyjny sam to zrobi
	}
}

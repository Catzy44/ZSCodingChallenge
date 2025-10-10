package me.kotsu.data.ftp;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import org.apache.commons.net.ftp.FTPClient;

import me.kotsu.config.AppConfiguration;
import me.kotsu.data.DataProvider;

public class FTPDataProvider implements DataProvider<FTPDataProviderConfig> {

	@Override
	public Optional<String> fetch(FTPDataProviderConfig config) {
		try {
			FTPClient ftp = new FTPClient();
			
			ftp.connect(config.server() + ":" + config.port());
			ftp.login(config.user(), config.password());
			
			ftp.enterLocalPassiveMode();// zawsze daję
			
			InputStream fileIS = ftp.retrieveFileStream(config.path());
			String fileContent = new String(fileIS.readAllBytes(), AppConfiguration.CHARSET); //hardcodowane dekodowanie stringa, takie mam założenia, że plik to String JSON!
			
			ftp.logout();
			ftp.disconnect();
			
			return Optional.of(fileContent);
			
		} catch (IOException e) {
			return Optional.empty();
		}
	}

}

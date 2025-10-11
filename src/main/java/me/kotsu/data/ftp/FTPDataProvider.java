package me.kotsu.data.ftp;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import org.apache.commons.net.ftp.FTPClient;

import me.kotsu.AppUtils;
import me.kotsu.data.DataProvider;

public class FTPDataProvider implements DataProvider<FTPDataProviderConfig> {
	private FTPDataProviderConfig config;
	public FTPDataProvider(FTPDataProviderConfig config) {
		this.config = config;
	}


	@Override
	public Optional<String> fetch() {
		
		FTPClient ftp = new FTPClient();
		try {
			ftp.connect(config.server(), config.port());
			
			if(!ftp.login(config.user(), config.password())) {
				return Optional.empty();
			}
			
			ftp.enterLocalPassiveMode();// zawsze daję
			
			try(InputStream fileStream = ftp.retrieveFileStream(config.path())) {
				byte[] allFileBytes = fileStream.readAllBytes();
				
				String fileContent = AppUtils.decodeBytesToString(allFileBytes, config.decoderCharset());
				
				if (!ftp.completePendingCommand()) {
	                return Optional.empty();
	            }
				
				return Optional.of(fileContent);
			}
		} catch (IOException ignored) {
			return Optional.empty();
		} finally {
			try {
				if (ftp.isConnected()) {
		            ftp.logout();
		            ftp.disconnect();
		        }
			} catch (IOException ignored) {}
		}
	}
}

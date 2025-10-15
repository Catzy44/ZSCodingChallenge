package me.kotsu.data.ftp;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.util.Optional;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import me.kotsu.AppUtils;
import me.kotsu.data.DataProvider;
import me.kotsu.exceptions.FetchException;

public class FTPDataProvider implements DataProvider<FTPDataProviderConfig> {
	private FTPDataProviderConfig config;
	public FTPDataProvider(FTPDataProviderConfig config) {
		this.config = config;
	}
	
	public void ftpConnect(FTPClient ftp) throws SocketException, IOException {
		ftp.connect(config.server(), config.port());
		if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
            throw new FetchException("FTP connect failed: " + ftp.getReplyCode() + " " + ftp.getReplyString());
        }
	}
	
	public void ftpLogin(FTPClient ftp) throws IOException {
		if(!ftp.login(config.user(), config.password())) {
			throw new FetchException("FTP login failed!");
		}
		ftp.enterLocalPassiveMode();// passive mode - must be set AFTER the login
	}
	
	public Optional<byte[]> readFile(FTPClient ftp, String path) throws FetchException, IOException {
		try(InputStream fileStream = ftp.retrieveFileStream(config.path())) {
			if(fileStream == null) {
				int code = ftp.getReplyCode();
	            if (code == FTPReply.FILE_UNAVAILABLE) {
	                return Optional.empty();
	            }
	            throw new FetchException("retrieveFileStream returned null: " + code + " " + ftp.getReplyString());
			}
			
			byte[] allFileBytes = fileStream.readAllBytes();
			if (!ftp.completePendingCommand()) {
				throw new FetchException("completePendingCommand failed: " + ftp.getReplyCode() + " " + ftp.getReplyString());
            }
			return Optional.of(allFileBytes);
		}
	}

	@Override
	public Optional<String> fetch() throws FetchException {
		try {
			FTPClient ftp = new FTPClient();
			
			ftpConnect(ftp);
			ftpLogin(ftp);
			
			Optional<byte[]> fileBytes = readFile(ftp, config.path());
			if(fileBytes.isEmpty()) {
				return Optional.empty();
			}
			String fileContent = AppUtils.decodeBytesToString(fileBytes.get(), config.decoderCharset());
			return Optional.of(fileContent);
		} catch (IOException e) {
			throw new FetchException("Fetching FTP failed! ",e);
		}
	}
}

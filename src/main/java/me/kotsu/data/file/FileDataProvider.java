package me.kotsu.data.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import me.kotsu.AppUtils;
import me.kotsu.data.DataProvider;
import me.kotsu.exceptions.FetchException;

public class FileDataProvider implements DataProvider<FileDataProviderConfig> {
	private FileDataProviderConfig config;
	public FileDataProvider(FileDataProviderConfig config) {
		this.config = config;
	}

	@Override
	public Optional<String> fetch() throws FetchException {
		if(config.path() == null) {
			return Optional.empty(); //no path provided - return empty
		}
		Path filePath = config.path();
		
		if(!Files.exists(filePath)) {
			return Optional.empty(); //no file - return empty
		}
		
		try {
	        byte[] allFileBytes = Files.readAllBytes(filePath);
	        return Optional.of(AppUtils.decodeBytesToString(allFileBytes, config.decoderCharset()));
	    } catch (IOException e) {
	    	throw new FetchException("IO error reading file: ", e);
	    }
	}
	
}

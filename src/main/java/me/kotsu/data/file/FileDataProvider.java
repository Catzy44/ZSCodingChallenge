package me.kotsu.data.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import me.kotsu.AppUtils;
import me.kotsu.data.DataProvider;

public class FileDataProvider implements DataProvider<FileDataProviderConfig> {

	@Override
	public Optional<String> fetch(FileDataProviderConfig config) {
		if(config.path() == null) {
			throw new IllegalArgumentException("provided path is null!");
		}
		Path path = config.path();
		
		if(!Files.exists(path)) {
			return Optional.empty();
		}
		
		try {
	        byte[] allFileBytes = Files.readAllBytes(path);
	        return Optional.of(AppUtils.decodeBytesToString(allFileBytes));
	    } catch (IOException e) {
	        return Optional.empty();
	    }
	}
	
}

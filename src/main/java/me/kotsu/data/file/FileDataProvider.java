package me.kotsu.data.file;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import me.kotsu.data.DataProvider;

public class FileDataProvider implements DataProvider<FileDataProviderConfig> {

	@Override
	public Optional<String> fetch(FileDataProviderConfig providerConfig) {
		if(providerConfig.path() == null) {
			throw new IllegalArgumentException("provided path is null!");
		}
		Path path = providerConfig.path();
		
		if(!Files.exists(path)) {
			return Optional.empty();
		}
		
		try {
	        byte[] fileContent = Files.readAllBytes(path);
	        return Optional.of(new String(fileContent, StandardCharsets.UTF_8));
	    } catch (IOException e) {
	        return Optional.empty();
	    }
	}
	
}

package me.kotsu.data.source;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import lombok.Getter;
import me.kotsu.AppUtils;
import me.kotsu.data.file.FileDataProvider;

@Getter
public class FileDataSource implements TestDataSource {
	private Path file;
	
	@Override
	public void enable() throws IOException {
		file = Files.createTempFile("lista_", ".tmp");
        Files.write(file, AppUtils.getFullTestFileContent());
	}
	
	@Override
	public void disable() throws IOException {
		if (file != null) {
            Files.deleteIfExists(file);
        }
	}
	
	@Override
	public boolean excludeFromThrowingTest() {
		return true;
	}
	
	@Override
	public Class<?> dataProviderClass() {
		return FileDataProvider.class;
	}
}

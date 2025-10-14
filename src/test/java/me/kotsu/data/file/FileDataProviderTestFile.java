package me.kotsu.data.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import lombok.Getter;
import me.kotsu.AppUtils;

@Getter
public class FileDataProviderTestFile {
	private Path file;
	
	public void place() throws IOException {
		file = Files.createTempFile("lista_", ".tmp");
        Files.write(file, AppUtils.getFullTestFileContent());
	}
	public void delete() throws IOException {
		if (file != null) {
            Files.deleteIfExists(file);
        }
	}
}

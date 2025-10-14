package me.kotsu.data.file;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import me.kotsu.AppUtils;

class FileDataProviderTest {

    private static final Charset DECODER_CHARSET = StandardCharsets.UTF_8;

    private Path testFile;
    private FileDataProvider fileProvider;

    private byte[] getFullTestFileContent() throws IOException {
        try (var in = getClass().getResourceAsStream("lista.json")) {
        	if (in == null) {
                throw new IllegalStateException("missing resource: lista.json");
            }
            return in.readAllBytes();
        }
    }

    private Path createTempTestFile() throws IOException {
        Path file = Files.createTempFile("lista_", ".tmp");
        Files.write(file, getFullTestFileContent());
        return file;
    }

    @BeforeEach
    void setUp() throws IOException {
        testFile = createTempTestFile();
        fileProvider = new FileDataProvider(new FileDataProviderConfig(testFile, DECODER_CHARSET));
    }

    @AfterEach
    void tearDown() throws IOException {
        if (testFile != null) {
            Files.deleteIfExists(testFile);
        }
    }

    @Test
    void returnsCorrectFileContentIfPresent() throws IOException {
        Optional<String> dataFetched = fileProvider.fetch();
        assertTrue(dataFetched.isPresent(), "FileDataProvider should return data");

        String fetched = dataFetched.get();
        String expected = AppUtils.decodeBytesToString(getFullTestFileContent(), DECODER_CHARSET);

        assertEquals(expected, fetched, "fetched content should == file content");
    }

    @Test
    void returnsEmptyIfFileMissing() throws IOException {
        tearDown(); // symulacja braku pliku
        
        Optional<String> dataFetched = fileProvider.fetch();
        assertTrue(dataFetched.isEmpty(), "should return nothing if the file is missing");
    }
}

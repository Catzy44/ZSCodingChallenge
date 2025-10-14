package me.kotsu;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

public class AppUtils {
	public static String decodeBytesToString(byte[] bytes, Charset charset) {
		return new String(bytes, charset); //hardcodowane dekodowanie stringa, takie mam założenia, że plik to String JSON!
	}
	
	public static final String TEST_FILE_NAME = "lista.json";
	
	public static byte[] getFullTestFileContent() throws IOException {
        try (var in = AppUtils.class.getResourceAsStream("lista.json")) {
            if (in == null) {
                throw new IllegalStateException("missing resource: lista.json");
            }
            return in.readAllBytes();
        }
    }
	
	public static void deleteWithContents(Path p) throws IOException {
		Files.walk(p).sorted(Comparator.reverseOrder()).forEach(f -> {
			try {Files.delete(f);} catch (Exception ignored) {}
		});
	}
} 

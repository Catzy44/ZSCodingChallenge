package me.kotsu;
import java.io.IOException;
import java.nio.charset.Charset;

public class AppUtils {
	public static String decodeBytesToString(byte[] bytes, Charset charset) {
		return new String(bytes, charset); //hardcodowane dekodowanie stringa, takie mam założenia, że plik to String JSON!
	}
	
	public static byte[] getFullTestFileContent() throws IOException {
        try (var in = AppUtils.class.getResourceAsStream("lista.json")) {
            if (in == null) {
                throw new IllegalStateException("missing resource: lista.json");
            }
            return in.readAllBytes();
        }
    }
}

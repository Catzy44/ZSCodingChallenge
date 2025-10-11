package me.kotsu;
import java.nio.charset.Charset;

public class AppUtils {
	public static String decodeBytesToString(byte[] bytes, Charset charset) {
		return new String(bytes, charset); //hardcodowane dekodowanie stringa, takie mam założenia, że plik to String JSON!
	}
}

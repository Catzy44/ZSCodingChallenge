package me.kotsu;
import me.kotsu.config.AppConfiguration;

public class AppUtils {
	public static String decodeBytesToString(byte[] bytes) {
		return new String(bytes, AppConfiguration.CHARSET); //hardcodowane dekodowanie stringa, takie mam założenia, że plik to String JSON!
	}
}

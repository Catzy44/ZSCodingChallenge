package me.kotsu.formatter;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import me.kotsu.exceptions.ParsingException;

public class FormatterDynamicTest {
	static Stream<Formatter> formatters() {
        return Arrays.stream(FormattersRegistry.values()).map(FormattersRegistry::get);
    }

    @ParameterizedTest(name = "{0} - returns something for basic case")
    @MethodSource("formatters")
    void formatsBaseCaseToNonEmptyString(Formatter formatter) throws ParsingException {
    	int[] elements = new int[] {0,1,2,3,4};
		String formatted = formatter.format(elements);
		assertNotNull(formatted);
		assertFalse(formatted.isEmpty());
    }
    
    @ParameterizedTest(name = "{0} - handles empty array")
    @MethodSource("formatters")
	public void handlesEmptyArray(Formatter formatter) {
		int[] elements = new int[] {};
		String formatted = formatter.format(elements);
		assertNotNull(formatted);
	}
    
    @ParameterizedTest(name = "{0} - throws on null")
    @MethodSource("formatters")
	public void throwsOnNull(Formatter formatter) {
		assertThrows(Exception.class, () -> formatter.format(null));
	}
}

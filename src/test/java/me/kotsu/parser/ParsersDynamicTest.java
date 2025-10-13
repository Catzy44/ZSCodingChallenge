package me.kotsu.parser;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class ParsersDynamicTest {
	private static Stream<Parser> parsers() {
        return Arrays.stream(ParsersRegistry.values())
                     .map(ParsersRegistry::get);
    }

    @ParameterizedTest(name = "should correctly parse valid JSON using {0}")
    @MethodSource("parsers")
    @DisplayName("should correctly parse valid JSON with all registered parsers")
    void shouldParseValidJson(Parser parser) {
        String json = "{\"elements\":[10, 13, -5, 50, 0]}";
        Optional<int[]> result = parser.parse(json);

        assertTrue(result.isPresent(), "Parser should return non-empty result");
        assertArrayEquals(new int[]{10, 13, -5, 50, 0}, result.get());
    }

    @ParameterizedTest(name = "should return empty Optional for malformed JSON using {0}")
    @MethodSource("parsers")
    @DisplayName("should return empty Optional for malformed JSON")
    void shouldReturnEmptyForMalformedJson(Parser parser) {
        String malformedJson = "{\"elements\":[1,2,3}";
        Optional<int[]> result = parser.parse(malformedJson);

        assertTrue(result.isEmpty(), "Parser should safely return empty Optional for invalid input");
    }

    @ParameterizedTest(name = "should return empty Optional for missing field using {0}")
    @MethodSource("parsers")
    @DisplayName("should return empty Optional for JSON without 'elements'")
    void shouldReturnEmptyForMissingElements(Parser parser) {
        String jsonWithoutField = "{\"data\":[1,2,3]}";
        Optional<int[]> result = parser.parse(jsonWithoutField);

        assertTrue(result.isEmpty());
    }

    @ParameterizedTest(name = "should return empty Optional for null input using {0}")
    @MethodSource("parsers")
    @DisplayName("should return empty Optional for null input")
    void shouldReturnEmptyForNullInput(Parser parser) {
        Optional<int[]> result = parser.parse(null);

        assertTrue(result.isEmpty());
    }

    @ParameterizedTest(name = "should return empty Optional for null elements using {0}")
    @MethodSource("parsers")
    @DisplayName("should return empty Optional for JSON with null elements")
    void shouldReturnEmptyForNullElements(Parser parser) {
        String json = "{\"elements\":null}";
        Optional<int[]> result = parser.parse(json);

        assertTrue(result.isEmpty());
    }
}

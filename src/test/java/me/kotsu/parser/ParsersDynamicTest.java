package me.kotsu.parser;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import me.kotsu.exceptions.ParsingException;

class ParsersDynamicTest {

    static Stream<Parser> parsers() {
        return Arrays.stream(ParsersRegistry.values()).map(ParsersRegistry::get);
    }

    @ParameterizedTest(name = "{0} - poprawny JSON")
    @MethodSource("parsers")
    void parsesValidJson(Parser parser) throws ParsingException {
        String json = "{\"elements\":[10,13,-5,50,0]}";
        Optional<int[]> result = parser.parse(json);

        assertTrue(result.isPresent());
        assertArrayEquals(new int[]{10,13,-5,50,0}, result.get());
    }

    @ParameterizedTest(name = "{0} - błędny JSON")
    @MethodSource("parsers")
    void throwsOnInvalidJSON(Parser parser) throws ParsingException {
        String broken = "{\"elements\":[1,2,3}";
        
        assertThrows(ParsingException.class, () -> parser.parse(broken));
    }

    @ParameterizedTest(name = "{0} - brak pola elements")
    @MethodSource("parsers")
    void handlesMissingField(Parser parser) throws ParsingException {
        String json = "{\"data\":[1,2,3]}";
        Optional<int[]> result = parser.parse(json);

        assertTrue(result.isEmpty());
    }

    @ParameterizedTest(name = "{0} - null input")
    @MethodSource("parsers")
    void handlesNullInput(Parser parser) throws ParsingException {
        Optional<int[]> result = parser.parse(null);
        assertTrue(result.isEmpty());
    }

    @ParameterizedTest(name = "{0} - null elements")
    @MethodSource("parsers")
    void handlesNullElements(Parser parser) throws ParsingException {
        String json = "{\"elements\":null}";
        Optional<int[]> result = parser.parse(json);

        assertTrue(result.isEmpty());
    }
}

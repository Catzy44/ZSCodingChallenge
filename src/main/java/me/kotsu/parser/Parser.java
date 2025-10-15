package me.kotsu.parser;

import java.util.Optional;

import me.kotsu.exceptions.ParsingException;

public interface Parser {
	/**
	 * Decodes or encodes textual data.
	 *
	 * Behavior:
	 * - Successful parsing: returns Optional<int[]>
	 * - No data to parse: returns empty Optional<>
	 * - Invalid data or format: throws ParsingException with original exception
	 */
	public Optional<int[]> parse(String data) throws ParsingException;
	public Optional<String> encode(int[] data);
}

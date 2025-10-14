package me.kotsu.parser;

import java.util.Optional;

import me.kotsu.exceptions.ParsingException;

public interface Parser {
	public Optional<int[]> parse(String data) throws ParsingException;
}

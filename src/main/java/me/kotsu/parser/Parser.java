package me.kotsu.parser;

import java.util.Optional;

public interface Parser {
	public Optional<int[]> parse(String data);
}

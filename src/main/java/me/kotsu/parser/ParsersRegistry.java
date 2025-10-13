package me.kotsu.parser;

import me.kotsu.parser.json.JsonParser;

public enum ParsersRegistry {
    JSON(new JsonParser());

	private final Parser instance;

	ParsersRegistry(Parser instance) {
        this.instance = instance;
    }

    /**
     * @return singleton instance of selected {@link Parser}
     */
    public Parser get() {
        return instance;
    }
}
package me.kotsu.formatter;

import me.kotsu.formatter.simple.SimpleFormatter;

public enum FormattersRegistry {
    SIMPLE(new SimpleFormatter());

	private final Formatter instance;

	FormattersRegistry(Formatter instance) {
        this.instance = instance;
    }

    /**
     * @return singleton instance of selected {@link Formatter}
     */
    public Formatter get() {
        return instance;
    }
}
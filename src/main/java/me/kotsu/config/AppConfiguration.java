package me.kotsu.config;

import me.kotsu.data.DataProviderService;
import me.kotsu.formatter.Formatter;
import me.kotsu.parser.Parser;
import me.kotsu.sorter.Sorter;

public interface AppConfiguration {
	public DataProviderService buildDataProviderService();
	public Parser buildParser();
	public Sorter buildSorter();
	public Formatter buildFormatter();
}

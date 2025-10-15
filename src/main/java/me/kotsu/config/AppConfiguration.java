package me.kotsu.config;

import me.kotsu.data.DataProviderService;
import me.kotsu.parser.Parser;
import me.kotsu.sort.Sorter;

public interface AppConfiguration {
	public DataProviderService buildDataProviderService();
	public Parser buildParser();
	public Sorter buildSorter();
}

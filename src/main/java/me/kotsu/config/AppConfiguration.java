package me.kotsu.config;

import me.kotsu.data.DataProviderFactory;
import me.kotsu.parser.Parser;
import me.kotsu.sort.SortingService;

public interface AppConfiguration {
	public DataProviderFactory buildFactory();
	public Parser buildParser();
	public SortingService buildSorter();
}

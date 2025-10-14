package me.kotsu.config;

import me.kotsu.data.DataProviderService;
import me.kotsu.parser.Parser;
import me.kotsu.sort.SortingService;

public interface AppConfiguration {
	public DataProviderService buildDataProviderService();
	public Parser buildParser();
	public SortingService buildSorter();
}

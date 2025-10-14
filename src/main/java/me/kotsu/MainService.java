package me.kotsu;

import java.util.Arrays;
import java.util.stream.Collectors;

import me.kotsu.config.AppConfiguration;
import me.kotsu.exceptions.ParsingException;
import me.kotsu.sort.SortingService;

public class MainService {
	private static AppConfiguration config;
	public MainService(AppConfiguration conf) {
		config = conf;
	}
	
	/**
	 * Entry point of the application.
	 * 
	 * 1. Fetches integer list from first available data source (File, HTTP, FTP).
	 * 2. Parses input into int array using selected parser.
	 * 3. Sorts data using selected sorting algorithm.
	 * 
	 * @return sorted output and logs execution time.
	 * @throws ParsingException 
	 */
	public String start() throws ParsingException {
		String data = config.buildDataProviderService()
				.fetchFirstAvailableDataSource()
				.orElseThrow();
		
		int[] elements = config.buildParser()
				.parse(data)
				.orElseThrow();
		
		SortingService sortingService = config.buildSorter();
		sortingService.sort(elements);
		
		return Arrays.stream(elements)
	            .mapToObj(String::valueOf)
	            .collect(Collectors.joining(", "));
	}
}

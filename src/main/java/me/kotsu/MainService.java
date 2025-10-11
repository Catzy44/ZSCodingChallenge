package me.kotsu;

import java.util.Arrays;
import java.util.stream.Collectors;

import me.kotsu.config.AppConfiguration;
import me.kotsu.sort.SortingService;

public class MainService {
	private static AppConfiguration config;
	public MainService(AppConfiguration conf) {
		config = conf;
	}
	
	public String start() {
		String data = config.buildFactory()
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

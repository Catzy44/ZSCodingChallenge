package me.kotsu;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.kotsu.config.AppConfiguration;
import me.kotsu.exceptions.FetchException;
import me.kotsu.exceptions.ParsingException;
import me.kotsu.formatter.Formatter;
import me.kotsu.parser.Parser;
import me.kotsu.sort.Sorter;

public class MainService {
	private static final Logger logger = LoggerFactory.getLogger(MainService.class);
	
	private static AppConfiguration config;
	public MainService(AppConfiguration conf) {
		config = conf;
	}
	
	/**
     * Pipeline:
     * 1. fetch data from first available source
     * 2. parse JSON into int[].
     * 3. sort data with selected algorithm.
     * 4. format data to string
     *
     * @return sorted numbers as a fo string
     * @throws FetchException   if no data source succeeded
     * @throws ParsingException if parsing failed
     */
    public String start() throws FetchException, ParsingException {
        Parser parser = config.buildParser();
        Sorter sorter = config.buildSorter();
        Formatter formatter = config.buildFormatter();
        
        return config.buildDataProviderService()
        		.streamValidParsedData(parser)
        		.peek(sorter::sort)
                .map(formatter::format)
                .findFirst()
                .orElseThrow(() -> new FetchException("Cannot find any source with a valid data!"));
    }
    
    

    public String formatOutput(int[] elements) {
        return Arrays.stream(elements)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(", "));
    }
}

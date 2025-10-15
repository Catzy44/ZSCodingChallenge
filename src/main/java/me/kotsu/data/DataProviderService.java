package me.kotsu.data;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.kotsu.exceptions.FetchException;
import me.kotsu.exceptions.ParsingException;
import me.kotsu.parser.Parser;

public class DataProviderService {
	private static final Logger logger = LoggerFactory.getLogger(DataProviderService.class);
	private final List<DataProvider<?>> activeDataProviders;
	 
	public DataProviderService(List<DataProvider<?>> providers) {
		this.activeDataProviders = providers;
	}

	/*public Optional<String> fetchFirstAvailableDataSource() {
		 return activeDataProviders.stream()
		 .map( p -> fetchWrapper(p))
		 .filter(Optional::isPresent)
		 .findFirst()
		 .orElse(Optional.empty());
	 }*/
	
	
//	public Stream<DataProvider<?>> streamProviders() {
//		return activeDataProviders.stream();
//	}
	
	
	public Stream<int[]> streamValidParsedData(Parser parser) {
		return activeDataProviders.stream()
				.map(dataProvider -> tryFetchAndParse(dataProvider, parser))
				.filter(Optional::isPresent)
				.map(Optional::get);
	}
	
	public Optional<int[]> tryFetchAndParse(DataProvider<?> provider, Parser parser) {
    	String pName = provider.getClass().toString();
        try {
            Optional<String> rawDecodedContent = provider.fetch();
            if (rawDecodedContent.isEmpty()) {
            	logger.warn(String.format("%s - File not found / File empty", pName));
            	
            	return Optional.empty();
            }

            Optional<int[]> parsedContent = parser.parse(rawDecodedContent.get());
            if (parsedContent.isEmpty()) {//dead code, probably?
            	logger.warn(String.format("%s - File not found / File empty", pName));
            	
            	return Optional.empty();
            }

            return parsedContent;
        } catch (FetchException e) {
        	logger.error(String.format("%s - %s", pName, e.getMessage()));
        	
            return Optional.empty();
        } catch (ParsingException e) {
        	logger.error(String.format("%s - %s", pName, e.getMessage()));
        	
            return Optional.empty();
        }
    }
	
	public static DataProviderServiceBuilder builder() {
        return new DataProviderServiceBuilder();
    }
}

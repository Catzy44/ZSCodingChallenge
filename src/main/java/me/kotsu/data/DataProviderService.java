package me.kotsu.data;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.kotsu.monitoring.MeasureTimeAspect;

public class DataProviderService {
	private static final Logger logger = LoggerFactory.getLogger(MeasureTimeAspect.class);
	private final List<DataProvider<?>> activeDataProviders;
	 
	public DataProviderService(List<DataProvider<?>> providers) {
		this.activeDataProviders = providers;
	}

	public Optional<String> fetchFirstAvailableDataSource() {
		 return activeDataProviders.stream()
		 .map( p -> fetchWrapper(p))
		 .filter(Optional::isPresent)
		 .findFirst()
		 .orElse(Optional.empty());
	 }
	
	public Optional<String> fetchWrapper(DataProvider<?> p) {
		try {
			return p.fetch();
		} catch (Exception e) {
			logger.error("fetching "+p.getClass()+" failed with and Exception!");
			e.printStackTrace();
		}
		return Optional.empty();
	}
	
	public static DataProviderServiceBuilder builder() {
        return new DataProviderServiceBuilder();
    }
}

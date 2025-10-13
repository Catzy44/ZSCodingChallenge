package me.kotsu.data;

import java.util.List;
import java.util.Optional;

public class DataProviderService {
	
	//private final Map<DataProvider<?>, DataProviderConfig> dataSources;//linked bo kolejność ma być zachowana
	private final List<DataProvider<?>> activeDataProviders;
	 
	public DataProviderService(List<DataProvider<?>> providers) {
		this.activeDataProviders = providers;
	}

	public Optional<String> fetchFirstAvailableDataSource() {
		 return activeDataProviders.stream()
		 .map(DataProvider::fetch)
		 .filter(Optional::isPresent)
		 .findFirst()
		 .orElse(Optional.empty());
	 }
	
	public static DataProviderServiceBuilder builder() {
        return new DataProviderServiceBuilder();
    }
}

package me.kotsu.data;

import java.util.List;
import java.util.Optional;

public class DataProviderFactory {
	
	//private final Map<DataProvider<?>, DataProviderConfig> dataSources;//linked bo kolejność ma być zachowana
	private final List<DataProvider<?>> providers;
	 
	public DataProviderFactory(List<DataProvider<?>> providers) {
		this.providers = providers;
	}

	@SuppressWarnings("unchecked")
	public Optional<String> fetchFirstAvailableDataSource() {
		 return providers.stream()
		 .map(DataProvider::fetch)
		 .filter(Optional::isPresent)
		 .findFirst()
		 .orElse(Optional.empty());
	 }
	
	public static DataProviderFactoryBuilder builder() {
        return new DataProviderFactoryBuilder();
    }
}

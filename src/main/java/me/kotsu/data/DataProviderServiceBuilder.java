package me.kotsu.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataProviderServiceBuilder {
    //private final Map<DataProvider<?>, DataProviderConfig> sources = new LinkedHashMap<>();
    private final List<DataProvider<?>> selectedDataProviders = new ArrayList<DataProvider<?>>();

    public <T extends DataProviderConfig> DataProviderServiceBuilder add(DataProvider<T> provider) {
    	selectedDataProviders.add(provider);
        return this;
    }

    public DataProviderService build() {
        return new DataProviderService(Collections.unmodifiableList(selectedDataProviders)); //robię już immutable bo builder ten tego...
    }
}
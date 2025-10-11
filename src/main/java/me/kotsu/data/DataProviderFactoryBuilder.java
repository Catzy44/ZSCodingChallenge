package me.kotsu.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataProviderFactoryBuilder {
    //private final Map<DataProvider<?>, DataProviderConfig> sources = new LinkedHashMap<>();
    private final List<DataProvider<?>> providers = new ArrayList<DataProvider<?>>();

    public <T extends DataProviderConfig> DataProviderFactoryBuilder add(DataProvider<T> provider) {
    	providers.add(provider);
        return this;
    }

    public DataProviderFactory build() {
        return new DataProviderFactory(Collections.unmodifiableList(providers)); //robię już immutable bo builder ten tego...
    }
}
package me.kotsu.data.dumb;

import java.util.Optional;

import me.kotsu.data.DataProviderConfig;

public record DumbDataProviderConfig(Optional<String> resp) implements DataProviderConfig {}
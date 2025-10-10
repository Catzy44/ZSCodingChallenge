package me.kotsu.data.file;

import java.nio.file.Path;

import me.kotsu.data.DataProviderConfig;

public record FileDataProviderConfig(Path path) implements DataProviderConfig {}

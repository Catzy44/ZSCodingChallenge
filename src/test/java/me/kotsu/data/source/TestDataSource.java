package me.kotsu.data.source;

public interface TestDataSource {
	public void enable() throws Exception;
	public void disable() throws Exception;
	public boolean excludeFromThrowingTest();
	public Class<?> dataProviderClass();
}

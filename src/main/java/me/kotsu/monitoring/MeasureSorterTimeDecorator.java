package me.kotsu.monitoring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.kotsu.sort.Sorter;

public class MeasureSorterTimeDecorator implements Sorter {
	private static final Logger logger = LoggerFactory.getLogger(MeasureSorterTimeDecorator.class);
	private Sorter sorter;

	public MeasureSorterTimeDecorator(Sorter sorter) {
		this.sorter = sorter;
	}

	@Override
	public void sort(int[] data) {
		String sorterClassName = sorter.getClass().toString();
		long start = System.currentTimeMillis();
		
		sorter.sort(data);
		
		long end = System.currentTimeMillis();
		logger.info(String.format("Sorter %s executed in %d ms", sorterClassName, (end - start)));
	}
}

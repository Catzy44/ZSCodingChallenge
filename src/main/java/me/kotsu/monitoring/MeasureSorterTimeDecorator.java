package me.kotsu.monitoring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.kotsu.sort.SortingAlgorithm;

public class MeasureSorterTimeDecorator implements SortingAlgorithm {
	private static final Logger logger = LoggerFactory.getLogger(MeasureSorterTimeDecorator.class);
	private SortingAlgorithm sorter;

	public MeasureSorterTimeDecorator(SortingAlgorithm sorter) {
		this.sorter = sorter;
	}

	@Override
	public void sort(int[] data) {
		String pName = sorter.getClass().toString();
		long start = System.currentTimeMillis();
		
		sorter.sort(data);
		
		long end = System.currentTimeMillis();
		logger.info(String.format("Sorter %s executed in %d ms", pName, (end - start)));
	}
}

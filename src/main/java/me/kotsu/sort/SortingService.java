package me.kotsu.sort;

import me.kotsu.monitoring.MeasureTime;

public class SortingService {
	private final SortingAlgorithm sorter;

	public SortingService(SortingAlgorithm sorter) {
		this.sorter = sorter;
	}
	
	@MeasureTime(value = "sorting")
	public void sort(int[] arr) {
		sorter.sort(arr);
	}
}

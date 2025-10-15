package me.kotsu.sort;

import me.kotsu.monitoring.__MeasureTime;

public interface SortingAlgorithm {
	
	@__MeasureTime(value = "sorting")
	void sort(int[] data);
}

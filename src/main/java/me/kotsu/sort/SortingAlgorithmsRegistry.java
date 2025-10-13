package me.kotsu.sort;

import me.kotsu.sort.bubble.SortingAlgorithmBubble;
import me.kotsu.sort.selection.SortingAlgorithmSelection;

public enum SortingAlgorithmsRegistry {
    BUBBLE(new SortingAlgorithmBubble()),
    SELECTION(new SortingAlgorithmSelection());

	private final SortingAlgorithm instance;

    SortingAlgorithmsRegistry(SortingAlgorithm instance) {
        this.instance = instance;
    }

    /**
     * @return singleton instance of selected {@link SortingAlgorithm}
     */
    public SortingAlgorithm get() {
        return instance;
    }
}
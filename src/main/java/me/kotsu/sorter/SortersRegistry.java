package me.kotsu.sorter;

import me.kotsu.sorter.bubble.BubbleSorter;
import me.kotsu.sorter.selection.SelectionSorter;

public enum SortersRegistry {
    BUBBLE(new BubbleSorter()),
    SELECTION(new SelectionSorter());

	private final Sorter instance;

    SortersRegistry(Sorter instance) {
        this.instance = instance;
    }

    /**
     * @return singleton instance of selected {@link Sorter}
     */
    public Sorter get() {
        return instance;
    }
}
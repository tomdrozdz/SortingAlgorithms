package main;

import sorting.Quicksort;
import sorting.SortingAlgorithm;
import ui.UI;

public class Main {

	private SortingAlgorithm sortAlg;

	public static void main(String[] args) {
		new Main();
	}

	public Main() {

		sortAlg = new Quicksort();
		new UI(this);
	}

	public SortingAlgorithm getSortingAlgorithm() {
		return sortAlg;
	}

	public void setSortingAlgorithm(SortingAlgorithm sortAlg) {
		this.sortAlg = sortAlg;
	}
}
package sorting;

import otherActions.Actions;

import java.util.Random;

public class Quicksort implements SortingAlgorithm {

	private Random rand = new Random();

	@Override
	public void sort(int[][] sequences) {
		long start;
		long end;

		for(int i=0; i<sequences.length; i++) {

			start = System.nanoTime();
			quicksort(sequences[i], 0, sequences[i].length);
			end = System.nanoTime();

			System.out.println(end-start);

			Actions.test(sequences[i]);
		}
	}

	public void quicksort(int[] sequence, int begin, int end) {
		if(begin + 1 < end) {
			int middle = split(sequence, begin, end);
			quicksort(sequence, begin, middle);
			quicksort(sequence, middle+1, end);
		}
	}

	private int split(int[] sequence, int begin, int end) {
		
		swap(sequence, begin, begin + rand.nextInt(end-begin));
		int pivot = sequence[begin];
		
		int i = begin+1;
		int j = end-1;
		
		while(true) {
			
			while(i<=j && sequence[i] <= pivot)
				i++;
			
			while(sequence[j] > pivot)
				j--;
			
			if(i >= j) {
				swap(sequence, begin, j);
				return j;
			}
			swap(sequence, i, j);
		}
	}

	private void swap(int[] sequence, int idx1, int idx2) {

		if (idx1 != idx2) {
			int tmp = sequence[idx1];
			sequence[idx1] = sequence[idx2];
			sequence[idx2] = tmp;
		}
	}
}

package sorting;

import otherActions.Actions;

public class ShellSort implements SortingAlgorithm {

	private int[] gaps;
	private Gaps gapCalculations;

	public ShellSort(int gapChoice) {
		switch(gapChoice) {
			case 1: gapCalculations = new ShellSequence(); break;
			case 2: gapCalculations = new KnuthSequence(); break;
			case 3: gapCalculations = new CiuraSequence(); break;
		}
	}

	@Override
	public void sort(int[][] sequences) {

		long start;
		long end;

		gapCalculations.calculateGaps(sequences[0]);

		for(int i=0; i<sequences.length; i++) {

			start = System.nanoTime();
			Shellsort(sequences[i]);
			end = System.nanoTime();

			System.out.println(end-start);

			Actions.test(sequences[i]);
		}
	}

	public void Shellsort(int[] sequence) {
		for(int i = gaps.length - 1; i >= 0; i--)
			sortWithSelectedGap(sequence, gaps[i]);
	}

	private void sortWithSelectedGap(int[] sequence, int gap) {

		for(int i = gap; i < sequence.length; i++) {
			int tmp = sequence[i];

			int j;
			for(j = i - gap; j >= 0 && sequence[j] > tmp; j -= gap)
				sequence[j + gap] = sequence[j];

			sequence[j+gap] = tmp;
		}
	}

	private class ShellSequence implements Gaps {
		public void calculateGaps(int[] sequence) {

			int size = 0;
			int[] calculated = new int[200];

			for(int gap = sequence.length/2, i = 0; gap >= 1; gap /= 2, i++) {
				calculated[i] = gap;
				size++;
			}

			gaps = new int[size];

			for(int i = size - 1, j = 0; i >= 0; i--, j++)
				gaps[j] = calculated[i];
		}
	}

	private class KnuthSequence implements Gaps {
		public void calculateGaps(int[] sequence) {

			int size = 0;
			int[] calculated = new int[200];

			for(int i = 2, j = 0, gap = 1; gap <= sequence.length/3; i++, j++) {
				calculated[j] = gap;
				gap = (int) (Math.pow(3,i) - 1)/2;
				size++;
			}

			gaps = new int[size];

			for(int i = 0; i < size; i++)
				gaps[i] = calculated[i];
		}
	}

	private class CiuraSequence implements Gaps {
		public void calculateGaps(int[] sequence) {

			int size=0;
			int[] initial = new int[] {1, 4, 10, 23, 57, 132, 301, 701, 1750};
			int[] calculated = new int[200];
			int last = 1750;

			for(int i = 0, nextGap = (int) ((double) last * 2.25); nextGap < sequence.length; i++, nextGap = (int) ((double) last * 2.25)) {
				last = nextGap;
				calculated[i] = nextGap;
				size++;
			}

			int i;
			if(initial[8] < sequence.length)
				gaps = new int[initial.length + size];
			else
				for(int k = 0; k < initial.length; k++)
					if(initial[k] >= sequence.length) {
						gaps = new int[k];
						break;
					}

			for(i = 0; i < initial.length && initial[i] < sequence.length; i++)
				gaps[i] = initial[i];

			for(int j = 0; j < size; j++) {
				gaps[i] = calculated[j];
				i++;
			}
		}
	}

	private interface Gaps {
		void calculateGaps(int[] sequence);
	}
}
